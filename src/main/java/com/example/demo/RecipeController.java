package com.example.demo;

import java.util.ArrayList;
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


	boolean count=false;
	/**
	 * レシピ検索の料理を表示
	 */
	@RequestMapping("/search")
	public ModelAndView searchitems(
			@RequestParam(name = "search", defaultValue = "") String dish,
			ModelAndView mv) {
		//aがtrueなら遷移前ページの検索情報を取得


		boolean a = (boolean) session.getAttribute("Frag");

		boolean b = false;
		if (a) {
			if (dish.equals(null) || dish.length() == 0) {
				dish = (String) session.getAttribute("searchResult");
			}
			a = false;
			session.removeAttribute("searchResult");
			session.setAttribute("Frag", a);
			b = true;
		}

		List<Favorite> list;
		Users user = (Users) session.getAttribute("userInfo");
		if (user != null) {
			list = favoriteRepository.findByEmail(user.getEmail());
		} else {
			list = favoriteRepository.findAll();
		}
		session.setAttribute("favorite", list);

		List<Recipe> recipeList = null;//nullを入れて中身を空っぽに
		recipeList = recipeRepository.findAll();
		boolean fMenu = (boolean) session.getAttribute("FMenu");

		if (dish == null || dish.length() == 0) {//すべて空白だったら
			recipeList = recipeRepository.findAll();//全件検索
			mv.addObject("mes", "レシピ一覧");
			session.setAttribute("mes", "レシピ一覧");
			System.out.println(fMenu+"75");
			}

		else if (!dish.equals("")) {//nameが空白でないとき
			recipeList = recipeRepository.findByDishLike("%" + dish + "%");//あいまい検索で表示
			mv.addObject("mes", dish + "の検索結果");

			if (b == false) {//検索が一回目なら
				session.setAttribute("searchResult", dish);
				fMenu=false;
				System.out.println(fMenu+"84");
			}
		}
		mv.addObject("recipes", recipeList);
System.out.println(fMenu);

		//お気に入りページでお気に入り解除した場合の処理
		if (fMenu==true) {
			List<Favorite> Favolist = favoriteRepository.findByEmailAndFav(user.getEmail(), true);
			int num = 0;
			List<Recipe> RList = new ArrayList<Recipe>();
			for (Favorite L : Favolist) {//お気に入りレシピを呼び出す（テーブルが別のため）
				List<Recipe> LL = recipeRepository.findByDishLike(L.getDish());
				if (num != 0) {//一回目かの判定
					RList = (List<Recipe>) session.getAttribute("LL");
					RList.addAll(LL);
					session.setAttribute("LL", RList);
				} else {
					session.setAttribute("LL", LL);
				}

				num++;
			}
			RList=(List<Recipe>) session.getAttribute("LL");
			mv.addObject("mes", "お気に入り");
			mv.addObject("recipes",RList );
			fMenu=false;
		}
		System.out.println(fMenu);
		session.setAttribute("FMenu", fMenu);
		session.setAttribute("recipes", recipeList);
		mv.setViewName("/recipeList");//recipeList.htmlで表示

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

	@RequestMapping("{dish}/favorite") //お気に入り用メソッド
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
			List<Favorite> LFavorite = favoriteRepository.findByDishAndEmailLike(dish, user.getEmail());

			if (LFavorite.isEmpty() == true || LFavorite.size() == 0) {
				Favorite FAV = new Favorite(dish, user.getEmail(), false);
				favoriteRepository.saveAndFlush(FAV);
			}
			//お気に入りの情報取得
			Optional<Favorite> Fdish = favoriteRepository.findByDishAndEmail(dish, user.getEmail());

			//更新のためのデータ
			Favorite F = Fdish.get();
			int id = F.getId();
			if (F.getFav()) {
				fav = false;
			} else {
				fav = true;
			}

			//お気に入り情報の更新
			Favorite FAV = new Favorite(id, dish, user.getEmail(), fav);
			favoriteRepository.saveAndFlush(FAV);
		}
		//表示用処理

		//お気に入り登録しているリストの用意

		//検索の実行の確認
		boolean frg;
		if (prev.equals("/search")) {
			frg = true;
		} else {
			frg = false;
		}
		Optional<Favorite> Fdish = favoriteRepository.findByDishAndEmail(dish, user.getEmail());
		Favorite favo = Fdish.get();
		session.setAttribute("fav", favo.getFav());
		session.setAttribute("Frag", frg);
		mv.setViewName("redirect:" + Return);
		count=true;
		return mv;
	}


	//お気に入りの条件検索
	@RequestMapping("/favomenu") //お気に入りページ
	public ModelAndView favomenu(@RequestParam(name = "prev", defaultValue = "/main") String prev,
			ModelAndView mv) {
		Users user = (Users) session.getAttribute("userInfo");
		int result = prev.indexOf("0/") + 1;
		String Return = prev.substring(result);//前のページの8080/以降のデータ取得

		if (user == null) {
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
		} else {
			//DBの中のお気に入りされているものを呼び出す

			List<Favorite> list = favoriteRepository.findByEmailAndFav(user.getEmail(), true);
			int num = 0;
			for (Favorite L : list) {//お気に入りレシピを呼び出す（テーブルが別のため）
				List<Recipe> LL = recipeRepository.findByDishLike(L.getDish());

				List<Recipe> RList = new ArrayList<Recipe>();

				if (num != 0) {//一回目かの判定
					RList = (List<Recipe>) session.getAttribute("LL");
					RList.addAll(LL);
					session.setAttribute("LL", RList);
				} else {
					session.setAttribute("LL", LL);
				}

				num++;
			}

			List<Recipe> Rlist = (List<Recipe>) session.getAttribute("LL");

			session.setAttribute("FMenu", true);
			mv.addObject("recipes", Rlist);
			mv.addObject("mes", "お気に入り");
			mv.setViewName("recipeList");

		}

		return mv;
	}
}
