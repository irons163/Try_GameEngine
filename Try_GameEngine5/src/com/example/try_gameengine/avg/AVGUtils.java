package com.example.try_gameengine.avg;

import java.util.concurrent.TimeUnit;

public class AVGUtils {

	public static void pause(long timeMillis) {
		try {
			TimeUnit.MILLISECONDS.sleep(timeMillis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted in pause !");
		}
	}
}
