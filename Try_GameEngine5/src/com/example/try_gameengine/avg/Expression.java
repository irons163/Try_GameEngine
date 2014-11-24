package com.example.try_gameengine.avg;

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
public interface Expression {

	// ����ü�
	Random GLOBAL_RAND = new Random();

	// �w�]�ܼ�1,�Ω�O����e��ܶ�
	String V_SELECT_KEY = "SELECT";

	// ���A��
	String BRACKET_LEFT_TAG = "(";

	// �k�A��
	String BRACKET_RIGHT_TAG = ")";

	// �{���X���q�}�l�аO
	String BEGIN_TAG = "begin";

	// �{���X���q�����аO
	String END_TAG = "end";

	// �{���X���q�եμаO
	static String CALL_TAG = "call";

	// �w�s��s�аO
	String RESET_CACHE_TAG = "reset";
	
	// �֭p��J��ƼаO
	String IN_TAG = "in";

	// �֭p��J��ư���]��X�^�аO
	String OUT_TAG = "out";

	// �h��аO
	String SELECTS_TAG = "selects";

	// �C�L�аO
	String PRINT_TAG = "print";

	// �üƼаO
	String RAND_TAG = "rand";

	// �]�w�����ܼƼаO
	String SET_TAG = "set";

	// ���J�����}���аO
	String INCLUDE_TAG = "include";

	// ����P�w�аO
	String IF_TAG = "if";

	// ����P�w�����аO
	String IF_END_TAG = "endif";

	// ���аO
	String ELSE_TAG = "else";

	// �H�U���`���Ÿ�
	String FLAG_L_TAG = "//";

	String FLAG_C_TAG = "#";

	String FLAG_I_TAG = "'";

	String FLAG_LS_B_TAG = "/*";

	String FLAG_LS_E_TAG = "*/";

	String FLAG = "@";

	char FLAG_CHAR = FLAG.toCharArray()[0];

}

