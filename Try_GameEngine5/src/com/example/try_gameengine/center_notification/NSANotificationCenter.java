package com.example.try_gameengine.center_notification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;

/**
 * {@code NSANotificationCenter} is a class like NSNotificationCenter in iOS.
 * It is a center to control all of the {@link NSANotification}.
 * @author irons
 *
 */
public class NSANotificationCenter{
	private static NSANotificationCenter nsaNotificationCenter;
	private final Map<String, HashSet<NSANotifiable>> observers;
	
	private NSANotificationCenter(){
		observers =new HashMap<String, HashSet<NSANotifiable>>();
	};
	
	/**
	 * create singleton {@code NSANotificationCenter} instance.
	 * @return
	 */
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
	
	/**
	 * add observer to {@code NSANotificationCenter}.
	 * @param observer
	 * 			observer for cache notification.
	 * @param notificationName
	 * 			name of notification.
	 * @param object
	 * 			object attached to notification and observer can handle this..
	 */
	public synchronized void addObserver(NSANotifiable observer, String notificationName, Object object){
		if(!observers.containsKey(notificationName)){
			HashSet<NSANotifiable> nsaNotifiables = new HashSet<NSANotifiable>();
			nsaNotifiables.add(observer);
			observers.put(notificationName, nsaNotifiables);
		}else{
			observers.get(notificationName).add(observer);
		}		
	}
	
	/**
	 * post notification, and observers can receive notification.
	 * @param nsaNotification for post.
	 */
	public synchronized void postNotification(NSANotification nsaNotification){
		HashSet<NSANotifiable> nsaNotifiables = observers.get(nsaNotification.getName());
		for(NSANotifiable nsaNotifiable : nsaNotifiables){
			nsaNotifiable.receiveNotification(nsaNotification);
		}
	}
	
	/**
	 * post notification.
	 * @param notificationName
	 * @param anyObjectForMessage
	 */
	public synchronized void postNotification(String notificationName, Object anyObjectForMessage){
		postNotification(notificationName, anyObjectForMessage, null);
	}

	/**
	 * 
	 * @param notificationName
	 * @param anyObjectForMessage
	 * @param userInfo
	 */
	public synchronized void postNotification(String notificationName, Object anyObjectForMessage, ContentValues userInfo){
		NSANotification nsaNotification = new NSANotification();
		nsaNotification.setName(notificationName);
		nsaNotification.setObject(anyObjectForMessage);
		nsaNotification.setUserInfo(userInfo);
		postNotification(nsaNotification);
	}
	
	/**
	 * remove observer from listener.
	 * @param observer to remove.
	 */
	public synchronized void removeObserver(NSANotifiable observer){
		for(Entry<String, HashSet<NSANotifiable>> nsaNotifiables : observers.entrySet()){
			if(nsaNotifiables.getValue().contains(observer)){
				nsaNotifiables.getValue().remove(observer);
			}
		}
	}
	
	/**
	 * remove observer by this.
	 * @param observer
	 * 			
	 * @param notificationName
	 * @param object
	 */
	public synchronized void removeObserver(NSANotifiable observer, String notificationName, Object object){
		for(Entry<String, HashSet<NSANotifiable>> nsaNotifiables : observers.entrySet()){
			if(nsaNotifiables.getKey().equals(notificationName) && nsaNotifiables.getValue().contains(observer)){
				nsaNotifiables.getValue().remove(observer);
				break;
			}
		}
	}
}
