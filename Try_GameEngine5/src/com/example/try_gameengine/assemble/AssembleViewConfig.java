package com.example.try_gameengine.assemble;

import android.content.Context;
import android.graphics.Bitmap.Config;

public class AssembleViewConfig {
	public enum DirectionConfig{
		NONE, TOP, BOTTOM, LEFT, RIGHT, 
	}
	
	public enum CenterConfig{
		NONE, CENTER, CENTER_HO, CENTER_VIRTICAL
	}
	
	final float x;
	final float y;
	final float w;
	final float h;
	final DirectionConfig directionConfig;
	final CenterConfig centerConfig;
	final float persentX;
	final float persentY;
	
	private AssembleViewConfig(final Builder builder) {
		x = builder.x;
		y = builder.x;
		w = builder.x;
		h = builder.x;
		directionConfig = builder.directionConfig;
		centerConfig = builder.centerConfig;
		persentX = builder.persentX;
		persentY = builder.persentY;
	}
	
	public static AssembleViewConfig createDefault(Context context) {
		return new Builder().build();
	}
	
	public static class Builder {
		private Context context;
		private float x;
		private float y;
		private float w;
		private float h;
		private DirectionConfig directionConfig = DirectionConfig.NONE;
		private CenterConfig centerConfig = CenterConfig.NONE;
		private float persentX;
		private float persentY;
		
		public Builder() {
//			this.context = context.getApplicationContext();
		}
		
		public Builder setXY(float x, float y){
			this.x = x;
			this.y = y;
			return this;
		}
		
		public Builder setWH(float w, float h){
			this.w = w;
			this.h = h;
			return this;
		}
		
		public Builder setDirectionConfig(DirectionConfig directionConfig){
			this.directionConfig = directionConfig;
			return this;
		}
		
		public Builder setCenterConfig(CenterConfig centerConfig){
			this.centerConfig = centerConfig;
			return this;
		}
		
		public Builder setPersentX(float persentX){
			this.persentX = persentX;
			return this;
		}
		
		public Builder setPersentY(float persentY){
			this.persentY = persentY;
			return this;
		}
		
		public AssembleViewConfig build() {
			initEmptyFieldsWithDefaultValues();
			return new AssembleViewConfig(this);
		}
		
		private void initEmptyFieldsWithDefaultValues() {
//			if (taskExecutor == null) {
//				taskExecutor = DefaultConfigurationFactory
//						.createExecutor(threadPoolSize, threadPriority, tasksProcessingType);
//			} else {
//				customExecutor = true;
//			}
//			if (taskExecutorForCachedImages == null) {
//				taskExecutorForCachedImages = DefaultConfigurationFactory
//						.createExecutor(threadPoolSize, threadPriority, tasksProcessingType);
//			} else {
//				customExecutorForCachedImages = true;
//			}
//			if (diskCache == null) {
//				if (diskCacheFileNameGenerator == null) {
//					diskCacheFileNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
//				}
//				diskCache = DefaultConfigurationFactory
//						.createDiskCache(context, diskCacheFileNameGenerator, diskCacheSize, diskCacheFileCount);
//			}
//			if (memoryCache == null) {
//				memoryCache = DefaultConfigurationFactory.createMemoryCache(memoryCacheSize);
//			}
//			if (denyCacheImageMultipleSizesInMemory) {
//				memoryCache = new FuzzyKeyMemoryCache(memoryCache, MemoryCacheUtils.createFuzzyKeyComparator());
//			}
//			if (downloader == null) {
//				downloader = DefaultConfigurationFactory.createImageDownloader(context);
//			}
//			if (decoder == null) {
//				decoder = DefaultConfigurationFactory.createImageDecoder(writeLogs);
//			}
//			if (defaultDisplayImageOptions == null) {
//				defaultDisplayImageOptions = DisplayImageOptions.createSimple();
//			}
		}

	}
}
