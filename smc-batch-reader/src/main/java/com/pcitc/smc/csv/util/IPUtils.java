package com.pcitc.smc.csv.util;

import java.util.HashMap;
import java.util.Map;

public class IPUtils {

	private IPUtils() {
	}

	/**
	 * 判断ip是否为内网地址
	 * 
	 * @param ip
	 *            ip地址
	 * @return
	 */
	public static boolean judgeIPWhetherIntranet(String ip) {
		byte[] b = IPToBytes(ip);
		final byte b0 = b[0];
		final byte b1 = b[1];
		// 10.x.x.x/8
		final byte SECTION_1 = 0x0A;
		// 172.16.x.x/12
		final byte SECTION_2 = (byte) 0xAC;
		final byte SECTION_3 = (byte) 0x10;
		final byte SECTION_4 = (byte) 0x1F;
		// 192.168.x.x/16
		final byte SECTION_5 = (byte) 0xC0;
		final byte SECTION_6 = (byte) 0xA8;

		switch (b0) {
		case SECTION_1:
			return true;
		case SECTION_2:
			if (b1 >= SECTION_3 && b1 <= SECTION_4) {
				return true;
			}
		case SECTION_5:
			switch (b1) {
			case SECTION_6:
				return true;
			}
		default:
			return false;
		}
	}

	/***
	 * 格式化正常日期类型
	 * 
	 * @param strDate
	 * @return
	 */
	public static String splitDate(String strDate) {
		String[] strDates = strDate.split(" ");
		return strDates[2] + "-" + month(strDates[1]) + "-" + strDates[0] + " " + strDates[3];
	}

	/**
	 * 字符岳父转换数字月份
	 * 
	 * @param strMonth
	 * @return
	 */
	private static String month(String strMonth) {
		Map<String, String> monthMap = new HashMap<String, String>();
		monthMap.put("一月", "01");
		monthMap.put("二月", "02");
		monthMap.put("三月", "03");
		monthMap.put("四月", "04");
		monthMap.put("五月", "05");
		monthMap.put("六月", "06");
		monthMap.put("七月", "07");
		monthMap.put("八月", "08");
		monthMap.put("九月", "09");
		monthMap.put("十月", "10");
		monthMap.put("十一月", "11");
		monthMap.put("十二月", "12");
		return monthMap.get(strMonth);
	}

	/***
	 * ip转byte
	 * 
	 * @param ip
	 * @return
	 */
	private static byte[] IPToBytes(String ip) {
		byte[] b = new byte[4];
		String[] ip_arr = ip.split("\\.");
		for (int i = ip_arr.length - 1; i > -1; i--) {
			b[i] = new Integer(ip_arr[i]).byteValue();
		}
		return b;
	}
}
