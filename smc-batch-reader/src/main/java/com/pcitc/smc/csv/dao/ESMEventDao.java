package com.pcitc.smc.csv.dao;

import java.util.List;

import com.pcitc.smc.csv.model.ESMEvent;

public interface ESMEventDao {
	
	public void saveESMEvent(ESMEvent esmEvent);
	
	public void saveESMEventBatch(List<? extends ESMEvent> items);

	public void call_dataToType();

}
