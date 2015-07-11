package com.yc.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	IAppUserService userService;


	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("loginName");
		String pwd = request.getParameter("password");
		HttpSession session = request.getSession();
		AppUser personnel = userService.getUser(name);
		if (personnel == null) {
			request.getSession().setAttribute("message", "nouser");
			return "redirect:/login";
		} else {
			if (personnel.getPassword().equals(pwd.trim())) {
				session.setAttribute("loginUser", personnel);
				return "redirect:/management/index";
			} else {
				System.out.println("密码错误！！");
				request.getSession().setAttribute("message", "nouser");
				return "redirect:/login";
			}
		}
	}
	
	@RequestMapping(value = "regist", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("user/register");
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("loginUser");
		return "redirect:/login";
	}

	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public String registing(AppUser personnel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		userService.save(personnel);
		return "redirect:/login";
	}
}
