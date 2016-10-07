package com.example.try_gameengine.center_notification;

import android.content.ContentValues;

/**
 * {@code NSANotification} is a class like NSNotification in iOS.
 * The instances of NSANotification to store all of notification things..
 * @author irons
 *
 */
public class NSANotification {
	/**
	 * name of {@code NSANotification}.
	 */
	private String name;
	/**
	 * object attached to {@code NSANotification}.
	 */
	private Object object;
	/**
	 * useInfo in {@code NSANotification}.
	 */
	private ContentValues userInfo;
	/**
	 * get name of {@code NSANotification}.
	 * @return String.
	 */
	public String getName() {
		return name;
	}
	/**
	 * get object attached to {@code NSANotification}.
	 * @return Object.
	 */
	public Object getObject() {
		return object;
	}
	/** 
	 * get userInfo in {@code NSANotification}.
	 * @return ContentValuse.
	 */
	public ContentValues getUserInfo() {
		return userInfo;
	}
	/**
	 * set name of {@code NSANotification}.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * set object attached to {@code NSANotification}.
	 * @param object
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * set useInfo in {@code NSANotification}.
	 * @param userInfo
	 */
	public void setUserInfo(ContentValues userInfo) {
		this.userInfo = userInfo;
	}
}
