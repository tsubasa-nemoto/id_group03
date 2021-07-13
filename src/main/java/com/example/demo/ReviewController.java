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
public class ReviewController {
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	HttpSession session;


	//@RequestMapping("{dish}/review")
	//public ModelAndView review(ModelAndView mv,
	//		@PathVariable(name="dish")String dish) {
	//
	//	mv.setViewName("review");
	//	return mv;
	//}

	@RequestMapping("{dish}/review")
	public ModelAndView review(ModelAndView mv,
			@PathVariable(name="dish")String dish)
	{


			Optional<Recipe>recipe= recipeRepository.findByDish(dish);
			Recipe R=recipe.get();
			List<Review>Rlist=reviewRepository.findByCode(R.getCode());
			 Users user=(Users)session.getAttribute("userInfo");
			if(user!=null) {
				String U = user.getName();
			mv.addObject("mes","ユーザー名:"+U);
			}else {
				mv.addObject("mes","入力するにはログインしてください");
			}
			mv.addObject("recipe",R);
			mv.addObject("reviewList",Rlist);
			mv.setViewName("review");

		return mv;
	}
}
