package com.example.try_gameengine.avg

import java.util.Random

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
 // */
interface Expression {
    companion object {
        // 程式碼片段調用標記
        const val CALL_TAG: String = "call"

        // 全域亂數
        val GLOBAL_RAND: Random = Random()

        // 預設變數1,用於記錄當前選擇項
        const val V_SELECT_KEY: String = "SELECT"

        // 左括弧
        const val BRACKET_LEFT_TAG: String = "("

        // 右括弧
        const val BRACKET_RIGHT_TAG: String = ")"

        // 程式碼片段開始標記
        const val BEGIN_TAG: String = "begin"

        // 程式碼片段結束標記
        const val END_TAG: String = "end"

        // 緩存刷新標記
        const val RESET_CACHE_TAG: String = "reset"

        // 累計輸入資料標記
        const val IN_TAG: String = "in"

        // 累計輸入資料停止（輸出）標記
        const val OUT_TAG: String = "out"

        // 多選標記
        const val SELECTS_TAG: String = "selects"

        // 列印標記
        const val PRINT_TAG: String = "print"

        // 亂數標記
        const val RAND_TAG: String = "rand"

        // 設定環境變數標記
        const val SET_TAG: String = "set"

        // 載入內部腳本標記
        const val INCLUDE_TAG: String = "include"

        // 條件判定標記
        const val IF_TAG: String = "if"

        // 條件判定結束標記
        const val IF_END_TAG: String = "endif"

        // 轉折標記
        const val ELSE_TAG: String = "else"

        // 以下為注視符號
        const val FLAG_L_TAG: String = "//"

        const val FLAG_C_TAG: String = "#"

        const val FLAG_I_TAG: String = "'"

        const val FLAG_LS_B_TAG: String = "/*"

        const val FLAG_LS_E_TAG: String = "*/"

        const val FLAG: String = "@"

        val FLAG_CHAR: Char = FLAG.toCharArray()[0]
    }
}

