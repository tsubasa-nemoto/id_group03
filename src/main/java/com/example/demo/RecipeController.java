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
		//sessionが途切れた場合の処理

		System.out.println(dish + "dish38");
		//aがtrueなら遷移前ページの検索情報を取得
		boolean a = (boolean) session.getAttribute("Frag");

		boolean b = false;

					if (a) {
							if (dish.equals(null) || dish.length() == 0) {
								dish = (String) session.getAttribute("searchResult");
							}
							else {
								session.removeAttribute("searchResult");
							}

							a = false;
					session.removeAttribute("searchResult");
					session.setAttribute("Frag", a);
					b = true;
					}

		List<Recipe> recipeList = null;//nullを入れて中身を空っぽに
		recipeList = recipeRepository.findAll();


		if (dish == null || dish.length() == 0) {//すべて空白だったら
			recipeList = recipeRepository.findAll();//全件検索
			mv.addObject("mes", "レシピ一覧");
			session.setAttribute("mes", "レシピ一覧");
		}

		else if (!dish.equals("")) {//nameが空白でないとき
			recipeList = recipeRepository.findByDishLike("%" + dish + "%");//あいまい検索で表示
			mv.addObject("mes", dish + "の検索結果");


			if (b == false) {//検索が一回目なら
				session.setAttribute("searchResult", dish);
			}
		}
		mv.addObject("recipes", recipeList);//itemListをitems(list.htmlのth:eachのところ)に格納
		//mv.addObject("search", dish);//テキストボックスに保持(menu.htmlのth:valueで格納するキーをつくる)
		session.setAttribute("recipes", recipeList);
		mv.setViewName("/recipeList");//list.htmlで表示



		return mv;
	}

	@RequestMapping(value = "{dish}/details")
	public ModelAndView details(
			@PathVariable(name = "dish") String dish,
			ModelAndView mv) {

		//商品詳細ページ
		Optional<Recipe> recipe = recipeRepository.findByDish(dish);
		Recipe R = recipe.get();
		List<Review> review = reviewRepository.findByCode(R.getCode());

		mv.addObject("reviewList", review);
		mv.addObject("recipe", R);

		mv.setViewName("details");
		return mv;
	}

	@RequestMapping("{dish}/favorite")	//お気に入り用メソッド
	public ModelAndView fav(@PathVariable(name = "dish") String dish,
			@RequestParam(name = "prev", defaultValue = "/main") String prev,
			ModelAndView mv) {

		Users user = (Users) session.getAttribute("userInfo");

		int result = prev.indexOf("0/") + 1;
		String Return = prev.substring(result);//前のページの8080/以降のデータ取得
		boolean fav;

		if (user == null) {//ログインの確認
			session.setAttribute("prev", Return); //searchのためのリターンの値

			boolean frg;
			if (prev.equals("/search")) {
				frg = true;//検索の実行の確認
			} else {
				frg = false;
			}
			session.setAttribute("Frag", frg);

			mv.addObject("message", "お気に入り機能を利用するにはログインしてください");
			mv.setViewName("login");
			return mv;

		}

		else {
			//新規登録（お気に入り）
			List<Favorite> LFavorite = favoriteRepository.findByDishAndNameLike(dish, user.getName());

			if (LFavorite.isEmpty() == true || LFavorite.size() == 0) {
				Favorite FAV = new Favorite(dish, user.getName(), false);
				favoriteRepository.saveAndFlush(FAV);
			}
			//お気に入りの情報取得
			Optional<Favorite> Fdish = favoriteRepository.findByDishAndName(dish, user.getName());

			//更新のためのデータ
			Favorite F = Fdish.get();
			int id = F.getId();
			if (F.getFav()) {
				fav = false;
			} else {
				fav = true;
			}

			//お気に入り情報の更新
			Favorite FAV = new Favorite(id, dish, user.getName(), fav);
			favoriteRepository.saveAndFlush(FAV);
		}
		//表示用処理
		session.setAttribute("fav", fav);
		List<Favorite> list = favoriteRepository.findByFav(true);
		session.setAttribute("favorite", list);
		//お気に入り登録しているリストの用意

		//検索の実行の確認
		boolean frg;
		if (prev.equals("/search")) {
			frg = true;
		} else {
			frg = false;
		}

		session.setAttribute("Frag", frg);
		mv.setViewName("redirect:" + Return);
		return mv;
	}
	@RequestMapping("/favomenu")
	public ModelAndView favomenu(@RequestParam(name = "prev", defaultValue = "/main") String prev,
		ModelAndView mv	) {
		Users user = (Users) session.getAttribute("userInfo");
		int result = prev.indexOf("0/") + 1;
		String Return = prev.substring(result);//前のページの8080/以降のデータ取得

		if(user==null)
		{
			session.setAttribute("prev", Return); //searchのためのリターンの値

			boolean frg;
			if (prev.equals("/search")) {
				frg = true;//検索の実行の確認
			} else {
				frg = false;
			}
			session.setAttribute("Frag", frg);

			mv.addObject("message", "お気に入り機能を利用するにはログインしてください");
			mv.setViewName("login");
		}else {
			String userName=user.getName();
			List<Favorite> list = favoriteRepository.findByNameAndFav(userName,true);
			session.setAttribute("List", "");
			List<Recipe> LL = null;//nullを入れて中身を空っぽに
			LL = recipeRepository.findAll();
			for(Favorite L:list) {

//
//				LL=recipeRepository.findByDishLike(list.getDish());
//
//
//
//				session.setAttribute("List", List);
			}
			List<Recipe> Rlist=(List<Recipe>)session.getAttribute("List");
			mv.addObject("recipes", Rlist);
			mv.addObject("mes","お気に入り");
			mv.setViewName("recipeList");

		}


return mv		;
	}
}
