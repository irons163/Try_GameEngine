package com.example.try_gameengine.avg;

import java.math.BigDecimal;
import java.util.Random;

/**
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
 * @email�Gceponline@yahoo.com.cn
 * @version 0.1
 */
final public class NumberUtils {

	private static final int DEF_DIV_SCALE = 10;
	
	//PS:�]������e�q���D�A���PC������²��
	private NumberUtils(){
		
	}
	

	/**
	 * ��o�@���H����unsigned int
	 * 
	 * @param maxInt
	 * @param doNotInclude1
	 * @param doNotInclude2
	 * @return
	 */
	public static int getRandomUnsignedInt(int maxInt, int doNotInclude1,
			int doNotInclude2) {
		int n = 2;
		if (doNotInclude1 == doNotInclude2) {
			doNotInclude2 = maxInt + 1;
		}
		if (doNotInclude1 > doNotInclude2) {
			n = doNotInclude2;
			doNotInclude2 = doNotInclude1;
			doNotInclude1 = n;
			n = 2;
		}
		if (doNotInclude1 < 0) {
			doNotInclude1 = maxInt + 1;
		}
		if (doNotInclude2 < 0) {
			doNotInclude2 = maxInt + 1;
		}
		if (doNotInclude1 > maxInt) {
			n--;
		}
		if (doNotInclude2 > maxInt) {
			n--;
		}
		int val = (int) Math.floor(Math.random()
				* ((double) maxInt - (double) n));
		if (val >= doNotInclude1) {
			val++;
		}
		if (val >= doNotInclude2) {
			val++;
		}
		return val;
	}

	/**
	 * ��o�@���H����unsigned int
	 * 
	 * @param maxInt
	 * @param doNotInclude
	 * @return
	 */
	public static int getRandomUnsignedInt(int maxInt, int doNotInclude) {
		int val = 0;
		if (doNotInclude > -1 && doNotInclude <= maxInt) {
			val = (int) Math.floor(Math.random() * ((double) maxInt - 1.0D));
			if (val >= doNotInclude) {
				val++;
			}
		} else {
			val = (int) Math.floor(Math.random() * (double) maxInt);
		}
		return val;
	}

	/**
	 * ��o�@���H����unsigned int
	 * 
	 * @param maxInt
	 * @return
	 */
	public static int getRandomUnsignedInt(int maxInt) {
		return getRandomUnsignedInt(maxInt, -1);
	}

	/**
	 * ��^�@�նü�
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static int getRandomInt(int num1, int num2) {
		int result = 0;
		if (num2 > -1 && num2 <= num1) {
			result = (int) Math.floor(Math.random() * ((double) num1 - 1.0D));
			if (result >= num2) {
				result++;
			}
		} else {
			result = (int) Math.floor(Math.random() * (double) num1);
		}
		return result;
	}

	/**
	 * ������
	 * 
	 * @param i
	 * @param min
	 * @param max
	 * @return
	 */
	public static int mid(int i, int min, int max) {
		return Math.max(i, Math.min(min, max));
	}

	final static private String[] zeros = { "", "0", "00", "000", "0000",
			"00000", "000000", "0000000", "00000000", "000000000", "0000000000" };

	/**
	 * �����w�ƭȸɨ����
	 * 
	 * @param number
	 * @param numDigits
	 * @return
	 */
	public static String addZeros(long number, int numDigits) {
		return addZeros(String.valueOf(number), numDigits);
	}

	/**
	 * �����w�ƭȸɨ����
	 * 
	 * @param number
	 * @param numDigits
	 * @return
	 */
	public static String addZeros(String number, int numDigits) {
		int length = numDigits - number.length();
		if (length != 0) {
			number = zeros[length] + number;
		}
		return number;
	}

	/**
	 * �P�_�O�_���Ʀr
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNan(String param) {
		boolean result = false;
		if (param == null || "".equals(param)) {
			return result;
		}
		param = param.replace('d', '_').replace('f', '_');
		try {
			Double test = new Double(param);
			test.intValue();
			result = true;
		} catch (NumberFormatException ex) {
			return result;
		}
		return result;
	}

	/**
	 * �ˬd�@�ӼƦr�O�_����
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(int val) {
		return (val == Integer.MIN_VALUE) ? true : 0 == val;
	}

	/**
	 * �ˬd�@�Ӧr��Ʀ�O�_����
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(String val) {
		return (val == null | "".equals(val) | val.equals(Integer
				.toString(Integer.MAX_VALUE)));
	}

	/**
	 * ��­p���ӼƭȪ��ʤ���
	 * 
	 * @param divisor
	 * @param dividend
	 * @return
	 */
	public static double toPercent(long divisor, long dividend) {
		if (divisor == 0 || dividend == 0) {
			return 0d;
		}
		double cd = divisor * 1d;
		double pd = dividend * 1d;

		return (Math.round(cd / pd * 10000) * 1d) / 100;
	}

	/**
	 * ��o�@�ի��w���ת��ü�
	 * 
	 * @param size
	 * @return
	 */
	public static int toRandom(int size) {
		Random rad = new Random();
		rad.setSeed(System.currentTimeMillis());
		return Math.abs(rad.nextInt()) % size;
	}


	/**
	 * ��o100%�i��Ѿl�ƭȦʤ���C
	 * 
	 * @param maxValue
	 * @param minusValue
	 * @return
	 */
	public static float minusPercent(float maxValue, float minusValue) {
		return 100 - ((minusValue / maxValue) * 100);
	}

	/**
	 * ��o100%�i��ƭȦʤ���C
	 * 
	 * @param maxValue
	 * @param minusValue
	 * @return
	 */
	public static float percent(float maxValue, float minValue) {
		return (minValue / maxValue) * 100;
	}

	/**
	 * �Nvalue��Ʀ�����Ʀ쪺�j�p�g
	 * 
	 * @param value
	 * @param type
	 *            1:�j�g���� 2�G�p�g����
	 * 
	 * @return
	 */
	public static String toConvertCnNumber(long value, int type) {
		String[] chNumber = { "�s", "��", "�L", "�T", "�v", "��", "��", "�m", "��", "�h" };
		String[] digit = { "", "�B", "��", "�a", "�U", "�Q", "��", "�a" };
		switch (type) {
		case 1:
			String[] capsCNumber = { "�s", "��", "�L", "�T", "�v", "��", "��", "�m",
					"��", "�h" };
			chNumber = capsCNumber;
		case 2:
			String[] minCNumber = { "�s", "�@", "�G", "�T", "�|", "��", "��", "�C",
					"�K", "�E" };
			chNumber = minCNumber;
		}
		String retStr = "";

		String inputStr = Long.toString(value);
		for (int i = inputStr.length(); i > 0; i--) {
			char ch = inputStr.charAt(i - 1);
			if (ch != '0') {

				retStr = chNumber[ch - '0'] + digit[inputStr.length() - i]
						+ retStr;
			} else {
				if (inputStr.length() - i == 4)
					retStr = "�s�U" + retStr;
				else
					retStr = "�s" + retStr;
			}
		}

		int pos = retStr.indexOf("�s�s");
		while (pos >= 0) {
			retStr = retStr.replaceAll("�s�s", "�s");
			pos = retStr.indexOf("�s�s");
		}

		retStr = retStr.replaceAll("�s�U", "�U");

		return retStr;
	}

	/**
	 * ���Ѻ�T���[�k�B��C
	 * 
	 * @param v1
	 *            �Q�[��
	 * @param v2
	 *            �[��
	 * @return ��ӰѼƪ��M
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * ���Ѻ�T����k�B��C
	 * 
	 * @param v1
	 *            �Q���
	 * @param v2
	 *            ���
	 * @return ��ӰѼƪ��t
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * ���Ѻ�T�����k�B��C
	 * 
	 * @param v1
	 *            �Q����
	 * @param v2
	 *            ����
	 * @return ��ӰѼƪ��n
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * ���ѡ]�۹�^��T�����k�B��A��o�Ͱ����ɪ����p�ɡA��T�� �p���I�H��10�줸�A�H�᪺�Ʀr�|�ˤ��J�C
	 * 
	 * @param v1
	 *            �Q����
	 * @param v2
	 *            ����
	 * @return ��ӰѼƪ���
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * ���ѡ]�۹�^��T�����k�B��C��o�Ͱ����ɪ����p�ɡA��scale�Ѽƫ� �w��סA�H�᪺�Ʀr�|�ˤ��J�C
	 * 
	 * @param v1
	 *            �Q����
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ܪ�ܻݭn��T��p���I�H��X��C
	 * @return ��ӰѼƪ���
	 */

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * ���Ѻ�T���p�Ʀ�|�ˤ��J�B�z�C
	 * 
	 * @param v
	 *            �ݭn�|�ˤ��J���Ʀ�
	 * @param scale
	 *            �p���I��O�d�X��
	 * @return �|�ˤ��J�᪺���G
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


}

