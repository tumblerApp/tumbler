package com.yc.controller.response;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;
import com.yc.util.TumblerUtil;

@Controller
@RequestMapping("/request/user")
public class ReponseUserController {
	@Autowired
	IAppUserService appUserService;
 	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> login(@RequestParam(value="userName")String userName,
			@RequestParam(value="password")String password) throws ServletException, IOException {
 		
 		ModelMap mode = new ModelMap();
 		AppUser user = appUserService.getUser(userName);
 		if ( user != null ) {
 			if ( password.equals(user.getPassword()) ) {
 				mode.put("message", "success");
 			} else {
 				mode.put("message", "fail");
 			}
 		} else {
 			mode.put("message", "fail");
 		}
		return mode;
	}
 	
 	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> register(@RequestParam(value="phone")String phone,
			@RequestParam(value="password")String password) throws ServletException, IOException {
 		ModelMap mode = new ModelMap();
 		AppUser user = appUserService.getUser(phone);
 		if ( user != null ) {
 			mode.put("message", "existed");
 		} else {
 			AppUser appUser = new AppUser();
 			appUser.setPhone(phone);
 			appUser.setPassword(password);
 			appUserService.save(appUser);
 			
 			mode.put("message", "success");
 		}
		return mode;
	}
 	
 	@RequestMapping(value = "smsVerification", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> smsVerification(@RequestParam(value="phone")String phone) throws ServletException, IOException{
 		ModelMap mode = new ModelMap();
		try {
			mode = TumblerUtil.sendSmsVerification(phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mode;
	}
}

