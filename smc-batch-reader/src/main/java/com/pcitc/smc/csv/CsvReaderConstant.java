package com.pcitc.smc.csv;

import java.util.ArrayList;
import java.util.List;

import com.pcitc.smc.csv.model.ESMEvent;

public class CsvReaderConstant {
	
	//1.将CSV文件接收目录的文件复制到目录下的Done文件夹下，读取时在Done下读取，读取完毕删除源文件
	//public static final String FILE_DONE_DIR="done/";
	
	public static long count1 = 0;//过滤掉ip和目标ip为空的数量
	public static long count2 = 0;//不是时候ip的数量
	
	public static String logSize = "";//以兆为单位M
	
	public static List<ESMEvent> eventList = new ArrayList<ESMEvent>();
	
	public static String delete = "";//是否删除源文件
	
	public static String sort = "";//排序顺序
	
	public static String javaVersion = "";//java版本
	
	public static String lang = "";//编码
	
}
