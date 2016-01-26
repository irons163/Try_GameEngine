package com.example.try_gameengine.center_notification;

import android.content.ContentValues;

public class NSANotification {
	private String name;
	private Object object;
	private ContentValues userInfo;
	
	
	public String getName() {
		return name;
	}
	public Object getObject() {
		return object;
	}
	public ContentValues getUserInfo() {
		return userInfo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public void setUserInfo(ContentValues userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
