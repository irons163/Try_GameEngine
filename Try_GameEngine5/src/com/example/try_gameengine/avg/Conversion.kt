package com.example.try_gameengine.avg

import java.util.Arrays
import java.util.Stack
import java.util.StringTokenizer

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
abstract class Conversion : Expression {
    // 數學運算式運算類
    protected var compute: Compute = Compute()

    /**
     * 簡單的四則運算式運算
     // */
    inner class Compute {
        private fun sort(flag: String): Int {
            return sort(flag.toCharArray()[0])
        }

        /**
         * 排序優先順序
         * 
         * @param flag
         * @return
         // */
        private fun sort(flag: Char): Int {
            var result = 0
            when (flag) {
                '+' -> result = 0
                '-' -> result = 0
                '*' -> result = 1
                '/' -> result = 1
                else -> result = -1
            }
            return result
        }

        /**
         * 驗證是否為四則運算
         * 
         * @param exp
         * @return
         // */
        private fun exp(exp: String): Boolean {
            return exp.indexOf("+") != -1 || exp.indexOf("-") != -1 || exp.indexOf("*") != -1 || exp.indexOf(
                "/"
            ) != -1
        }

        /**
         * 解析運算式
         * 
         * @param exp
         * @return
         // */
        fun parse(exp: Any): Int {
            return parse(exp.toString())
        }

        /**
         * 解析運算式
         * 
         * @param exp
         * @return
         // */
        fun parse(exp: String): Int {
            var exp = exp
            var endIndex = 0
            var startIndex = exp.indexOf("(", endIndex)
            while (startIndex != -1) {
                endIndex = exp.indexOf(")", startIndex) + 1
                val segment = exp.substring(startIndex, endIndex)
                val tResult = match(segment.replace("(".toRegex(), "").replace(")".toRegex(), ""))
                exp = exp.replace(segment.toRegex(), tResult.toString())
                startIndex = exp.indexOf("(", 0)
            }
            return match(exp)
        }

        /**
         * 自動匹配四則運算運算式，並返回計算結果
         * 
         * @param exp
         * @return
         // */
        private fun match(exp: String): Int {
            var exp = exp
            if (!isNumber(exp.substring(0, 1))) {
                exp = ("0" + exp).intern()
            }
            for (i in ops!!.indices) {
                val operator: String = ops[i]
                exp = exp.replace(
                    operator.toRegex(),
                    (Expression.Companion.FLAG + operator + Expression.Companion.FLAG)
                        .intern()
                )
            }
            var v1: String? = null
            var v2: String? = null
            val stack = Stack<Any?>()
            val exps: Array<String?>? = split(exp, Expression.Companion.FLAG)
            var type: String? = null
            var sort1 = -1
            var sort2 = -1
            var i = 0
            while (i < exps!!.size) {
                if (exp(exps[i]!!)) {
                    if (type == null) {
                        type = exps[i]
                    } else {
                        sort1 = sort(type)
                        sort2 = sort(exps[i]!!)
                        if (sort1 >= sort2) {
                            v2 = ((if ((type.indexOf("+") != -1)
                                || (type.indexOf("/") != -1)
                                || (type.indexOf("*") != -1)
                            ) "" else type)
                                    + stack.pop()).toString()
                            v1 = stack.pop().toString()
                            stack.push(operate(type, v1, v2))
                            type = exps[i]
                        } else if (sort1 < sort2) {
                            v1 = (stack.pop()).toString()
                            v2 = exps[i + 1].toString()
                            stack.push(Companion.operate(exps[i]!!, v1, v2))
                            i++
                        }
                    }
                } else {
                    stack.push(exps[i])
                }
                i++
            }
            v1 = stack.pop().toString()
            v2 = stack.pop().toString()
            return Companion.operate(type!!, v2, v1)
        }
    }

    companion object {
        private val ops: Array<String>? = arrayOf<String>(
            "\\+", "\\-", "\\*", "\\/", "\\(",
            "\\)"
        )

        /**
         * 檢查是否為字母與數位混合
         * 
         * @param value
         * @return
         // */
        fun isEnglishAndNumeric(value: String?): Boolean {
            if (value == null || value.trim { it <= ' ' }.length == 0) return true
            for (i in 0..<value.length) {
                val letter = value.get(i)
                if ((97 > letter.code || letter.code > 122) && (65 > letter.code || letter.code > 90)
                    && (48 > letter.code || letter.code > 57)
                ) return false
            }

            return true
        }

        /**
         * 分解字串
         * 
         * @param string
         * @param tag
         * @return
         // */
        fun split(string: String?, tag: String?): Array<String?> {
            val str = StringTokenizer(string, tag)
            val result = arrayOfNulls<String>(str.countTokens())
            var index = 0
            while (str.hasMoreTokens()) {
                result[index++] = str.nextToken()
            }
            return result
        }

        /**
         * 分解字串,並返回為list
         * 
         * @param string
         * @param tag
         * @return
         // */
        fun splitToList(string: String?, tag: String?): MutableList<String?> {
            return Arrays.asList<String?>(*split(string, tag))
        }

        /**
         * 檢查是否數字
         * 
         * @param value
         * @return
         // */
        fun isNumber(value: Any?): Boolean {
            try {
                (value as String?)!!.toInt()
            } catch (ne: NumberFormatException) {
                return false
            }
            return true
        }

        /**
         * 檢查是否漢字
         * 
         * @param str
         * @return
         // */
        fun isChinese(value: Any): Boolean {
            var result = false
            try {
                val chars = (value as String).toCharArray()
                for (i in chars.indices) {
                    val bytes = ("" + chars[i]).toByteArray()
                    if (bytes.size == 2) {
                        val ints = IntArray(2)
                        ints[0] = bytes[0].toInt() and 0xff
                        ints[1] = bytes[1].toInt() and 0xff
                        if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                            result = true
                            break
                        }
                    }
                }
            } catch (e: Exception) {
            }
            return result
        }

        /**
         * 四則運算
         * 
         * @param flag
         * @param v1
         * @param v2
         * @return
         // */
        fun operate(flag: String, v1: String, v2: String): Int {
            return operate(
                flag.toCharArray()[0], v1.toInt(),
                v2.toInt()
            )
        }

        /**
         * 四則運算
         * 
         * @param flag
         * @param v1
         * @param v2
         * @return
         // */
        fun operate(flag: Char, v1: Int, v2: Int): Int {
            when (flag) {
                '+' -> return v1 + v2
                '-' -> return v1 + (if (v2 > 0) -v2 else +v2)
                '*' -> return v1 * v2
                '/' -> return v1 / v2
            }
            return 0
        }

        /**
         * 替換指定字串
         * 
         * @param line
         * @param oldString
         * @param newString
         * @return
         // */
        fun replaceMatch(
            line: String, oldString: String,
            newString: String
        ): String {
            var i = 0
            var j = 0
            if ((line.indexOf(oldString, i).also { i = it }) >= 0) {
                val line2: CharArray? = line.toCharArray()
                val newString2: CharArray? = newString.toCharArray()
                val oLength = oldString.length
                val buffer = StringBuffer(line2!!.size)
                buffer.append(line2, 0, i).append(newString2)
                i += oLength
                j = i
                while ((line.indexOf(oldString, i).also { i = it }) > 0) {
                    buffer.append(line2, j, i - j).append(newString2)
                    i += oLength
                    j = i
                }
                buffer.append(line2, j, line2.size - j)
                return buffer.toString()
            } else {
                return line
            }
        }
    }
}
