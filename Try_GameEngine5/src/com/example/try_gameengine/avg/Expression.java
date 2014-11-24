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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public interface Expression {

	// 全域亂數
	Random GLOBAL_RAND = new Random();

	// 預設變數1,用於記錄當前選擇項
	String V_SELECT_KEY = "SELECT";

	// 左括弧
	String BRACKET_LEFT_TAG = "(";

	// 右括弧
	String BRACKET_RIGHT_TAG = ")";

	// 程式碼片段開始標記
	String BEGIN_TAG = "begin";

	// 程式碼片段結束標記
	String END_TAG = "end";

	// 程式碼片段調用標記
	static String CALL_TAG = "call";

	// 緩存刷新標記
	String RESET_CACHE_TAG = "reset";
	
	// 累計輸入資料標記
	String IN_TAG = "in";

	// 累計輸入資料停止（輸出）標記
	String OUT_TAG = "out";

	// 多選標記
	String SELECTS_TAG = "selects";

	// 列印標記
	String PRINT_TAG = "print";

	// 亂數標記
	String RAND_TAG = "rand";

	// 設定環境變數標記
	String SET_TAG = "set";

	// 載入內部腳本標記
	String INCLUDE_TAG = "include";

	// 條件判定標記
	String IF_TAG = "if";

	// 條件判定結束標記
	String IF_END_TAG = "endif";

	// 轉折標記
	String ELSE_TAG = "else";

	// 以下為注視符號
	String FLAG_L_TAG = "//";

	String FLAG_C_TAG = "#";

	String FLAG_I_TAG = "'";

	String FLAG_LS_B_TAG = "/*";

	String FLAG_LS_E_TAG = "*/";

	String FLAG = "@";

	char FLAG_CHAR = FLAG.toCharArray()[0];

}

