package com.example.try_gameengine.center_notification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;

public class NSANotificationCenter{
	private static NSANotificationCenter nsaNotificationCenter;
	private final Map<String, HashSet<NSANotifiable>> observers;
	
	private NSANotificationCenter(){
		observers =new HashMap<String, HashSet<NSANotifiable>>();
	};
	
	public static NSANotificationCenter defaultCenter(){
		if(nsaNotificationCenter==null){
			synchronized (NSANotificationCenter.class) {
				if(nsaNotificationCenter==null){
					nsaNotificationCenter = new NSANotificationCenter();
				}
			}
		}
		return nsaNotificationCenter;
	}
	
	public synchronized void addObserver(NSANotifiable observer, String notificationName, Object object){
		if(!observers.containsKey(notificationName)){
			HashSet<NSANotifiable> nsaNotifiables = new HashSet<NSANotifiable>();
			nsaNotifiables.add(observer);
			observers.put(notificationName, nsaNotifiables);
		}else{
			observers.get(notificationName).add(observer);
		}		
	}
	
	public synchronized void postNotification(NSANotification nsaNotification){
		HashSet<NSANotifiable> nsaNotifiables = observers.get(nsaNotification.getName());
		for(NSANotifiable nsaNotifiable : nsaNotifiables){
			nsaNotifiable.receiveNotification(nsaNotification);
		}
	}
	
	public synchronized void postNotification(String notificationName, Object anyObjectForMessage){
		postNotification(notificationName, anyObjectForMessage, null);
	}

	public synchronized void postNotification(String notificationName, Object anyObjectForMessage, ContentValues userInfo){
		NSANotification nsaNotification = new NSANotification();
		nsaNotification.setName(notificationName);
		nsaNotification.setObject(anyObjectForMessage);
		nsaNotification.setUserInfo(userInfo);
		postNotification(nsaNotification);
	}
	
	public synchronized void removeObserver(NSANotifiable observer){
		for(Entry<String, HashSet<NSANotifiable>> nsaNotifiables : observers.entrySet()){
			if(nsaNotifiables.getValue().contains(observer)){
				nsaNotifiables.getValue().remove(observer);
			}
		}
	}
	
	public synchronized void removeObserver(NSANotifiable observer, String notificationName, Object object){
		for(Entry<String, HashSet<NSANotifiable>> nsaNotifiables : observers.entrySet()){
			if(nsaNotifiables.getKey().equals(notificationName) && nsaNotifiables.getValue().contains(observer)){
				nsaNotifiables.getValue().remove(observer);
				break;
			}
		}
	}
}
