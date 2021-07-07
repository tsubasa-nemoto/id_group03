package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;

	//@Autowired
	//UserRepository userRepository;

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
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			ModelAndView mv
	) {
		// emailが空の場合にエラーとする
		if(email == null || email.length() == 0) {
			mv.addObject("message", "メールアドレスを入力してください");
			mv.setViewName("login");
			return mv;
		}

		// passwordが空の場合にエラーとする
		else if(password == null || password.length() == 0) {
			mv.addObject("message", "パスワードを入力してください");
			mv.setViewName("login");
			return mv;
		}

		// セッションスコープにログイン名とカテゴリ情報を格納する
		session.setAttribute("email", email);
		session.setAttribute("password", password);
		//session.setAttribute("categories", categoryRepository.findAll());

		mv.setViewName("TOP");
		return mv;
	}
}
