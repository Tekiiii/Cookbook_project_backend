package com.praksa.team4.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.praksa.team4.entities.Chef;
import com.praksa.team4.entities.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

	Recipe findByName(String name);

	List<Recipe> findByChef(Chef chef);

}
