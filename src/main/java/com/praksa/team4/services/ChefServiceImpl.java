package com.praksa.team4.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.praksa.team4.entities.Chef;
import com.praksa.team4.entities.UserEntity;
import com.praksa.team4.entities.dto.UserDTO;
import com.praksa.team4.repositories.ChefRepository;
import com.praksa.team4.repositories.UserRepository;
import com.praksa.team4.util.ErrorMessageHelper;
import com.praksa.team4.util.RESTError;
import com.praksa.team4.util.UserCustomValidator;

@Service
public class ChefServiceImpl implements ChefService {

	@Autowired
	private ChefRepository chefRepository;

	@Autowired
	UserCustomValidator userValidator;

	@Autowired
	private UserRepository userRepository;

	protected final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<?> createChef(UserDTO chef, BindingResult result, Authentication authentication) {

		if (result.hasErrors()) {
			logger.error("Sent incorrect parameters.");
			return new ResponseEntity<>(ErrorMessageHelper.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			logger.info("Validating if the users password matches the confirming password");
			userValidator.validate(chef, result);
			if (result.hasErrors()) {
				logger.error("Validation errors detected.");
				return new ResponseEntity<>(result.getFieldError(), HttpStatus.BAD_REQUEST);
			}
		}

		UserEntity existingUserWithEmail = userRepository.findByEmail(chef.getEmail());
		logger.info("Finding out whether there's a user with the same email.");

		UserEntity existingUserWithUsername = userRepository.findByUsername(chef.getUsername());
		logger.info("Finding out whether there's a user with the same username.");

		if (existingUserWithEmail != null) {
			logger.error("There is a user with the same email.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Email already exists"), HttpStatus.CONFLICT);
		}

		if (existingUserWithUsername != null) {
			logger.error("There is a user with the same username.");
			return new ResponseEntity<RESTError>(new RESTError(2, "Username already exists"), HttpStatus.CONFLICT);
		}

		Chef newChef = new Chef();

		newChef.setUsername(chef.getUsername());
		newChef.setPassword(chef.getPassword());
		newChef.setName(chef.getName());
		newChef.setLastname(chef.getLastname());
		newChef.setEmail(chef.getEmail());
		newChef.setRole("ROLE_CHEF");
		logger.info("Setting chef role.");

		chefRepository.save(newChef);
		logger.info("Saving chef to the database");

		return new ResponseEntity<Chef>(newChef, HttpStatus.CREATED);

	}

	public ResponseEntity<?> updateChef(Chef updatedChef, BindingResult result, Integer id,
			Authentication authentication) {

		String email = (String) authentication.getName();
		UserEntity currentChef = userRepository.findByEmail(email);

		if (currentChef.getRole().equals("ROLE_ADMIN")) {
			logger.info("Admin " + currentChef.getName() + " " + currentChef.getLastname() + " is updating chef.");
			Chef changeChef = chefRepository.findById(id).get();

			changeChef.setUsername(updatedChef.getUsername());
			changeChef.setPassword(updatedChef.getPassword());
			changeChef.setName(updatedChef.getName());
			changeChef.setLastname(updatedChef.getLastname());
			changeChef.setEmail(updatedChef.getEmail());
			changeChef.setRole(updatedChef.getRole());
			changeChef.setRecipes(updatedChef.getRecipes());
			chefRepository.save(changeChef);

			return new ResponseEntity<>(changeChef, HttpStatus.OK);
		} else if (currentChef.getRole().equals("ROLE_CHEF")) {
			logger.info(
					"Chef" + currentChef.getName() + " " + currentChef.getLastname() + " is updating his own profile.");
			Chef chef = (Chef) currentChef;
			Chef changeChef = chefRepository.findById(id).get();

			if (chef.getId().equals(changeChef.getId())) {
				logger.info("Chef is updating his own profile.");

				changeChef.setName(updatedChef.getName());
				changeChef.setLastname(updatedChef.getLastname());
				changeChef.setUsername(updatedChef.getUsername());
				changeChef.setEmail(updatedChef.getEmail());
				changeChef.setPassword(updatedChef.getPassword());
				chefRepository.save(changeChef);

				return new ResponseEntity<>(changeChef, HttpStatus.OK);
			}
		}

		return new ResponseEntity<RESTError>(new RESTError(2, "Not authorized to update chef"),
				HttpStatus.UNAUTHORIZED);

	}

}
