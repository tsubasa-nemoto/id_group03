package com.example.demo;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RecipeRepository recipeRepository;

	/**
	 * ログイン画面を表示
	 */
	@RequestMapping("/login")
	public String login(@RequestParam(name = "prev", defaultValue = "/main") String prev) {
		// セッション情報はクリアする
		System.out.println(session.getAttribute("prev"));
		String pre = (String) session.getAttribute("prev");
		session.invalidate();
		System.out.println(prev);
		System.out.println(pre);
		if (pre == null) {
			session.setAttribute("prev", prev);
		} else {
			session.setAttribute("prev", pre);
		}
		System.out.println(session.getAttribute("prev"));
		return "login";
	}

	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView mv,
			@RequestParam(name = "prev", defaultValue = "/main") String prev) {
		// userInfoのセッション情報はクリアする

		session.removeAttribute("userInfo");
		mv.setViewName("redirect:" + prev);

		return mv;
	}

	/**
	 * ログインを実行
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			ModelAndView mv) {
		// emailが空の場合にエラーとする
		if (email == null || email.length() == 0) {
			mv.addObject("message", "メールアドレスを入力してください");
			mv.setViewName("login");
			return mv;
		}

		// passwordが空の場合にエラーとする
		else if (password == null || password.length() == 0) {
			mv.addObject("message", "パスワードを入力してください");
			mv.setViewName("login");
			return mv;
		} else {
			Users user = null;
			Optional<Users> record = usersRepository.findByEmail(email);

			if (record.isEmpty() == false) {//アドレスが存在する場合

				user = record.get();

				String truePass = user.getPassword();
				if (truePass.equals(password)) {
					// セッションスコープにログイン名とカテゴリ情報を格納する
					session.setAttribute("userInfo", user);
					String prev = (String) session.getAttribute("prev");
					session.removeAttribute("prev");
					System.out.println(prev);
					mv.setViewName("redirect:" + prev);

					return mv;
				}
				// 空の場合にエラーとする
				else {
					mv.addObject("message", "パスワードが違います");
					mv.setViewName("login");

				}
			} else {
				mv.addObject("message", "メールアドレスが登録されていません");
				mv.setViewName("login");

			}
		}
		return mv;
	}

	//		// セッションスコープにログイン名とカテゴリ情報を格納する
	//		session.setAttribute("email", email);
	//		session.setAttribute("password", password);
	//		//session.setAttribute("categories", categoryRepository.findAll());
	//
	//		mv.setViewName("TOP");
	//		return mv;
	//	}
	/**
	 * 新規登録画面を表示
	 */
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public ModelAndView doSignup(ModelAndView mv,
			@RequestParam("name") String name,
			@RequestParam("email") String mail,
			@RequestParam("password") String password) {
		ArrayList<Users> users = (ArrayList<Users>) usersRepository.findAll();
		for (Users u : users) {
			if (u.getEmail().equals(mail)) {//USERSに含まれるidと一致するものの検索
				String mes = "登録済みのメールアドレスです";
				mv.addObject("message", mes);
				mv.setViewName("singup");
			} else {
				Users user = new Users(name, mail, password);
				usersRepository.saveAndFlush(user);
				mv.setViewName("/login");
			}
			return mv;

		}
		return mv;
	}

	@RequestMapping("/password")
	public ModelAndView d(ModelAndView mv) {
		mv.setViewName("password");
		return mv;
	}

	@PostMapping("/password")
	public ModelAndView pass(ModelAndView mv,
			@RequestParam("name") String name,
			@RequestParam("email") String mail) {
		ArrayList<Users> users = (ArrayList<Users>) usersRepository.findAll();
		for (Users u : users) {
			if (u.getEmail().equals(mail)) {
				if (u.getName().equals(name)) {
					//ユーザーの名前とアドレスが一致したらパスワードの再登録画面へ
					String tMail = u.getEmail();
					String tName = u.getName();
					session.setAttribute("mail", tMail);
					session.setAttribute("name", tName);
					//セッションスコープに名前とアドレスを保持
					mv.setViewName("rePass");
					return mv;
				} else {
					mv.addObject("message", "メールアドレスまたは名前が違います。");
					mv.setViewName("/password");
					return mv;
				}
			}
			//			else {
			//				mv.addObject("message", "メールアドレスが違います。");
			//				mv.setViewName("/password");
			//				return mv;
			//			}
		}
		return mv;
	}

	@PostMapping("/repass")
	public ModelAndView repass(ModelAndView mv,
			@RequestParam("pass1") String pass1,
			@RequestParam("pass2") String pass2) {
		if (pass1.equals(pass2)) {//passwordに間違いがないか確認
			String name = (String) session.getAttribute("name");
			String mail = (String) session.getAttribute("mail");
			Optional<Users> record = usersRepository.findByEmail(mail);
			//重複しないであろうアドレスでcodeを呼び出すための操作
			Users user = record.get();
			int code = user.getCode();
			Users reuser = new Users(code, name, mail, pass1);
			usersRepository.saveAndFlush(reuser);
			mv.addObject("message", "パスワードの再登録が完了しました。");
			mv.setViewName("login");
		} else {
			mv.addObject("message", "パスワードが一致しません。");
			mv.setViewName("rePass");
		}
		return mv;
	}

	@RequestMapping("/main")
	public ModelAndView top(ModelAndView mv) {
		//ランダム要素の追加の検討
		//DBから要素の取り出しセットを行う
		ArrayList<Recipe> recipe = (ArrayList<Recipe>) recipeRepository.findAll();
		session.setAttribute("Frag",false);
		session.setAttribute("Recipe", recipe);
		mv.setViewName("top");
		return mv;
	}

	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv) {
		ArrayList<Recipe> recipe = (ArrayList<Recipe>) recipeRepository.findAll();

		session.setAttribute("Recipe", recipe);
		//		session.setAttribute("URL2", "lime-juice.jpg");
		//		session.setAttribute("URL3", "pumpkin-soup.jpg");
		//		session.setAttribute("URL4", "small-salad.jpg");}
		mv.addObject("URL", "pancake.jpg");
		mv.addObject("recipe", recipe);
		mv.setViewName("TEST01");
		return mv;
	}
}
