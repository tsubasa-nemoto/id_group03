package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	HttpSession session;

	/**
	 * レシピ検索の料理を表示
	 */
	@RequestMapping("/search")
	public ModelAndView searchitems(
			@RequestParam("search") String dish,
			ModelAndView mv) {

		List<Recipe> recipeList = null;//nullを入れて中身を空っぽに
		recipeList = recipeRepository.findAll();
		if (dish.equals("")) {//すべて空白だったら
			recipeList = recipeRepository.findAll();//全件検索
			mv.addObject("mes", "レシピ一覧");
			session.setAttribute("mes", "レシピ一覧");
		} else if (!dish.equals("")) {//nameが空白でないとき
			recipeList = recipeRepository.findByDishLike("%" + dish + "%");//あいまい検索で表示
			mv.addObject("mes", dish + "の検索結果");

		}

		mv.addObject("recipes", recipeList);//itemListをitems(list.htmlのth:eachのところ)に格納
		//mv.addObject("search", dish);//テキストボックスに保持(menu.htmlのth:valueで格納するキーをつくる)
		session.setAttribute("recipes", recipeList);
		mv.setViewName("recipeList");//list.htmlで表示

		return mv;
	}

	@RequestMapping(value = "{dish}")
	public ModelAndView details(
			@PathVariable(name = "dish") String dish,
			ModelAndView mv) {
		Optional<Recipe> recipe = recipeRepository.findByDish(dish);
		Recipe R = recipe.get();
		List<Review> review = reviewRepository.findByCode(R.getCode());

		mv.addObject("reviewList",review);
		mv.addObject("recipe", R);

		mv.setViewName("details");
		return mv;
	}
}
