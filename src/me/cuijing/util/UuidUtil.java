package me.cuijing.util;

import java.util.UUID;

/**
 * UuidUtil
 * @author CuiJing 
 * @date   2016/4/20
 */
public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
}

