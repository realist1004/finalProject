

package com.overwatch.test.util;

public class MakeCertifiNum {
	
	public String makeNum() {
		
		String result = "";
		int num = 0;
		
		for(int i=0; i<4; i++) {
		num = (int)(Math.random()*9)+0;
		
		result += String.valueOf(num);
		}
		
		return result;
	}
}
