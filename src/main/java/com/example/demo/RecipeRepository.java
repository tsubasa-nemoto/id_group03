package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Integer>{
	List<Recipe> findByDishLike(String dish);
	Optional<Recipe> findByDish(String dish);


}
