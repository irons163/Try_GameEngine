package com.example.try_gameengine.avg

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.util.Locale

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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 // */
class StringUtils  // PS:因為手機容量問題，比較PC版有所簡化
private constructor() {
    /**
     * 批量轉換字串陣列編碼
     * 
     * @param s
     * @return
     // */
    fun getString(
        strs: Array<String?>, sourceEncoding: String,
        objectEncoding: String
    ): Array<String?>? {
        val ss = arrayOfNulls<String>(strs.size)
        try {
            for (i in strs.indices) {
                val aa: ByteArray? = strs[i]!!.toByteArray(charset(sourceEncoding))
                ss[i] = kotlin.text.String(aa!!, charset(objectEncoding))
            }
        } catch (e: UnsupportedEncodingException) {
            return null
        }
        return ss
    }

    companion object {
        /**
         * 過濾指定字串
         * 
         * @param string
         * @param oldString
         * @param newString
         * @return
         // */
        fun replace(
            string: String?, oldString: String,
            newString: String?
        ): String? {
            if (string == null) return null
            if (newString == null) return string
            var i = 0
            if ((string.indexOf(oldString, i).also { i = it }) >= 0) {
                val string2: CharArray? = string.toCharArray()
                val newString2: CharArray? = newString.toCharArray()
                val oLength = oldString.length
                val buf = StringBuffer(string2!!.size)
                buf.append(string2, 0, i).append(newString2)
                i += oLength
                var j: Int
                j = i
                while ((string.indexOf(oldString, i).also { i = it }) > 0) {
                    buf.append(string2, j, i - j).append(newString2)
                    i += oLength
                    j = i
                }

                buf.append(string2, j, string2.size - j)
                return buf.toString()
            } else {
                return string
            }
        }

        /**
         * 不匹配大小寫的過濾指定字串
         * 
         * @param line
         * @param oldString
         * @param newString
         * @return
         // */
        fun replaceIgnoreCase(
            line: String?, oldString: String,
            newString: String
        ): String? {
            if (line == null) return null
            val lcLine = line.lowercase(Locale.getDefault())
            val lcOldString = oldString.lowercase(Locale.getDefault())
            var i = 0
            if ((lcLine.indexOf(lcOldString, i).also { i = it }) >= 0) {
                val line2: CharArray? = line.toCharArray()
                val newString2: CharArray? = newString.toCharArray()
                val oLength = oldString.length
                val buf = StringBuffer(line2!!.size)
                buf.append(line2, 0, i).append(newString2)
                i += oLength
                var j: Int
                j = i
                while ((lcLine.indexOf(lcOldString, i).also { i = it }) > 0) {
                    buf.append(line2, j, i - j).append(newString2)
                    i += oLength
                    j = i
                }

                buf.append(line2, j, line2.size - j)
                return buf.toString()
            } else {
                return line
            }
        }

        /**
         * 不匹配大小寫的過濾指定字串
         * 
         * @param line
         * @param oldString
         * @param newString
         * @param count
         * @return
         // */
        fun replaceIgnoreCase(
            line: String?, oldString: String,
            newString: String, count: IntArray?
        ): String? {
            if (line == null) return null
            val lcLine = line.lowercase(Locale.getDefault())
            val lcOldString = oldString.lowercase(Locale.getDefault())
            var i = 0
            if ((lcLine.indexOf(lcOldString, i).also { i = it }) >= 0) {
                var counter = 1
                val line2: CharArray? = line.toCharArray()
                val newString2: CharArray? = newString.toCharArray()
                val oLength = oldString.length
                val buf = StringBuffer(line2!!.size)
                buf.append(line2, 0, i).append(newString2)
                i += oLength
                var j: Int
                j = i
                while ((lcLine.indexOf(lcOldString, i).also { i = it }) > 0) {
                    counter++
                    buf.append(line2, j, i - j).append(newString2)
                    i += oLength
                    j = i
                }

                buf.append(line2, j, line2.size - j)
                count!![0] = counter
                return buf.toString()
            } else {
                return line
            }
        }

        /**
         * 以指定條件過濾字串
         * 
         * @param line
         * @param oldString
         * @param newString
         * @param count
         * @return
         // */
        fun replace(
            line: String?, oldString: String,
            newString: String, count: IntArray?
        ): String? {
            if (line == null) return null
            var i = 0
            if ((line.indexOf(oldString, i).also { i = it }) >= 0) {
                var counter = 1
                val line2: CharArray? = line.toCharArray()
                val newString2: CharArray? = newString.toCharArray()
                val oLength = oldString.length
                val buf = StringBuffer(line2!!.size)
                buf.append(line2, 0, i).append(newString2)
                i += oLength
                var j: Int
                j = i
                while ((line.indexOf(oldString, i).also { i = it }) > 0) {
                    counter++
                    buf.append(line2, j, i - j).append(newString2)
                    i += oLength
                    j = i
                }

                buf.append(line2, j, line2.size - j)
                count!![0] = counter
                return buf.toString()
            } else {
                return line
            }
        }

        /**
         * 以指定字元分割字串
         * 
         * @param str
         * @param c
         * @return
         // */
        fun split(str: String?, c: Char): Array<String?> {
            var str = str
            str += c
            var n = 0
            for (i in 0..<str.length) {
                if (str.get(i) == c) {
                    n++
                }
            }
            val out: Array<String?>? = arrayOfNulls<String>(n)
            for (i in 0..<n) {
                val index = str!!.indexOf(c)
                out!![i] = str.substring(0, index)
                str = str.substring(index + 1, str.length)
            }
            return out!!
        }

        /**
         * 檢查一組字串是否完全由中文組成
         * 
         * @param str
         * @return
         // */
        fun isChinaLanguage(str: String): Boolean {
            val chars = str.toCharArray()
            val ints = IntArray(2)
            var isChinese = false
            val length = chars.size
            var bytes: ByteArray? = null
            for (i in 0..<length) {
                bytes = ("" + chars[i]).toByteArray()
                if (bytes.size == 2) {
                    ints[0] = bytes[0].toInt() and 0xff
                    ints[1] = bytes[1].toInt() and 0xff
                    if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                        isChinese = true
                    }
                } else {
                    return false
                }
            }
            return isChinese
        }


        /**
         * 判斷是否為null
         * 
         * @param param
         * @return
         // */
        fun isEmpty(param: String?): Boolean {
            return param == null || param.length == 0 || param.trim { it <= ' ' } == ""
        }

        /**
         * 判斷是否可能為true
         * 
         * @param param
         * @return
         // */
        fun isCovertBoolean(param: String?): Boolean {
            if (isEmpty(param)) return false
            when (param!!.get(0)) {
                '1', 'T', 'Y', 't', 'y' -> return true
            }
            return false
        }

        /**
         * 顯示指定編碼下的字元長度
         * 
         * @param encoding
         * @param str
         * @return
         // */
        fun getBytesLengthOfEncoding(encoding: String, str: String?): Int {
            if (str == null || str.length == 0) return 0
            try {
                val bytes: ByteArray? = str.toByteArray(charset(encoding))
                val length = bytes!!.size
                return length
            } catch (exception: UnsupportedEncodingException) {
                System.err.println(exception.message)
            }
            return 0
        }

        /**
         * 轉化指定字串為指定編碼格式
         * 
         * @param context
         * @param encoding
         * @return
         // */
        fun getSpecialString(context: String, encoding: String?): String? {
            try {
                val `in` = ByteArrayInputStream(
                    context
                        .toByteArray()
                )
                val isr = InputStreamReader(`in`, encoding)
                val reader = BufferedReader(isr)
                val buffer = StringBuffer()
                var result: String?
                while ((reader.readLine().also { result = it }) != null) {
                    buffer.append(result)
                }
                return buffer.toString()
            } catch (ex: Exception) {
                return context
            }
        }

        /**
         * 檢查指定字串中是否存在中文字元。
         * 
         * @param checkStr
         * 指定需要檢查的字串。
         * @return 邏輯值（True Or False）。
         // */
        fun hasChinese(checkStr: String): Boolean {
            var checkedStatus = false
            var isError = false
            val spStr = " _-"
            val checkStrLength = checkStr.length - 1
            for (i in 0..checkStrLength) {
                var ch = checkStr.get(i)
                if (ch < '\u007e') {
                    ch = ch.uppercaseChar()
                    if (((ch < 'A') || (ch > 'Z')) && ((ch < '0') || (ch > '9'))
                        && (spStr.indexOf(ch) < 0)
                    ) {
                        isError = true
                    }
                }
            }
            checkedStatus = !isError
            return checkedStatus
        }

        /**
         * 以規則運算式部分截取
         * 
         * @param value
         * @param pattern
         * @param replacement
         * @return
         // */
        fun subStitute(
            value: String?, pattern: String?,
            replacement: String?
        ): String? {
            var value = value
            if (value == null || value.length == 0) return value
            if (pattern == null || pattern.length == 0) return value
            val sb = StringBuffer()
            do {
                val patternIndex = value!!.indexOf(pattern)
                if (patternIndex == -1) {
                    sb.append(value)
                    break
                }
                sb.append(value.substring(0, patternIndex) + replacement)
                value = value.substring(
                    patternIndex + pattern.length, value
                        .length
                )
            } while (true)
            return sb.toString()
        }

        /**
         * 檢查是否為純字母
         * 
         * @param value
         * @return
         // */
        fun isAlphabet(value: String?): Boolean {
            if (value == null || value.length == 0) return false
            for (i in 0..<value.length) {
                val c = value.get(i).uppercaseChar()
                if ('A' <= c && c <= 'Z') return true
            }
            return false
        }

        /**
         * 檢查是否為字母與數位混合
         * 
         * @param value
         * @return
         // */
        fun isAlphabetNumeric(value: String?): Boolean {
            if (value == null || value.trim { it <= ' ' }.length == 0) return true
            for (i in 0..<value.length) {
                val letter = value.get(i)
                if (('a' > letter || letter > 'z')
                    && ('A' > letter || letter > 'Z')
                    && ('0' > letter || letter > '9')
                ) return false
            }
            return true
        }

        /**
         * 過濾首字元
         * 
         * @param str
         * @param pattern
         * @param replace
         * @return
         // */
        fun replaceFirst(
            str: String, pattern: String,
            replace: String?
        ): String {
            var s = 0
            var e = 0
            val result = StringBuffer()

            if ((str.indexOf(pattern, s).also { e = it }) >= 0) {
                result.append(str.substring(s, e))
                result.append(replace)
                s = e + pattern.length
            }
            result.append(str.substring(s))
            return result.toString()
        }

        /**
         * 以" "充滿指定字串
         * 
         * @param str
         * @param length
         * @return
         // */
        fun fillSpace(str: String, length: Int): String {
            val strLength = str.length
            if (strLength >= length) {
                return str
            }
            val spaceBuffer = StringBuffer()
            for (i in 0..<(length - strLength)) {
                spaceBuffer.append(" ")
            }
            return str + spaceBuffer.toString()
        }

        /**
         * 得到定位元組長的字串，位元數不足右補空格
         * 
         * @param str
         * @param length
         * @return
         // */
        fun fillSpaceByByte(str: String, length: Int): String {
            val strbyte = str.toByteArray()
            val strLength = strbyte.size
            if (strLength >= length) {
                return str
            }
            val spaceBuffer = StringBuffer()
            for (i in 0..<(length - strLength)) {
                spaceBuffer.append(" ")
            }
            return str + spaceBuffer.toString()
        }

        /**
         * 返回指定字串長度
         * 
         * @param s
         * @return
         // */
        fun length(s: String?): Int {
            if (s == null) return 0
            else return s.toByteArray().size
        }

        /**
         * 將字串的數位取出到一個字串中
         * 
         * @param s
         * String
         * @return String
         // */
        fun getDigitsOnly(s: String): String {
            val digitsOnly = StringBuffer()
            var c: Char
            for (i in 0..<s.length) {
                c = s.get(i)
                if (Character.isDigit(c)) {
                    digitsOnly.append(c)
                }
            }
            return digitsOnly.toString()
        }

        /**
         * 獲得特定字元總數
         * 
         * @param str
         * @param chr
         * @return
         // */
        fun charCount(str: String?, chr: Char): Int {
            var count = 0
            if (str != null) {
                val length = str.length
                for (i in 0..<length) {
                    if (str.get(i) == chr) {
                        count++
                    }
                }
                return count
            }
            return count
        }

        /**
         * 返回指定字元位置前資料
         * 
         * @param str
         * @param chr
         * @param max
         * @return
         // */
        fun charSubstring(str: String?, chr: Char, max: Int): String {
            var count = 0
            val sbr = StringBuffer()
            if (str != null) {
                val length = str.length
                for (i in 0..<length) {
                    val result = str.get(i)
                    sbr.append(result)
                    if (result == chr) {
                        count++
                    }
                    if (count == max) {
                        return sbr.toString()
                    }
                }
            }
            return sbr.toString()
        }

        /**
         * 清除字串陣列中空格
         * 
         * @param strings
         * @return
         // */
        fun trim(s: Array<String?>?): Array<String?>? {
            if (s == null) {
                return null
            }
            var i = 0
            val len = s.size
            while (i < len) {
                s[i] = s[i]!!.trim { it <= ' ' }
                i++
            }
            return s
        }

        /**
         * 整理字串中指定字元，清空指定符號
         * 
         * @param s
         * @param delimit
         * @return
         // */
        fun trim(s: String?, delimit: CharArray?): String? {
            if (s == null) {
                return null
            }
            val length = s.length
            var beginIndex = 0
            var endIndex = length
            while (beginIndex < length) {
                val c = s.get(beginIndex)
                var found = false
                for (i in delimit!!.indices) {
                    if (delimit[i] != c) {
                        continue
                    }
                    found = true
                    break
                }
                if (!found) break
                beginIndex++
            }

            while (endIndex > beginIndex) {
                val c = s.get(endIndex - 1)
                var found = false
                for (i in delimit!!.indices) {
                    if (delimit[i] != c) continue
                    found = true
                    break
                }

                if (!found) break
                endIndex--
            }

            if (beginIndex == endIndex) return ""
            if (beginIndex > 0 || endIndex < length) return s.substring(beginIndex, endIndex)
            else return s
        }
    }
}
