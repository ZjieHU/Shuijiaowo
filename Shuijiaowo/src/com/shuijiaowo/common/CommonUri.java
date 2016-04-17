package com.shuijiaowo.common;

public class CommonUri {

	// 访问服务器基础链接
	public static final String BASE_URL = "http://192.168.43.47:8080/ShuijiaowoServer/";

	// 获取验证码
	public static final String CODE_URL = BASE_URL + "CodeServlet";

	// 登录
	public static final String LOGIN_URL = BASE_URL + "LoginServlet";

	// 闹钟列表
	public static final String CLOCK_LIST_URL = BASE_URL + "ClockListServlet";

	// 增加或修改
	public static final String CLOCK_SAVE_URL = BASE_URL + "ClockSaveServlet";

	// 闹钟函数
	public static final String CLOCK_DELETE_URL = BASE_URL
			+ "ClockDeleteServlet";

	// 账号信息
	public static final String ACCOUNT_URL = BASE_URL + "AccountServlet";

	// 我的录音列表
	public static final String VIDEO_ME_URL = BASE_URL + "VideoMeServlet";

	// 上传录音
	public static final String VIDEO_UP_URL = BASE_URL + "VideoUpServlet";

	// 删除录音
	public static final String VIDEO_DELETE_URL = BASE_URL
			+ "VideoDeleteServlet";

	// 下载录音
	public static final String VIDEO_DOWN_URL = BASE_URL + "VideoDownServlet";

	// 启动页下载
	public static final String VIDEO_START_URL = BASE_URL + "VideoStartServlet";

	// Banner下载
	public static final String VIDEO_BANNER_URL = BASE_URL
			+ "VideoBannerServlet";

}
