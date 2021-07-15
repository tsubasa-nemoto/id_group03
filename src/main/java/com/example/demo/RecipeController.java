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
	FavoriteRepository favoriteRepository;
	@Autowired
	HttpSession session;

	/**
	 * レシピ検索の料理を表示
	 */
	@RequestMapping("/search")
	public ModelAndView searchitems(
			@RequestParam(name = "search", defaultValue = "") String dish,
			ModelAndView mv) {
		boolean a =(boolean)session.getAttribute("Frag");
		if(a) {
			dish=(String)session.getAttribute("searchResult");
			a=false;
			session.setAttribute("Frag", a);
		}

		List<Recipe> recipeList = null;//nullを入れて中身を空っぽに
		recipeList = recipeRepository.findAll();

		System.out.println(dish);
		if (dish.equals("")) {//すべて空白だったら
			recipeList = recipeRepository.findAll();//全件検索
			mv.addObject("mes", "レシピ一覧");
			session.setAttribute("mes", "レシピ一覧");
		} else if (!dish.equals("")) {//nameが空白でないとき
			recipeList = recipeRepository.findByDishLike("%" + dish + "%");//あいまい検索で表示
			mv.addObject("mes", dish + "の検索結果");
			session.setAttribute("searchResult",dish);
		}

		mv.addObject("recipes", recipeList);//itemListをitems(list.htmlのth:eachのところ)に格納
		//mv.addObject("search", dish);//テキストボックスに保持(menu.htmlのth:valueで格納するキーをつくる)
		session.setAttribute("recipes", recipeList);
		mv.setViewName("recipeList");//list.htmlで表示

		return mv;
	}

	@RequestMapping(value = "{dish}/details")
	public ModelAndView details(
			@PathVariable(name = "dish") String dish,
			ModelAndView mv) {
		Optional<Recipe> recipe = recipeRepository.findByDish(dish);
		Recipe R = recipe.get();
		List<Review> review = reviewRepository.findByCode(R.getCode());

		mv.addObject("reviewList", review);
		mv.addObject("recipe", R);

		mv.setViewName("details");
		return mv;
	}

	@RequestMapping("{dish}/favorite")
	public ModelAndView fav(@PathVariable(name = "dish") String dish,
			@RequestParam(name = "prev", defaultValue = "/main") String prev,
			ModelAndView mv) {
		System.out.println(prev);	//前のページの情報を保持
		Users user = (Users) session.getAttribute("userInfo");
		boolean fav;
		int result = prev.indexOf("0/") + 1;
		String Return = prev.substring(result);
		if (user == null) {
			mv.addObject("message", "お気に入り機能を利用するにはログインしてください");
			mv.setViewName("login");

			session.setAttribute("prev",Return ); //searchのためのリターンの値
			System.out.println(session.getAttribute("searchResult"));
			boolean frg=true;//検索の実行の確認
			session.setAttribute("Frag",frg);
			return mv;

		} else {
			List<Favorite> LFavorite = favoriteRepository.findByDishAndNameLike(dish, user.getName());
			if (LFavorite == null) {
				Favorite FAV = new Favorite(dish, user.getName(), false);
				favoriteRepository.saveAndFlush(FAV);
			}

			Optional<Favorite> Fdish = favoriteRepository.findByDishAndName(dish, user.getName());
			Favorite F = Fdish.get();
			int id = F.getId();

			if (F.getFav()) {
				fav = false;
			} else {
				fav = true;
			}
			Favorite FAV = new Favorite(id, dish, user.getName(), fav);
			favoriteRepository.saveAndFlush(FAV);
		}


		session.setAttribute("fav", fav);
		List<Favorite> list = favoriteRepository.findByFav(true);
		session.setAttribute("favorite", list);
		//お気に入り登録しているかの確認

		boolean frg=true;//検索の実行の確認
		session.setAttribute("Frag",frg);

		System.out.println(Return);
		mv.setViewName(Return);
		return mv;
	}
}
