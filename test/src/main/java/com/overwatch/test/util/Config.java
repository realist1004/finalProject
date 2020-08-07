package com.overwatch.test.util;


public class Config {

	public static final String appid = "TheEarlyTimes";

	public static final String apikey = "120c470c73c011eaa9c40cc47a1fcfae";

	public static String content = "나는 유리를 먹을 수 있어요. 그래도 아프지 않아요";

	public static final String sender = "01020361296";

	public static String receiver = "01020361296";

	
	public static String getContent() {
		return content;
	}

	public static void setContent(String content) {
		Config.content = content;
	}

	public static String getReceiver() {
		return receiver;
	}

	public static void setReceiver(String receiver) {
		Config.receiver = receiver;
	}
	
	

}
