package com.yc.controller.response;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yc.entity.user.AppUser;
import com.yc.entity.user.Sex;
import com.yc.service.IAppUserService;
import com.yc.service.impl.TumblerService;
import com.yc.util.ServiceException;
import com.yc.util.TumblerUtil;

@Controller
@RequestMapping("/request/user")
public class ReponseUserController {
	@Autowired
	IAppUserService appUserService;
	
	@Resource
	TumblerService tumblerService;
 	
	/**登陆验证
	 * @param userName 手机号
	 * @param password 密码
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> login(@RequestParam(value="userName")String userName,
			@RequestParam(value="password")String password) throws ServletException, IOException {
 		
 		ModelMap mode = new ModelMap();
 		AppUser user = appUserService.getUser(userName);
 		String pwd = KL(JM(KL(MD5(password))));
 		if ( user != null ) {
 			if ( pwd.equals(user.getPassword()) ) {
 				mode.put("message", "success");
 			} else {
 				mode.put("message", "fail");
 			}
 		} else {
 			mode.put("message", "fail");
 		}
		return mode;
	}
 	
 	/**注册
 	 * @param phone 手机号
 	 * @param password 密码
 	 * @return
 	 * @throws ServletException
 	 * @throws IOException
 	 */
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
 			appUser.setPassword(KL(MD5(password)));
 			appUserService.save(appUser);
 			
 			mode.put("message", "success");
 		}
		return mode;
	}
 	
 	/**发送验证码到手机，用于注册
 	 * @param phone 手机号
 	 * @return
 	 * @throws ServletException
 	 * @throws IOException
 	 */
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
 	
 	/**进入个人信息时初始化信息
 	 * @param userName 手机号
 	 * @return
 	 * @throws Exception
 	 */
 	@RequestMapping(value = "isActivate", method = { RequestMethod.GET, RequestMethod.POST })
 	@ResponseBody
	public Map<String, Object> isActivate(String userName) throws Exception {
 		ModelMap mode = new ModelMap();
		AppUser user = appUserService.getUser(userName);
		mode.put("phone", user.getPhone());
		if ( user.getStatus() != null ) {
			if ( user.getStatus() ) {
				mode.put("status", "already");
			} else if ( user.getStatus() ) {
				mode.put("status", "no");
			}
		} else {
			mode.put("status", null);
		}
		
		if ( user.getUserName() != null ) {
			mode.put("userName", user.getUserName());	
		} else {
			mode.put("userName", null);
		}
			
		if ( user.getSex() != null ) {
			if ( user.getSex().toString().equals("Male") ) {
				mode.put("sex", "男");
			} else if ( user.getSex().toString().equals("Female") ) {
				mode.put("sex", "女");
			} else {
				mode.put("sex", "other");
			}
		}
		
		if ( user.getBirthday() != null ) {
			mode.put("birthday", user.getBirthday());
		} else {
			mode.put("birthday", null);
		}
		
		if ( user.getEmail() != null ) {
			mode.put("email", user.getEmail());
		} else {
			mode.put("email", null);
		}
		return mode;
	}
 	
 	/**绑定邮箱，发送激活验证到邮箱中
 	 * @param email 邮箱
 	 * @param userName 手机
 	 * @throws Exception
 	 */
 	@RequestMapping(value = "binding", method = { RequestMethod.GET, RequestMethod.POST })
 	@ResponseBody
	public void load(String email, String userName) throws Exception {
		AppUser user = appUserService.getUser(userName);
		tumblerService.sendEmail(email, user);// 发邮箱激活
	}
 	
 	/**激活邮箱
 	 * @param email 邮箱
 	 * @param validateCode 激活码
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	@RequestMapping(value = "activate", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView activate(String email, String validateCode,HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
 		ModelAndView mav = new ModelAndView();
 		try {
			tumblerService.processActivate(email, validateCode);// 调用激活方法
			mav.setViewName("user/activate_success");
		} catch (ServiceException e) {
			request.setAttribute("message", e.getMessage());
			mav.setViewName("user/activate_failure");
		}
		return mav;
	}
 	
 	/**保存个人信息
 	 * @param nickname 昵称
 	 * @param sex 性别
 	 * @param birthday 生日
 	 * @param userName 手机号
 	 * @throws Exception
 	 */
 	@RequestMapping(value = "savePersonalInfo", method = { RequestMethod.GET, RequestMethod.POST })
 	@ResponseBody
	public void savePersonalInfo(String nickname, String sex, String birthday
			,String userName) throws Exception {
		AppUser user = appUserService.getUser(userName);
		user.setUserName(nickname);
		if ( sex.equals("男") ) {
			user.setSex(Sex.Male);
		} else if ( sex.equals("女") ) {
			user.setSex(Sex.Female);
		}
		user.setBirthday(birthday);
		appUserService.update(user);
	}
 	
 	/**
 	 * 用户修改密码
 	 * @param previousPsw 原密码
 	 * @param currentPsw 当前密码
 	 * @param userName 手机号
 	 * @return
 	 * @throws Exception
 	 */
 	@RequestMapping(value = "modifyPassword", method = { RequestMethod.GET, RequestMethod.POST })
 	@ResponseBody
	public Map<String, Object> modifyPassword(String previousPsw, String currentPsw,
			String userName) throws Exception {
 		ModelMap mode = new ModelMap();
		AppUser user = appUserService.getUser(userName);
		String previousPswMD5 = KL(JM(KL(MD5(previousPsw))));
		String currentPswMD5 = KL(JM(KL(MD5(currentPsw))));
		if ( !previousPswMD5.equals(user.getPassword()) ) {
			mode.put("message", "pswError");
		} else {
			user.setPassword(currentPswMD5);
			appUserService.update(user);
			mode.put("message", "success");
		}
		return mode;
	}
 	
	/**MD5加码。32位
	 * @param inStr 密码
	 * @return
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	// 可逆的加密算法
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	// 加密后解密
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}
}