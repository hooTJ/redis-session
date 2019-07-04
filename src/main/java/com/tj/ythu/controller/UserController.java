package com.tj.ythu.controller;

import com.alibaba.fastjson.JSONObject;
import com.tj.ythu.interceptor.LoginAnnotation;
import com.tj.ythu.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index() {

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String name, String password, HttpServletRequest request) {
		if ("".equals(name)) {
			request.setAttribute("errorMsg", "用户名不能为空");
			return "index";
		}

		if (!"root".equals(name)) {
			request.setAttribute("errorMsg", "用户名不存在");
			return "index";
		}

		if ("".equals(password)) {
			request.setAttribute("errorMsg", "密码不能为空");
			return "index";
		}

		if (!"123456".equals(password)) {
			request.setAttribute("errorMsg", "密码不正确");
			return "index";
		}

		User user = new User();
		user.setId(1);
		user.setName(name);
		user.setPassword(password);
		request.getSession().setAttribute("user_info", JSONObject.toJSON(user));

		return "redirect:home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@LoginAnnotation(isNeed = true)
	public String home(HttpServletRequest request) {
		String userInfo = request.getSession().getAttribute("user_info")
				.toString();
		User user = JSONObject.parseObject(userInfo, User.class);
		request.setAttribute("user", user);

		return "home";
	}
}
