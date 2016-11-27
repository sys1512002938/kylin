package com.pcitc.smc.csv.reader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.pcitc.smc.csv.CsvReaderConstant;
import com.pcitc.smc.csv.model.ESMEvent;
import com.pcitc.smc.csv.util.IPUtils;

public class ESMEventFieldSetMapper implements FieldSetMapper<ESMEvent>{

	
	
	@Override
	public ESMEvent mapFieldSet(FieldSet fieldSet) throws BindException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO Auto-generated method stub
		ESMEvent esmEvent = new ESMEvent();
		esmEvent.setEventId(fieldSet.readRawString("#event.eventId"));    
		esmEvent.setBaseEventIds(""); 
		esmEvent.setName(fieldSet.readRawString("event.name"));
		esmEvent.setMessage(fieldSet.readRawString("event.message"));    
		esmEvent.setType(fieldSet.readRawString("event.type"));
		/*String endTime = IPUtils.splitDate(fieldSet.readRawString("event.endTime"));
		
		try {
			esmEvent.setEndTime(sdf.parse(endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		esmEvent.setEndTime(fieldSet.readRawString("event.endTime"));
		esmEvent.setApplicationProtocol(fieldSet.readRawString("event.applicationProtocol"));   
		esmEvent.setTransportProtocol(fieldSet.readRawString("event.transportProtocol"));   
		esmEvent.setBytesIn(fieldSet.readRawString("event.bytesIn"));    
		esmEvent.setBytesOut(fieldSet.readRawString("event.bytesOut"));   
		esmEvent.setCustomer(fieldSet.readRawString("event.customer"));   
		esmEvent.setCustomerName(fieldSet.readRawString("event.customerName"));
		String startTime = IPUtils.splitDate(fieldSet.readRawString("event.startTime"));
		try {
			esmEvent.setStartTime(sdf.parse(startTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//esmEvent.setStartTime(fieldSet.readRawString("event.startTime"));
		esmEvent.setManagerReceiptTime(fieldSet.readRawString("event.managerReceiptTime"));  
		esmEvent.setCategorySignificance(fieldSet.readRawString("event.categorySignificance"));  
		esmEvent.setCategoryTechnique(fieldSet.readRawString("event.categoryTechnique"));   
		esmEvent.setCategoryBehavior(fieldSet.readRawString("event.categoryBehavior"));    
		esmEvent.setCategoryDeviceGroup(fieldSet.readRawString("event.categoryDeviceGroup"));   
		esmEvent.setCategoryObject(fieldSet.readRawString("event.categoryObject"));   
		esmEvent.setCategoryOutcome(fieldSet.readRawString("event.categoryOutcome"));  
		esmEvent.setFileName(fieldSet.readRawString("event.fileName"));   
		esmEvent.setFilePath(fieldSet.readRawString("event.filePath"));   
		esmEvent.setRequestCookies(fieldSet.readRawString("event.requestCookies"));   
		esmEvent.setRequestMethod(fieldSet.readRawString("event.requestMethod"));    
		esmEvent.setRequestProtocol(fieldSet.readRawString("event.requestProtocol"));  
		esmEvent.setRequestUrl(fieldSet.readRawString("event.requestUrl"));   
		esmEvent.setRequestUrlFileName(fieldSet.readRawString("event.requestUrlFileName"));  
		esmEvent.setRequestUrlPort(fieldSet.readRawString("event.requestUrlPort"));   
		esmEvent.setRequestUrlHost(fieldSet.readRawString("event.requestUrlHost"));   
		esmEvent.setRequestUrlQuery(fieldSet.readRawString("event.requestUrlQuery"));  
		esmEvent.setRequestContext(fieldSet.readRawString("event.requestContext"));   
		esmEvent.setSourceAddress(fieldSet.readRawString("event.sourceAddress"));    
		esmEvent.setSourceAssetName(fieldSet.readRawString("event.sourceAssetName"));  
		esmEvent.setSourceHostName(fieldSet.readRawString("event.sourceHostName"));   
		esmEvent.setSourceMacAddress(fieldSet.readRawString("event.sourceMacAddress"));    
		esmEvent.setSourcePort(fieldSet.readRawString("event.sourcePort"));   
		esmEvent.setSourceProcessName(fieldSet.readRawString("event.sourceProcessName"));   
		esmEvent.setSourceServiceName(fieldSet.readRawString("event.sourceServiceName"));   
		esmEvent.setSourceTranslatedAddress(fieldSet.readRawString("event.sourceTranslatedAddress")); 
		esmEvent.setSourceTranslatedPort(fieldSet.readRawString("event.sourceTranslatedPort"));  
		esmEvent.setSourceUserId(fieldSet.readRawString("event.sourceUserId")); 
		esmEvent.setSourceUserName(fieldSet.readRawString("event.sourceUserName"));   
		esmEvent.setSourceZoneName(fieldSet.readRawString("event.sourceZoneName"));   
		esmEvent.setSourceGeoCountryName(fieldSet.readRawString("event.sourceGeoCountryName"));  
		esmEvent.setSourceCeoLocationInfo(fieldSet.readRawString("event.sourceGeoLocationInfo")); 
		esmEvent.setTargetAddress(fieldSet.readRawString("event.targetAddress"));    
		esmEvent.setTargetAssetName(fieldSet.readRawString("event.targetAssetName"));  
		esmEvent.setTargetHostName(fieldSet.readRawString("event.targetHostName"));   
		esmEvent.setTargetMacAddress(fieldSet.readRawString("event.targetMacAddress"));   
		esmEvent.setTargetPort(fieldSet.readRawString("event.targetPort"));   
		esmEvent.setTargetProcessName(fieldSet.readRawString("event.targetProcessName"));  
		esmEvent.setTargetServiceName(fieldSet.readRawString("event.targetServiceName"));  
		esmEvent.setTargetTranslatedAddress(fieldSet.readRawString("event.targetTranslatedAddress"));  
		esmEvent.setTargetTranslatedPort(fieldSet.readRawString("event.targetTranslatedPort"));   
		esmEvent.setTargetUserId(fieldSet.readRawString("event.targetUserId")); 
		esmEvent.setTargetUserName(fieldSet.readRawString("event.targetUserName"));   
		esmEvent.setTargetZoneName(fieldSet.readRawString("event.targetZoneName"));   
		esmEvent.setTargetGeoCountryName(fieldSet.readRawString("event.targetGeoCountryName"));   
		esmEvent.setTargetCeoLocationInfo(fieldSet.readRawString("event.targetGeoLocationInfo"));  
		esmEvent.setAssetCriticality(fieldSet.readRawString("event.assetCriticality"));   
		esmEvent.setModelConfidence(fieldSet.readRawString("event.modelConfidence"));  
		esmEvent.setPriority(fieldSet.readRawString("event.priority"));   
		esmEvent.setRelevance(fieldSet.readRawString("event.relevance"));  
		esmEvent.setSeverity(fieldSet.readRawString("event.severity"));   
		esmEvent.setDeviceCustomString1(fieldSet.readRawString("event.deviceCustomString1"));    
		esmEvent.setDeviceCustomString2(fieldSet.readRawString("event.deviceCustomString2"));    
		esmEvent.setDeviceCustomString3(fieldSet.readRawString("event.deviceCustomString3"));    
		esmEvent.setDeviceCustomString4(fieldSet.readRawString("event.deviceCustomString4"));    
		esmEvent.setDeviceCustomString5(fieldSet.readRawString("event.deviceCustomString5"));    
		esmEvent.setDeviceCustomString6(fieldSet.readRawString("event.deviceCustomString6"));    
		esmEvent.setDeviceCustomNumber1(fieldSet.readRawString("event.deviceCustomNumber1"));    
		esmEvent.setDeviceCustomNumber2(fieldSet.readRawString("event.deviceCustomNumber2"));    
		esmEvent.setDeviceCustomNumber3(fieldSet.readRawString("event.deviceCustomNumber3"));    
		esmEvent.setDeviceCustomDate1(fieldSet.readRawString("event.deviceCustomDate1"));  
		esmEvent.setDeviceCustomDate2(fieldSet.readRawString("event.deviceCustomDate2"));  
		esmEvent.setDeviceAction(fieldSet.readRawString("event.deviceAction")); 
		esmEvent.setDeviceAddress(fieldSet.readRawString("event.deviceAddress"));    
		esmEvent.setDeviceAssetId(fieldSet.readRawString("event.deviceAssetId"));    
		esmEvent.setDeviceAssetName(fieldSet.readRawString("event.deviceAssetName"));  
		esmEvent.setDeviceDirection(fieldSet.readRawString("event.deviceDirection"));  
		esmEvent.setDeviceDomain(fieldSet.readRawString("event.deviceDomain")); 
		esmEvent.setDeviceEventCategory(fieldSet.readRawString("event.deviceEventCategory"));    
		esmEvent.setDeviceEventClassId(fieldSet.readRawString("event.deviceEventClassId")); 
		esmEvent.setDeviceFacility(fieldSet.readRawString("event.deviceFacility"));   
		esmEvent.setDeviceHostName(fieldSet.readRawString("event.deviceHostName"));   
		esmEvent.setDeviceInboundInterface(fieldSet.readRawString("event.deviceInboundInterface"));   
		esmEvent.setDeviceOutboundInterface(fieldSet.readRawString("event.deviceOutboundInterface"));  
		esmEvent.setDeviceProcessId(fieldSet.readRawString("event.deviceProcessId"));  
		esmEvent.setDeviceProcessName(fieldSet.readRawString("event.deviceProcessName"));  
		esmEvent.setDeviceProduct(fieldSet.readRawString("event.deviceProduct"));    
		esmEvent.setDeviceReceiptTime(fieldSet.readRawString("event.deviceReceiptTime"));  
		esmEvent.setDeviceSeverity(fieldSet.readRawString("event.deviceSeverity"));   
		esmEvent.setDeviceTranslatedAddress(fieldSet.readRawString("event.deviceTranslatedAddress"));  
		esmEvent.setDeviceVendor(fieldSet.readRawString("event.deviceVendor")); 
		esmEvent.setDeviceZone(fieldSet.readRawString("event.deviceZone"));   
		esmEvent.setDeviceZoneName(fieldSet.readRawString("event.deviceZoneName"));   
		esmEvent.setAgentAddress(fieldSet.readRawString("event.agentAddress")); 
		esmEvent.setAgentHostName(fieldSet.readRawString("event.agentHostName"));    
		esmEvent.setAgentName(fieldSet.readRawString("event.agentName"));  
		esmEvent.setAgentReceiptTime(fieldSet.readRawString("event.agentReceiptTime"));   
		esmEvent.setAgentSeverity(fieldSet.readRawString("event.agentSeverity"));               

		return esmEvent;
	}

}
