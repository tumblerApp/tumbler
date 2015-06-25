package com.yc.util;

import java.security.MessageDigest;

public class MD5Util {
	/**
	 * 将源字符串使用MD5加密为字节数组
	 * 
	 * @param source
	 * @return
	 */
	public static byte[] encode2bytes(String source) throws Exception {
		byte[] result = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(source.getBytes("UTF-8"));
		result = md.digest();
		return result;
	}

	/**
	 * 将源字符串使用MD5加密为32位16进制数
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String encode2hex(String source) throws Exception {
		byte[] data = encode2bytes(source);
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);

			if (hex.length() == 1) {
				hexString.append('0');
			}

			hexString.append(hex);
		}

		return hexString.toString();
	}

	/**
	 * 验证字符串是否匹配
	 * 
	 * @param unknown
	 *            待验证的字符串
	 * @param okHex
	 *            使用MD5加密过的16进制字符串
	 * @return 匹配返回true，不匹配返回false
	 * @throws Exception 
	 */
	public static boolean validate(String unknown, String okHex) throws Exception {
		return okHex.equals(encode2hex(unknown));
	}

}
