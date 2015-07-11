package com.yc.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.entity.user.AppUser;
import com.yc.service.IAppUserService;
import com.yc.util.MD5Util;
import com.yc.util.TumblerUtil;

@Service
public class TumblerService {
	
	private static final Logger LOG = Logger.getLogger(TumblerService.class);
	
	@Autowired
	IAppUserService userService;
	
	/**
	 * 处理邮件，发送激活码
	 * @param email 要绑定邮件
	 * @param user 绑定用户
	 * @throws Exception
	 */
	public void processregister(String email, AppUser user) throws Exception{
        user.setValidateCode(MD5Util.encode2hex(email));
        user.setEmail(email);
        user.setEmailBindTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setStatus(false);
        user = userService.update(user);//保存注册信息

        ///邮件的内容
        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新绑定邮件，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\"http://192.168.0.115:8080/user/binding?action=activate&email=");
        sb.append(email); 
        sb.append("&validateCode="); 
        sb.append(user.getValidateCode());
        sb.append("\">http://192.168.0.115:8080/user/binding?action=activate&email="); 
        sb.append(email);
        sb.append("&validateCode=");
        sb.append(user.getValidateCode());
        sb.append("</a>");

        //发送邮件
        TumblerUtil.send(email, sb.toString());
        LOG.info("发送邮件");
    }
	
	/**
	 * 传递激活码和email过来
	 * @param email 传递过来的Email
	 * @param validateCode 传递过来的激活码
	 * @throws ServiceException
	 * @throws ParseException
	 */
    public void processActivate(String email , String validateCode)throws Exception{  
         //数据访问层，通过email获取用户信息
        AppUser user=userService.getUserByEmail(email);
        //验证用户是否存在 
        if(user!=null) {  
            //验证用户激活状态  
            if(user.getStatus()==false) { 
                ///没激活
                Date currentTime = new Date();//获取当前时间  
                //验证链接是否过期 
                currentTime.before(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getEmailBindTime()));
                if(currentTime.before(user.getLastActivateTime())) {  
                    //验证激活码是否正确  
                    if(validateCode.equals(user.getValidateCode())) {  
                        //激活成功， //并更新用户的激活状态，为已激活 
                        System.out.println("==sq==="+user.getStatus());
                        user.setStatus(true);//把状态改为激活
                        System.out.println("==sh==="+user.getStatus());
                        userService.update(user);
                    } else {
                       throw new ServiceException("激活码不正确");  
                    }  
                } else { 
                	user.setEmail(null);
                	userService.update(user);
                	throw new ServiceException("激活码已过期！");  
                }  
            }  
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");  
        }  
    }
    
    
    
    
    
    
    //手机用户
    /**
	 * 处理邮件，发送激活码
	 * @param email 要绑定邮件 
	 * @param user 绑定用户
	 * @throws Exception
	 */
	public void sendEmail(String email, AppUser user) throws Exception{
        user.setValidateCode(MD5Util.encode2hex(email));
        user.setEmail(email);
        user.setEmailBindTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setStatus(false);
        user = userService.update(user);//保存注册信息

        ///邮件的内容
        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新绑定邮件，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\"http://192.168.0.107:8080/request/user/activate?email=");
        sb.append(email); 
        sb.append("&validateCode="); 
        sb.append(user.getValidateCode());
        sb.append("\">http://192.168.0.107:8080/request/user/activate?email="); 
        sb.append(email);
        sb.append("&validateCode=");
        sb.append(user.getValidateCode());
        sb.append("</a>");

        //发送邮件
        TumblerUtil.send(email, sb.toString());
        LOG.info("发送邮件");
    }
	
//    public String activateEmail(String email , String validateCode)throws Exception{  
//         //数据访问层，通过email获取用户信息
//        AppUser user=userService.getUserByEmail(email);
//        //验证用户是否存在 
//        if(user!=null) {  
//            //验证用户激活状态  
//            if(user.getStatus()==false) { 
//                ///没激活
//                Date currentTime = new Date();//获取当前时间  
//                //验证链接是否过期 
//                currentTime.before(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getEmailBindTime()));
//                if(currentTime.before(user.getLastActivateTime())) {  
//                    //验证激活码是否正确  
//                    if(validateCode.equals(user.getValidateCode())) {  
//                        //激活成功， //并更新用户的激活状态，为已激活 
//                        user.setStatus(true);//把状态改为激活
//                        userService.update(user);
//                    } else {
//                       return "激活码不正确";  
//                    }  
//                } else { 
//                	user.setEmail(null);
//                	userService.update(user);
//                	return "激活码已过期！";  
//                }  
//            }
//        } else {
//        	return "该邮箱未注册（邮箱地址不存在）！";  
//        }
//        return "success";
//    }
}
