package com.pcitc.smc.csv.service;

import java.util.List;

import com.pcitc.smc.csv.model.ESMEvent;

public interface IESMEventService {

	void saveEvent(ESMEvent esmEvent);
	
	void saveEventList(List<? extends ESMEvent> items);
	
	void saveEventListBatch(List<? extends ESMEvent> items);
	
	void callDataToType();

}