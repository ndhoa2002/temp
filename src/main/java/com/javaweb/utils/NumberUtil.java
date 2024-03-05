package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNumber(String value) {
		try {
			 Long number = Long.parseLong(value);
		} catch (NumberFormatException ex) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
