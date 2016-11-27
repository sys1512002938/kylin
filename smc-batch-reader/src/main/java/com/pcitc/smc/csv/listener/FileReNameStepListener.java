package com.pcitc.smc.csv.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.core.io.FileSystemResource;

import com.pcitc.smc.csv.CsvReaderConstant;
import com.pcitc.smc.csv.model.ESMEvent;

import au.com.bytecode.opencsv.CSVWriter;

public class FileReNameStepListener extends CsvReaderConstant implements StepExecutionListener {

	Date startDate = null;
	Date endDate = null;
	String start = "";
	String end = "";
	String fileName = "";
	String timestamp = "";//用时
	String records = "";//csv文件记录数
	String filtration = "";//过滤条数
	String validRec = "";//有效条数
	static long filtrationOLD = 0;
	@Override
	public void beforeStep(StepExecution stepExecution) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startDate = new Date();
		start = sdf.format(startDate);
		
		// TODO Auto-generated method stub
		try {
			System.out.println("==================FileReNameStepListener.beforeStep()====================");
			//System.out.println("inputFile:" + stepExecution.getJobParameters().getString("inputFile"));
			System.out.println("inputDir:" + stepExecution.getJobParameters().getString("inputDir"));
			System.out.println("inputSrcFile:" + stepExecution.getJobParameters().getString("inputSrcFile"));
			String inputFile = stepExecution.getJobParameters().getString("inputFile");
			String inputSrcFile = stepExecution.getJobParameters().getString("inputSrcFile");
			String inputDir = stepExecution.getJobParameters().getString("inputDir");
			//File doneDir = new File(inputDir + CsvReaderConstant.FILE_DONE_DIR);
			
			/*if (!doneDir.exists()) {
				doneDir.mkdirs();
			}
			if (inputFile != null && !"".equals(inputFile)) {
				FileSystemResource fsr = new FileSystemResource(inputSrcFile);
				FileUtils.copyFile(fsr.getFile(), new File(inputFile));
			}*/
			System.out.println("==================FileReNameStepListener.beforeStep()====================");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("=================FileReNameStepListener.afterStep()=====================");
			System.out.println("inputSrcFile:" + stepExecution.getJobParameters().getString("inputSrcFile"));
			String inputSrcFile = stepExecution.getJobParameters().getString("inputSrcFile");
			FileSystemResource srcFile = new FileSystemResource(inputSrcFile);
			fileName = srcFile.getFile().getName();
			long record = getTotalLines(srcFile.getFile().getAbsolutePath())-1;//去除标题行数
			records = Long.toString(record);
			long filtrationNEW = CsvReaderConstant.count1+CsvReaderConstant.count2;
			long cha = filtrationNEW-filtrationOLD;
			
			//按单个文件写入
			for(long i=filtrationOLD;i<filtrationNEW;i++){
				ESMEvent event = CsvReaderConstant.eventList.get(new Long(i).intValue());
				writeSCV(event);
			}
			
			filtrationOLD = filtrationNEW;
			
			filtration = Long.toString(cha);
			validRec = Long.toString(record-cha);
			if("true".equals(CsvReaderConstant.delete)){
				FileUtils.deleteQuietly(srcFile.getFile());
			}
			
			
			System.out.println("=================FileReNameStepListener.afterStep()=====================");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		endDate = new Date();
		end = sdf.format(endDate);
		
		getTimestamp();
		
		String path = getPath();
		File fileDir = null;
		File file = null;
		String os = System.getProperties().getProperty("os.name");
		String txtPath = "";
		if("Windows".equals(os.split(" ")[0])){
			fileDir = new File(path+"\\log");
			txtPath=path+"\\log\\log.txt";
			file = new File(txtPath);
		}else if("Linux".equals(os.split(" ")[0])){
			fileDir = new File(path+"/log");
			txtPath=path+"/log/log.txt";
			file = new File(txtPath);
		}
		
		makeDirAndFile(fileDir,file);
		write(txtPath);
		  
		return stepExecution.getExitStatus();
	}
	
	/**
	 * 读取指定文件条数
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private long getTotalLines(String fileName) throws IOException {
        FileReader in = new FileReader(fileName);
        LineNumberReader reader = new LineNumberReader(in);
        String strLine = reader.readLine();
        long totalLines = 0;
        while (strLine != null) {
            totalLines++;
            strLine = reader.readLine();
        }
        reader.close();
        in.close();
        return totalLines;
    }
	

	/**
	 * 获取时间戳
	 */
	public void getTimestamp(){
		long diff = endDate.getTime()-startDate.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		long second = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000*60*60)-minutes*(1000*60))/1000;
		timestamp = hours+"h:"+minutes+"m:"+second+"s";
	}
	
	/**
	 * 写入文件
	 * @param file
	 */
	public void write(String txtPath) {
		
		String content ="file: ["+fileName+"] startTime:["+start+"] endTime:["+end+"] time:["+timestamp+"] records:["+records+"] filtration:["+filtration+"] validRec:["+validRec+"] LANG:["+CsvReaderConstant.lang+"] version:["+CsvReaderConstant.javaVersion+"]\r\n";
		
		File f = new File(txtPath);
		//System.out.println("log文件大小："+f.length());//单位为字节B
		String longsize = CsvReaderConstant.logSize;//单位为兆
		double d = Double.parseDouble(longsize);
		long logSize = new Double(d).longValue();
		//日志文件大于配置文件中规定的量，清空
		if(f.length()>logSize*1024*1024){
			try {
				new FileWriter(f).write("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedWriter out = null ;  
        try  {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtPath,true),"utf-8"));  
            out.write(content);  
        } catch  (Exception e) {  
            e.printStackTrace();  
        } finally{
            try  {
                out.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }  
        }
	}
	
	/**
	 * 创建目录及文件
	 * @param path
	 */
	public void makeDirAndFile(File fileDir,File file){
		
		if(!fileDir.isDirectory()){
			fileDir.mkdirs();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 得到执行jar对应的路径
	 * @return
	 */
	public String getPath(){
		URL url = FileReNameStepListener.class.getProtectionDomain().getCodeSource().getLocation();  
        String filePath = null;  
        try {  
            filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"  
            // 截取路径中的jar包名  
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
        }  
          
        File file = new File(filePath);  
          
        filePath = file.getAbsolutePath();//得到路径 
        return filePath;
	}
	
	/**
	 * 讲过滤的数据写到指定的csv文件中
	 * @param event
	 */
	public void writeSCV(ESMEvent event){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String targetName = fileName.replace("done", "error");
		String path = getPath();
		File fileDir = null;
		File file = null;
		String os = System.getProperties().getProperty("os.name");
		String csvPath = "";
		if("Windows".equals(os.split(" ")[0])){
			fileDir = new File(path+"\\done");
			csvPath=path+"\\done\\"+targetName;
			file = new File(csvPath);
		}else if("Linux".equals(os.split(" ")[0])){
			fileDir = new File(path+"/done");
			csvPath=path+"/done/"+targetName;
			file = new File(csvPath);
		}
		
		makeDirAndFile(fileDir,file);
		
		
		CSVWriter writer;
		try {
		  File f = new File(csvPath);
		  writer = new CSVWriter(new FileWriter(f,true),',');

		  List<String[]> aList=new ArrayList<String[]>();  
		  List<String[]> bList=new ArrayList<String[]>();  
		  List<String> listH = new ArrayList<String>();  
		  //#event.eventId,event.baseEventIds,event.name,event.message,event.type,event.endTime,event.applicationProtocol,event.transportProtocol,event.bytesIn,event.bytesOut,event.customer,event.customerName,event.startTime,event.managerReceiptTime,event.categorySignificance,event.categoryTechnique,event.categoryBehavior,event.categoryDeviceGroup,event.categoryObject,event.categoryOutcome,event.fileName,event.filePath,event.requestCookies,event.requestMethod,event.requestProtocol,event.requestUrl,event.requestUrlFileName,event.requestUrlPort,event.requestUrlHost,event.requestUrlQuery,event.requestContext,event.sourceAddress,event.sourceAssetName,event.sourceHostName,event.sourceMacAddress,event.sourcePort,event.sourceProcessName,event.sourceServiceName,event.sourceTranslatedAddress,event.sourceTranslatedPort,event.sourceUserId,event.sourceUserName,event.sourceZoneName,event.sourceGeoCountryName,event.sourceGeoLocationInfo,event.targetAddress,event.targetAssetName,event.targetHostName,event.targetMacAddress,event.targetPort,event.targetProcessName,event.targetServiceName,event.targetTranslatedAddress,event.targetTranslatedPort,event.targetUserId,event.targetUserName,event.targetZoneName,event.targetGeoCountryName,event.targetGeoLocationInfo,event.assetCriticality,event.modelConfidence,event.priority,event.relevance,event.severity,event.deviceCustomString1,event.deviceCustomString2,event.deviceCustomString3,event.deviceCustomString4,event.deviceCustomString5,event.deviceCustomString6,event.deviceCustomNumber1,event.deviceCustomNumber2,event.deviceCustomNumber3,event.deviceCustomDate1,event.deviceCustomDate2,event.deviceAction,event.deviceAddress,event.deviceAssetId,event.deviceAssetName,event.deviceDirection,event.deviceDomain,event.deviceEventCategory,event.deviceEventClassId,event.deviceFacility,event.deviceHostName,event.deviceInboundInterface,event.deviceOutboundInterface,event.deviceProcessId,event.deviceProcessName,event.deviceProduct,event.deviceReceiptTime,event.deviceSeverity,event.deviceTranslatedAddress,event.deviceVendor,event.deviceZone,event.deviceZoneName,event.agentAddress,event.agentHostName,event.agentName,event.agentReceiptTime,event.agentSeverity
		  listH.add("#event.eventId");    
		  listH.add("event.baseEventIds"); 
		  listH.add("event.name");   
		  listH.add("event.message");    
		  listH.add("event.type");   
		  listH.add("event.endTime");    
		  listH.add("event.applicationProtocol");   
		  listH.add("event.transportProtocol");   
		  listH.add("event.bytesIn");    
		  listH.add("event.bytesOut");   
		  listH.add("event.customer");   
		  listH.add("event.customerName"); 
		  listH.add("event.startTime");  
		  listH.add("event.managerReceiptTime");  
		  listH.add("event.categorySignificance");  
		  listH.add("event.categoryTechnique");   
		  listH.add("event.categoryBehavior");    
		  listH.add("event.categoryDeviceGroup");   
		  listH.add("event.categoryObject");   
		  listH.add("event.categoryOutcome");  
		  listH.add("event.fileName");   
		  listH.add("event.filePath");   
		  listH.add("event.requestCookies");   
		  listH.add("event.requestMethod");    
		  listH.add("event.requestProtocol");  
		  listH.add("event.requestUrl");   
		  listH.add("event.requestUrlFileName");  
		  listH.add("event.requestUrlPort");   
		  listH.add("event.requestUrlHost");   
		  listH.add("event.requestUrlQuery");  
		  listH.add("event.requestContext");   
		  listH.add("event.sourceAddress");    
		  listH.add("event.sourceAssetName");  
		  listH.add("event.sourceHostName");   
		  listH.add("event.sourceMacAddress");    
		  listH.add("event.sourcePort");   
		  listH.add("event.sourceProcessName");   
		  listH.add("event.sourceServiceName");   
		  listH.add("event.sourceTranslatedAddress"); 
		  listH.add("event.sourceTranslatedPort");  
		  listH.add("event.sourceUserId"); 
		  listH.add("event.sourceUserName");   
		  listH.add("event.sourceZoneName");   
		  listH.add("event.sourceGeoCountryName");  
		  listH.add("event.sourceGeoLocationInfo"); 
		  listH.add("event.targetAddress");    
		  listH.add("event.targetAssetName");  
		  listH.add("event.targetHostName");   
		  listH.add("event.targetMacAddress");   
		  listH.add("event.targetPort");   
		  listH.add("event.targetProcessName");  
		  listH.add("event.targetServiceName");  
		  listH.add("event.targetTranslatedAddress");  
		  listH.add("event.targetTranslatedPort");   
		  listH.add("event.targetUserId"); 
		  listH.add("event.targetUserName");   
		  listH.add("event.targetZoneName");   
		  listH.add("event.targetGeoCountryName");   
		  listH.add("event.targetGeoLocationInfo");  
		  listH.add("event.assetCriticality");   
		  listH.add("event.modelConfidence");  
		  listH.add("event.priority");   
		  listH.add("event.relevance");  
		  listH.add("event.severity");   
		  listH.add("event.deviceCustomString1");    
		  listH.add("event.deviceCustomString2");    
		  listH.add("event.deviceCustomString3");    
		  listH.add("event.deviceCustomString4");    
		  listH.add("event.deviceCustomString5");    
		  listH.add("event.deviceCustomString6");    
		  listH.add("event.deviceCustomNumber1");    
		  listH.add("event.deviceCustomNumber2");    
		  listH.add("event.deviceCustomNumber3");    
		  listH.add("event.deviceCustomDate1");  
		  listH.add("event.deviceCustomDate2");  
		  listH.add("event.deviceAction"); 
		  listH.add("event.deviceAddress");    
		  listH.add("event.deviceAssetId");    
		  listH.add("event.deviceAssetName");  
		  listH.add("event.deviceDirection");  
		  listH.add("event.deviceDomain"); 
		  listH.add("event.deviceEventCategory");    
		  listH.add("event.deviceEventClassId"); 
		  listH.add("event.deviceFacility");   
		  listH.add("event.deviceHostName");   
		  listH.add("event.deviceInboundInterface");   
		  listH.add("event.deviceOutboundInterface");  
		  listH.add("event.deviceProcessId");  
		  listH.add("event.deviceProcessName");  
		  listH.add("event.deviceProduct");    
		  listH.add("event.deviceReceiptTime");  
		  listH.add("event.deviceSeverity");   
		  listH.add("event.deviceTranslatedAddress");  
		  listH.add("event.deviceVendor"); 
		  listH.add("event.deviceZone");   
		  listH.add("event.deviceZoneName");   
		  listH.add("event.agentAddress"); 
		  listH.add("event.agentHostName");    
		  listH.add("event.agentName");  
		  listH.add("event.agentReceiptTime");   
		  listH.add("event.agentSeverity");
		  aList.add(listH.toArray(new String[listH.size()]));  
		      
		  List<String> listB=new ArrayList<String>();  
		  listB.add(event.getEventId());  
		  listB.add(event.getBaseEventIds());  
		  listB.add(event.getName());  
		  listB.add(event.getMessage());  
		  listB.add(event.getType());  
//		  listB.add(sdf.format(event.getEndTime()));
		  listB.add(event.getEndTime());
		  listB.add(event.getApplicationProtocol());  
		  listB.add(event.getTransportProtocol());  
		  listB.add(event.getBytesIn());  
		  listB.add(event.getBytesOut());  
		  listB.add(event.getCustomer());  
		  listB.add(event.getCustomerName());  
		  listB.add(sdf.format(event.getStartTime())); 
		  //listB.add(event.getStartTime());
		  listB.add(event.getManagerReceiptTime());  
		  listB.add(event.getCategorySignificance());  
		  listB.add(event.getCategoryTechnique());  
		  listB.add(event.getCategoryBehavior());  
		  listB.add(event.getCategoryDeviceGroup());  
		  listB.add(event.getCategoryObject());  
		  listB.add(event.getCategoryOutcome());  
		  listB.add(event.getFileName());  
		  listB.add(event.getFilePath());  
		  listB.add(event.getRequestCookies());  
		  listB.add(event.getRequestMethod());  
		  listB.add(event.getRequestProtocol());  
		  listB.add(event.getRequestUrl());  
		  listB.add(event.getRequestUrlFileName());  
		  listB.add(event.getRequestUrlPort());  
		  listB.add(event.getRequestUrlHost());  
		  listB.add(event.getRequestUrlQuery());  
		  listB.add(event.getRequestContext());  
		  listB.add(event.getSourceAddress());  
		  listB.add(event.getSourceAssetName());  
		  listB.add(event.getSourceHostName());  
		  listB.add(event.getSourceMacAddress());  
		  listB.add(event.getSourcePort());  
		  listB.add(event.getSourceProcessName());  
		  listB.add(event.getSourceServiceName());  
		  listB.add(event.getSourceTranslatedAddress());  
		  listB.add(event.getSourceTranslatedPort());  
		  listB.add(event.getSourceUserId());  
		  listB.add(event.getSourceUserName());  
		  listB.add(event.getSourceZoneName());  
		  listB.add(event.getSourceGeoCountryName());  
		  listB.add(event.getSourceCeoLocationInfo());  
		  listB.add(event.getTargetAddress());  
		  listB.add(event.getTargetAssetName());  
		  listB.add(event.getTargetHostName()); 
		  listB.add(event.getTargetMacAddress());
		  listB.add(event.getTargetPort());
		  listB.add(event.getTargetProcessName());
		  listB.add(event.getTargetServiceName());
		  listB.add(event.getTargetTranslatedAddress());
		  listB.add(event.getTargetTranslatedPort());
		  listB.add(event.getTargetUserId());
		  listB.add(event.getTargetUserName());
		  listB.add(event.getTargetZoneName());
		  listB.add(event.getTargetGeoCountryName());
		  listB.add(event.getTargetCeoLocationInfo());
		  listB.add(event.getAssetCriticality());
		  listB.add(event.getModelConfidence());
		  listB.add(event.getPriority());
		  listB.add(event.getRelevance());
		  listB.add(event.getSeverity());
		  listB.add(event.getDeviceCustomString1());
		  listB.add(event.getDeviceCustomString2());
		  listB.add(event.getDeviceCustomString3());
		  listB.add(event.getDeviceCustomString4());
		  listB.add(event.getDeviceCustomString5());
		  listB.add(event.getDeviceCustomString6());
		  listB.add(event.getDeviceCustomNumber1());
		  listB.add(event.getDeviceCustomNumber2());
		  listB.add(event.getDeviceCustomNumber3());
		  listB.add(event.getDeviceCustomDate1());
		  listB.add(event.getDeviceCustomDate2());
		  listB.add(event.getDeviceAction());
		  listB.add(event.getDeviceAddress());
		  listB.add(event.getDeviceAssetId());
		  listB.add(event.getDeviceAssetName());
		  listB.add(event.getDeviceDirection());
		  listB.add(event.getDeviceDomain());
		  listB.add(event.getDeviceEventCategory());
		  listB.add(event.getDeviceEventClassId());
		  listB.add(event.getDeviceFacility());
		  listB.add(event.getDeviceHostName());
		  listB.add(event.getDeviceInboundInterface());
		  listB.add(event.getDeviceOutboundInterface());
		  listB.add(event.getDeviceProcessId());
		  listB.add(event.getDeviceProcessName());
		  listB.add(event.getDeviceProduct());
		  listB.add(event.getDeviceReceiptTime());
		  listB.add(event.getDeviceSeverity());
		  listB.add(event.getDeviceTranslatedAddress());
		  listB.add(event.getDeviceVendor());
		  listB.add(event.getDeviceZone());
		  listB.add(event.getDeviceZoneName());
		  listB.add(event.getAgentAddress());
		  listB.add(event.getAgentHostName());
		  listB.add(event.getAgentName());
		  listB.add(event.getAgentReceiptTime());
		  listB.add(event.getAgentSeverity());
		  bList.add(listB.toArray(new String[listB.size()]));  
		  if(f.length()>0){
			  writer.writeAll(bList);
		  }else{
			  writer.writeAll(aList);  
			  writer.writeAll(bList);
		  }
		    
		  writer.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
