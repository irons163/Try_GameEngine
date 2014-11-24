package com.example.try_gameengine.avg;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email ceponline@yahoo.com.cn
 * @version 0.1.0
 */
public abstract class Resources {

	private static ClassLoader classLoader;

	private final static Object lock = new Object();

	private final static Map<String, Object> lazyResources = new HashMap<String, Object>(
			LSystem.DEFAULT_MAX_CACHE_SIZE);

	static {
		try {
			// �bAndroid��Thread.currentThread()�覡����Q�o|||�K�K
			// classLoader = Thread.currentThread().getContextClassLoader();
			classLoader = Resources.class.getClassLoader();
		} catch (Throwable ex) {
			classLoader = null;
		}
	}

	/**
	 * ��o�귽�W���йB�⾹
	 * 
	 * @return
	 */
	public static Iterator<String> getNames() {
		synchronized (lock) {
			return lazyResources.keySet().iterator();
		}
	}

	/**
	 * �ˬd���w�귽�W�O�_�s�b
	 * 
	 * @param resName
	 * @return
	 */
	public static boolean contains(String resName) {
		synchronized (lock) {
			return (lazyResources.get(resName) != null);
		}
	}

	/**
	 * �R�����w�W�٪��귽
	 * 
	 * @param resName
	 */
	public static void remove(String resName) {
		synchronized (lock) {
			lazyResources.remove(resName);
		}
	}

	public static void destroy() {
		lazyResources.clear();
	}

	public void finalize() {
		destroy();
	}

	/**
	 * ��o��e�t�Ϊ�ClassLoader
	 * 
	 * @return
	 */
	public final static ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * ��o���w����ClassLoader
	 * 
	 * @param clazz
	 * @return
	 */
	public final static ClassLoader getClassLoader(Class<?> clazz) {
		return clazz.getClassLoader();
	}

	/**
	 * ���}�@�ӫ��w��ClassLoader�귽
	 * 
	 * @param resName
	 * @param cl
	 * @return
	 * @throws IOException
	 */
	public static InputStream openResource(String resName, final ClassLoader c)
			throws IOException {
		final InputStream result = c.getResourceAsStream(resName);
		if (result == null) {
			throw new IOException("Exception to load resource [" + resName
					+ "] .");
		}
		return result;
	}

	/**
	 * ���}��e�����J���U���귽��
	 * 
	 * @param resName
	 * @return
	 * @throws IOException
	 */
	public static InputStream openResource(String resName) throws IOException {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(classLoader
					.getResourceAsStream(resName));
		} catch (Exception e) {
			throw new RuntimeException(resName + " not found!");
		}
		return in;
	}

	/**
	 * ���J�귽��
	 * 
	 * @param resName
	 * @return
	 */
	public final static ArrayByte getResource(String resName) {
		if (resName == null) {
			return null;
		}
		resName = resName.startsWith("/") ? resName.substring(1) : resName;
		String innerName = resName;
		String keyName = innerName.replaceAll(" ", "").toLowerCase();
		synchronized (lock) {
			if (lazyResources.size() > LSystem.DEFAULT_MAX_CACHE_SIZE) {
				lazyResources.clear();
				LSystem.gc();
			}
			byte[] data = (byte[]) lazyResources.get(keyName);
			if (data != null) {
				return new ArrayByte(data);
			}
		}
		InputStream in = null;
		// �~���ɼлx
		boolean filePath = innerName.startsWith("$");
		if (filePath || isExists(resName)) {
			try {
				innerName = innerName.substring(1, innerName.length());
				in = new BufferedInputStream(new FileInputStream(new File(
						innerName)));
			} catch (FileNotFoundException ex) {
			}
		} else {
			in = new BufferedInputStream(classLoader
					.getResourceAsStream(innerName));
		}
		ArrayByte byteArray = new ArrayByte();
		try {
			byteArray.write(in);
			in.close();
			byteArray.reset();
			lazyResources.put(keyName, byteArray.getData());
		} catch (IOException ex) {
			byteArray = null;
		}
		if (byteArray == null) {
			throw new RuntimeException(resName + " file not found !");
		}
		return byteArray;
	}

	/**
	 * ���J�귽��(�L�w�s)
	 * 
	 * @param resName
	 * @return
	 */
	public final static ArrayByte getNotCacheResource(String resName) {
		if (resName == null) {
			return null;
		}
		resName = resName.startsWith("/") ? resName.substring(1) : resName;
		InputStream in = null;
		// �~���ɼлx
		boolean filePath = resName.startsWith("$");
		if (filePath || isExists(resName)) {
			try {
				resName = resName.substring(1, resName.length());
				in = new BufferedInputStream(new FileInputStream(new File(
						resName)));
			} catch (FileNotFoundException ex) {
			}
		} else {
			in = new BufferedInputStream(classLoader
					.getResourceAsStream(resName));
		}
		ArrayByte byteArray = new ArrayByte();
		try {
			byteArray.write(in);
			in.close();
			byteArray.reset();
		} catch (IOException ex) {
			byteArray = null;
		}
		if (byteArray == null) {
			throw new RuntimeException(resName + " file not found !");
		}
		return byteArray;
	}

	/**
	 * ���J�귽�ɬ�InputStream�榡
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getResourceAsStream(final String fileName) {
		if ((fileName.indexOf("file:") >= 0) || (fileName.indexOf(":/") > 0)) {
			try {
				URL url = new URL(fileName);
				return new BufferedInputStream(url.openStream());
			} catch (Exception e) {
				return null;
			}
		}
		return new ByteArrayInputStream(getResource(fileName).getData());
	}

	/**
	 * ���J�귽�ɬ�InputStream�榡(�L�w�s)
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getNotCacheResourceAsStream(final String fileName) {
		if ((fileName.indexOf("file:") >= 0) || (fileName.indexOf(":/") > 0)) {
			try {
				URL url = new URL(fileName);
				return new BufferedInputStream(url.openStream());
			} catch (Exception e) {
				return null;
			}
		}
		return new ByteArrayInputStream(getNotCacheResource(fileName).getData());
	}

	/**
	 * �NInputStream�ରbyte[]
	 * 
	 * @param is
	 * @return
	 */
	final static public byte[] getDataSource(InputStream is) {
		if (is == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[8192];
		try {
			int read;
			while ((read = is.read(bytes)) >= 0) {
				byteArrayOutputStream.write(bytes, 0, read);
			}
			bytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.flush();
					byteArrayOutputStream = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
			}
		}
		return bytes;
	}

	public final static InputStream getResource(Class<?> clazz, String resName) {
		return clazz.getResourceAsStream(resName);
	}

	private static boolean isExists(String fileName) {
		return new File(fileName).exists();
	}

	/**
	 * �q�LurlŪ�������ɬy
	 * 
	 * @param uri
	 * @return
	 */
	final static public byte[] getHttpStream(final String uri) {
		URL url;
		try {
			url = new URL(uri);
		} catch (Exception e) {
			return null;
		}
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (Exception e) {
			return null;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] arrayByte = null;
		try {
			arrayByte = new byte[4096];
			int read;
			while ((read = is.read(arrayByte)) >= 0) {
				os.write(arrayByte, 0, read);
			}
			arrayByte = os.toByteArray();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
			}
		}

		return arrayByte;
	}

}

