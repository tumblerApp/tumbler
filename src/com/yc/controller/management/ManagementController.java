package com.yc.controller.management;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;

@Controller
@RequestMapping("/management")
public class ManagementController {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ManagementController.class);

	@Autowired
	IAppUserService userService;
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("management/index", null);
	}

	@RequestMapping(value = "userList", method = RequestMethod.GET)
	public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AppUser> list = userService.getAll();
		ModelMap mode = new ModelMap();
		mode.put("list", list);
		return new ModelAndView("management/userList", mode);
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.GET)
	public ModelAndView updateUser(Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppUser user = userService.findById(id);
		ModelMap mode = new ModelMap();
		mode.put("user", user);
		return new ModelAndView("management/updateUser", mode);
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public String updateUsers(String loginName, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppUser user = userService.getUser(loginName);
		if (user != null) {
			user.setPassword(password);
			userService.update(user);
		}
		return "redirect:/management/userList";
	}

	@RequestMapping(value = "deleteUser", method = RequestMethod.GET)
	public String deleteUser(Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userService.delete(id);
		return "redirect:/management/userList";
	}
}
