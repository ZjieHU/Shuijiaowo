package com.shuijiaowo.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckString {

	// 验证是否为手机号码
	public static boolean isMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("(^(13\\d|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[^346,\\D]\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// 验证是否为4位数字验证码
	public static boolean isCodeOk(String codeString) {

		// 判断验证码的快
		return true;

	}

}
