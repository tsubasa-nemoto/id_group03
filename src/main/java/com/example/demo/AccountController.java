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

	/**
	 * ログイン画面を表示
	 */
	@RequestMapping("/login")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "login";
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
					//session.setAttribute("categories", categoryRepository.findAll());

					mv.setViewName("top");
					return mv;
				}
				// 空の場合にエラーとする
				else {
					mv.addObject("message", "パスワードが違います");
					mv.setViewName("login");

				}
			}else {
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
		ArrayList<Users> users= (ArrayList<Users>)usersRepository.findAll();
		for (  Users u:users) {
			if (u.getEmail().equals(mail)) {//USERSに含まれるidと一致するものの検索
				String mes = "登録済みのメールアドレスです";
				mv.addObject("message",mes );
			mv.setViewName("singup");
			}else {
				Users user = new Users(name,mail,password);
			usersRepository.saveAndFlush(user);
				mv.setViewName("/login");
			}
			return mv;

		}
		return mv;
	}
}
