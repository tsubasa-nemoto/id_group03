package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepository;
@Autowired
HttpSession session;


	/**
	 * レシピ検索の料理を表示
	 */
	@RequestMapping("/search")
	public ModelAndView searchitems(
			//@RequestParam("search") String dish,
			ModelAndView mv) {

		List<Recipe> recipeList = null;//nullを入れて中身を空っぽに
		recipeList = recipeRepository.findAll();
//		if (dish.equals("") ) {//すべて空白だったら
//			recipeList = recipeRepository.findAll();//全件検索
//		} else if (!dish.equals("")) {//nameが空白でないとき
//			recipeList = recipeRepository.findByDishLike("%"+dish+"%");//あいまい検索で表示
////		} else if (!name.equals("") && !minPrice.equals("") && !maxPrice.equals("")) {//すべて空白でないとき
////			itemList = itemRepository.findByNameLikeAndPriceBetween("%"+name+"%", Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
//		}



		//itemList = itemRepository.findByNameLike("%"+name+"%");//あいまい検索したnameをitemListに格納
		mv.addObject("recipes",recipeList);//itemListをitems(list.htmlのth:eachのところ)に格納
		//mv.addObject("search", dish);//テキストボックスに保持(menu.htmlのth:valueで格納するキーをつくる)
		session.setAttribute("recipes", recipeList);
		mv.setViewName("recipeList");//list.htmlで表示

		return mv;
	}

	@RequestMapping(value = "{dish}")
	public ModelAndView details(
			@PathVariable(name = "dish")String dish ,
			ModelAndView mv	) {
		Optional<Recipe> recipe;
		recipe= recipeRepository.findByDish(dish);
		Recipe R=recipe.get();
			mv.addObject("recipe",R);

mv.setViewName("details");
		return mv;
	}
}
