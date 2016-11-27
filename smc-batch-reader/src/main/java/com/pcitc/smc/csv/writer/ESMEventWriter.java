package com.pcitc.smc.csv.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.pcitc.smc.csv.model.ESMEvent;
import com.pcitc.smc.csv.service.IESMEventService;

public class ESMEventWriter implements ItemWriter<ESMEvent>{
	
	@Autowired
	private IESMEventService esmEventService;

	@Override
	public void write(List<? extends ESMEvent> items) throws Exception {
		// TODO Auto-generated method stub
		/*for( ESMEvent esmEvent : items){
			esmEventService.saveEvent(esmEvent);
		}*/
		esmEventService.saveEventList(items);
	}

}
