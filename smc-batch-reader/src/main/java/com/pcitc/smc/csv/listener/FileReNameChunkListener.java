package com.pcitc.smc.csv.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class FileReNameChunkListener implements ChunkListener{

	@Override
	public void beforeChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		System.out.println("StepContext.Attribute:"+context.getStepContext().getAttribute("inputFile"));
		System.out.println("ChunkContext.Attribute:"+context.getAttribute("inputFile"));
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

}
