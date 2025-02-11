package com.praksa.team4.entities.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.praksa.team4.entities.Allergens;
import com.praksa.team4.entities.MyCookBook;

public class RegularUserDTO {
	
	@NotNull(message = "Username must be specified")
	@Size(min = 2, max = 30, message = "User name must be between {min} and {max} characters long.")
	private String username;
		
	@NotNull(message = "Name must be included.")
	@Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.")
	private String name;

	@NotNull(message = "Lastname must be included.")
	@Size(min = 2, max = 30, message = "Lastname must be between {min} and {max} characters long.")
	private String lastname;

	@NotNull(message = "Email must be included.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.")
	private String email;
	
	private MyCookBook myCookBook;

	private List<Allergens> allergens;

	public RegularUserDTO() {
		super();
	}

	public RegularUserDTO(
			@NotNull(message = "Username must be specified") @Size(min = 2, max = 30, message = "User name must be between {min} and {max} characters long.") String username,
			@NotNull(message = "Name must be included.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Lastname must be included.") @Size(min = 2, max = 30, message = "Lastname must be between {min} and {max} characters long.") String lastname,
			@NotNull(message = "Email must be included.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			MyCookBook myCookBook, List<Allergens> allergens) {
		super();
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.myCookBook = myCookBook;
		this.allergens = allergens;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MyCookBook getMyCookBook() {
		return myCookBook;
	}

	public void setMyCookBook(MyCookBook myCookBook) {
		this.myCookBook = myCookBook;
	}

	public List<Allergens> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<Allergens> allergens) {
		this.allergens = allergens;
	}
}
