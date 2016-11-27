package com.pcitc.smc.csv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcitc.smc.csv.dao.ESMEventDao;
import com.pcitc.smc.csv.model.ESMEvent;
import com.pcitc.smc.csv.service.IESMEventService;

@Service("esmEventService")
public class ESMEventServiceImpl implements IESMEventService {
	
	@Autowired
	private ESMEventDao esmEventDao;
	
	/* (non-Javadoc)
	 * @see com.pcitc.smc.csv.service.impl.IESMEventService#saveEvent(com.pcitc.smc.csv.model.ESMEvent)
	 */
	@Override
	public void saveEvent(ESMEvent esmEvent){
		try {
			esmEventDao.saveESMEvent(esmEvent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveEventList(List<? extends ESMEvent> items) {
		// TODO Auto-generated method stub
		try {
			for( ESMEvent esmEvent : items){
				esmEventDao.saveESMEvent(esmEvent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void saveEventListBatch(List<? extends ESMEvent> items) {
		// TODO Auto-generated method stub
			try {
				esmEventDao.saveESMEventBatch(items);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}

	/**
	 * 调用存储过程
	 */
	@Override
	public void callDataToType() {
		esmEventDao.call_dataToType();
	}

}
