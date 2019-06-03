package com.example.ray.playviewandroid.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**MD5加密工具类
 */
public class MD5Util {

	/**
	 * 对字符串进行 MD5 加密
	 * 
	 * @param str
	 *            待加密字符串
	 * @return 加密后字符串
	 */
	public static String MD5(String str) {
		if (!StringUtil.isNotEmpty(str, false)) {
			return "";
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		byte[] encodedValue = md5.digest();
		int j = encodedValue.length;
		char finalValue[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte encoded = encodedValue[i];
			finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
			finalValue[k++] = hexDigits[encoded & 0xf];
		}

		return new String(finalValue);
	}
}
