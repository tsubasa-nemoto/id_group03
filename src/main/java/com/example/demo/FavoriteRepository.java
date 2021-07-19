package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Integer>{
Optional<Favorite> findByDishAndEmail(String dish,String email);
List<Favorite> findByDishAndEmailLike(String dish,String email);
	List<Favorite> findByFav(boolean favo);
	List <Favorite> findByEmailAndFav(String email,boolean favo);
	List<Favorite>findByEmail(String email);

}
