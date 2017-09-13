package com.wdxxs2z.zuul.utils;

import java.util.Set;

public class StringTools {
	
	public static String converSetToString(Set<String> converSet){
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		int index = 1;
		for (String s : converSet) {
			if (index != converSet.size()) {
				sb.append(s).append(",");
			}else{
				sb.append(s);
			}
			index++;
		}
		sb.append("'");
		return sb.toString();
	}

}
