package com.example.try_gameengine.assemble

import android.content.Context

class AssembleViewConfig private constructor(builder: Builder) {
    enum class DirectionConfig {
        NONE, TOP, BOTTOM, LEFT, RIGHT,
    }

    enum class CenterConfig {
        NONE, CENTER, CENTER_HO, CENTER_VIRTICAL
    }

    val x: Float
    val y: Float
    val w: Float
    val h: Float
    val directionConfig: DirectionConfig?
    val centerConfig: CenterConfig?
    val persentX: Float
    val persentY: Float

    init {
        x = builder.x
        y = builder.x
        w = builder.x
        h = builder.x
        directionConfig = builder.directionConfig
        centerConfig = builder.centerConfig
        persentX = builder.persentX
        persentY = builder.persentY
    }

    class Builder {
        val context: Context? = null
        var x = 0f
        var y = 0f
        var w = 0f
        var h = 0f
        var directionConfig: DirectionConfig? = DirectionConfig.NONE
        var centerConfig: CenterConfig? = CenterConfig.NONE
        var persentX = 0f
        var persentY = 0f

        fun setXY(x: Float, y: Float): Builder {
            this.x = x
            this.y = y
            return this
        }

        fun setWH(w: Float, h: Float): Builder {
            this.w = w
            this.h = h
            return this
        }

        fun setDirectionConfig(directionConfig: DirectionConfig?): Builder {
            this.directionConfig = directionConfig
            return this
        }

        fun setCenterConfig(centerConfig: CenterConfig?): Builder {
            this.centerConfig = centerConfig
            return this
        }

        fun setPersentX(persentX: Float): Builder {
            this.persentX = persentX
            return this
        }

        fun setPersentY(persentY: Float): Builder {
            this.persentY = persentY
            return this
        }

        fun build(): AssembleViewConfig {
            initEmptyFieldsWithDefaultValues()
            return AssembleViewConfig(this)
        }

        private fun initEmptyFieldsWithDefaultValues() {
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

    companion object {
        fun createDefault(context: Context?): AssembleViewConfig {
            return Builder().build()
        }
    }
}
