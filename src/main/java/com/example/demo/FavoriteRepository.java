package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Integer>{
Optional<Favorite> findByDishAndName(String dish,String name);
List<Favorite> findByDishAndNameLike(String dish,String name);
	List<Favorite> findByFav(boolean favo);

}
