package com.shuijiaowo.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckString {

	// ��֤�Ƿ�Ϊ�ֻ�����
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

	// ��֤�Ƿ�Ϊ4λ������֤��
	public static boolean isCodeOk(String codeString) {

		// �ж���֤��Ŀ�
		return true;

	}

}
