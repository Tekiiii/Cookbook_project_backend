package com.praksa.team4.repositories;

import org.springframework.data.repository.CrudRepository;

import com.praksa.team4.entities.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

	//Optional<Recipe> findByName(String name);
	Recipe findByName(String name);

}
