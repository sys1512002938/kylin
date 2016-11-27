package com.pcitc.smc.csv.processor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import com.pcitc.smc.csv.CsvReaderConstant;
import com.pcitc.smc.csv.model.ESMEvent;
import com.pcitc.smc.csv.util.IPUtils;

public class HanderEventCsv implements ItemProcessor<ESMEvent, ESMEvent> {

	// static List<ESMEvent> eventList = new ArrayList<ESMEvent>();
	@Override
	public ESMEvent process(ESMEvent item) throws Exception {
		// TODO Auto-generated method stub
		/** 处理感染恶意软件事件 */
		String sourceAddress = item.getSourceAddress();
		String targetAddress = item.getTargetAddress();
		/** 判断源ip和目标ip是否为空 */
		if (StringUtils.isBlank(targetAddress)) {
			CsvReaderConstant.count1++;
			CsvReaderConstant.eventList.add(item);
			return null;
		}

		String targetTranslatedAddress = item.getTargetTranslatedAddress();
		String direction = item.getDeviceDirection();
		/** 1. 目标ip不是内网ip and inbound and 转换ip为空 */
		/** 判断目标是否为石化内网ip */
		if (!IPUtils.judgeIPWhetherIntranet(targetAddress)) {
			/** 如果不是石化内网，查看是否有转换ip */
			if (StringUtils.isBlank(targetTranslatedAddress) && "Inbound".equalsIgnoreCase(direction)) {
				/** 既不是，石化内网ip,又没有转换ip,说明是石化以外的ip */
				CsvReaderConstant.count2++;
				CsvReaderConstant.eventList.add(item);
				return null;
			}
		}
		if ("Outbound".equalsIgnoreCase(direction)) {
			/** 感染恶意软件 */
			item.setSourceAddress(targetAddress);
			item.setTargetAddress(sourceAddress);
			/** 转换ip */
			String sourceTranslatedAddress = item.getSourceTranslatedAddress();
			item.setTargetTranslatedAddress(sourceTranslatedAddress);
			item.setSourceTranslatedAddress(targetTranslatedAddress);
			/** 转换位置信息 */
			String sourcegeocountryname = item.getSourceGeoCountryName();
			String targetgeocountryname = item.getTargetGeoCountryName();
			String sourcegeolocationinfo = item.getSourceCeoLocationInfo();
			String targetgeolocationinfo = item.getTargetCeoLocationInfo();
			item.setSourceGeoCountryName(targetgeocountryname);
			item.setSourceCeoLocationInfo(targetgeolocationinfo);
			item.setTargetGeoCountryName(sourcegeocountryname);
			item.setTargetCeoLocationInfo(sourcegeolocationinfo);
		}

		/** 处理好的数据，如果目标ip */
		if (!IPUtils.judgeIPWhetherIntranet(item.getTargetAddress())) {
			if (StringUtils.isNoneBlank(targetTranslatedAddress)) {
				item.setTargetAddress(targetTranslatedAddress);
			}
		}

		/** 格式化所有时间 */
		String endTime = item.getEndTime();
		item.setEndTime(IPUtils.splitDate(endTime));

		/*String startTime = item.getStartTime();
		item.setStartTime(IPUtils.splitDate(startTime));*/

		String managerReceiptTime = item.getManagerReceiptTime();
		item.setManagerReceiptTime(IPUtils.splitDate(managerReceiptTime));

		String deviceReceiptTime = item.getDeviceReceiptTime();
		item.setDeviceReceiptTime(IPUtils.splitDate(deviceReceiptTime));

		String agentReceiptTime = item.getAgentReceiptTime();
		item.setAgentReceiptTime(IPUtils.splitDate(agentReceiptTime));
		return item;
	}

}
