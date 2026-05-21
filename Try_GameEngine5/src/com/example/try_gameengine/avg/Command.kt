package com.example.try_gameengine.avg

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Serializable
import java.util.Arrays
import java.util.Collections
import java.util.Locale

/**
 * Copyright 2008 - 2010
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
 * @version 0.1.2
 // */
class Command : Conversion, Serializable {
    // 緩存腳本名
    private var cacheCommandName: String? = null

    // 注釋標記中
    private var flaging = false

    // 判斷標記中
    private var ifing = false

    // 函數標記中
    private var functioning = false

    // 分支標記
    private var esleflag = false

    private var backIfBool = false

    private var executeCommand: String? = null

    private var nowPosFlagName: String? = null

    private var addCommand = false

    private var isInnerCommand = false

    var isRead: Boolean = false

    private var isCall = false

    private var isCache = false

    private var if_bool = false

    private var elseif_bool = false

    private var innerCommand: Command? = null

    private var commandString: String = ""

    private var temps: MutableList<Any?>? = null

    private var printTags: MutableList<Any?>? = null

    private var randTags: MutableList<Any?>? = null

    private var scriptSize = 0

    private var offsetPos = 0

    // 腳本數據清單
    private var scriptList: MutableList<Any?>? = null

    // 腳本名
    private var scriptName: String? = null

    /**
     * 構造函數，載入指定指令檔
     * 
     * @param fileName
     // */
    constructor(fileName: String?) {
        initCommand()
        formatCommand(fileName)
    }

    /**
     * 構造函數，載入指定list腳本
     * 
     * @param resource
     // */
    constructor(fileName: String?, resource: MutableList<Any?>) {
        initCommand()
        formatCommand("function", resource)
    }

    fun formatCommand(fileName: String?) {
        formatCommand(fileName, includeFile(fileName))
    }

    fun formatCommand(name: String?, resource: MutableList<Any?>) {
        conditionEnvironmentList!!.clear()
        setEnvironmentList!!.put(Expression.Companion.V_SELECT_KEY, "-1")
        scriptName = name
        scriptList = resource
        scriptSize = scriptList!!.size
        offsetPos = 0
        flaging = false
        ifing = false
        isCache = true
        esleflag = false
        backIfBool = false
    }

    private fun setupIF(
        commandString: String, nowPosFlagName: String?,
        setEnvironmentList: MutableMap<Any?, Any?>, conditionEnvironmentList: MutableMap<Any?, Any?>
    ): Boolean {
        var result = false
        conditionEnvironmentList.put(nowPosFlagName, false)
        try {
            val temps: MutableList<Any?> = commandSplit(commandString)
            var valueA: Any? = temps.get(1) as String?
            var valueB: Any? = temps.get(3) as String?
            valueA = if (setEnvironmentList.get(valueA) == null)
                valueA
            else
                setEnvironmentList.get(valueA)
            valueB = if (setEnvironmentList.get(valueB) == null)
                valueB
            else
                setEnvironmentList.get(valueB)

            // 非純數字
            if (!Conversion.Companion.isNumber(valueB)) {
                try {
                    // 嘗試四則運算公式匹配
                    valueB = compute.parse(valueB!!)
                } catch (e: Exception) {
                }
            }
            val condition = temps.get(2) as String?

            // 無法判定
            if (valueA == null || valueB == null) {
                conditionEnvironmentList
                    .put(nowPosFlagName, false)
            }

            // 相等
            if ("==" == condition) {
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (valueA.toString() == valueB.toString()).also { result = it })
                // 非等
            } else if ("!=" == condition) {
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (valueA.toString() != valueB.toString()).also { result = it })
                // 大於
            } else if (">" == condition) {
                val numberA = valueA.toString().toInt()
                val numberB = valueB.toString().toInt()
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (numberA > numberB).also { result = it })
                // 小於
            } else if ("<" == condition) {
                val numberA = valueA.toString().toInt()
                val numberB = valueB.toString().toInt()
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (numberA < numberB).also { result = it })

                // 大於等於
            } else if (">=" == condition) {
                val numberA = valueA.toString().toInt()
                val numberB = valueB.toString().toInt()
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (numberA >= numberB).also { result = it })
                // 小於等於
            } else if ("<=" == condition) {
                val numberA = valueA.toString().toInt()
                val numberB = valueB.toString().toInt()
                conditionEnvironmentList.put(
                    nowPosFlagName,
                    (numberA <= numberB).also { result = it })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 打開腳本緩存
     * 
     // */
    fun openCache() {
        isCache = true
    }

    /**
     * 關閉腳本緩存
     * 
     // */
    fun closeCache() {
        isCache = false
    }

    /**
     * 當前腳本行緩存名
     * 
     * @return
     // */
    fun nowCacheOffsetName(): String {
        return (scriptName + Expression.Companion.FLAG + offsetPos + Expression.Companion.FLAG + commandString)
            .lowercase(Locale.getDefault())
    }

    @get:Synchronized
    val reads: Array<String?>
        /**
         * 返回當前的讀入資料集合
         * 
         * @return
         // */
        get() {
            var result =
                reader.toString()
            result = result.replace(
                Expression.Companion.SELECTS_TAG.toRegex(),
                ""
            )
            return Conversion.Companion.split(
                result,
                Expression.Companion.FLAG
            )
        }

    /**
     * 返回指定索引的讀入資料
     * 
     * @param index
     * @return
     // */
    @Synchronized
    fun getRead(index: Int): String? {
        try {
            return this.reads[index]
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * 注入選擇變數
     * 
     * @param type
     // */
    fun select(type: Int) {
        if (innerCommand != null) {
            innerCommand!!.setVariable(Expression.Companion.V_SELECT_KEY, type.toString())
        }
        setVariable(Expression.Companion.V_SELECT_KEY, type.toString())
    }

    val select: String?
        get() = getVariable(Expression.Companion.V_SELECT_KEY) as String?

    /**
     * 插入變數
     * 
     * @param key
     * @param value
     // */
    fun setVariable(key: String?, value: Any?) {
        setEnvironmentList!!.put(key, value)
    }

    var variables: MutableMap<Any?, Any?>
        /**
         * 返回變數集合
         * 
         * @return
         // */
        get() = setEnvironmentList!!
        /**
         * 插入變數集合
         * 
         * @param vars
         // */
        set(vars) {
            setEnvironmentList!!.putAll(vars)
        }

    fun getVariable(key: String?): Any? {
        return setEnvironmentList!!.get(key)
    }

    /**
     * 刪除變數
     * 
     * @param key
     // */
    fun removeVariable(key: String?) {
        setEnvironmentList!!.remove(key)
    }

    /**
     * 判定腳本是否允許繼續解析
     * 
     * @return
     // */
    fun next(): Boolean {
        return (offsetPos < scriptSize)
    }

    /**
     * 跳轉向指定索引位置
     * 
     * @param offset
     * @return
     // */
    fun gotoIndex(offset: Int): Boolean {
        val result = offset < scriptSize && offset > 0 && offset != offsetPos
        if (result) {
            offsetPos = offset
        }
        return result
    }

    /**
     * 批次處理執行腳本，並返回可用list結果
     * 
     * @return
     // */
    fun batchToList(): MutableList<Any?> {
        val reslist: MutableList<Any?> = ArrayList<Any?>(scriptSize)
        while (next()) {
            val execute = doExecute()
            if (execute != null) {
                reslist.add(execute)
            }
        }
        return reslist
    }

    /**
     * 批次處理執行腳本，並返回可用string結果
     * 
     * @return
     // */
    fun batchToString(): String {
        val resString = StringBuffer(scriptSize * 10)
        while (next()) {
            val execute = doExecute()
            if (execute != null) {
                resString.append(execute)
                resString.append("\n")
            }
        }
        return resString.toString()
    }

    private fun setupSET() {
        if (commandString.startsWith(Expression.Companion.SET_TAG)) {
            val temps: MutableList<Any?> = Companion.commandSplit(commandString!!)
            val len = temps.size
            var result: String? = null
            if (len == 4) {
                result = temps.get(3).toString()
            } else if (len > 4) {
                val sbr = StringBuffer(len)
                for (i in 3..<temps.size) {
                    sbr.append(temps.get(i))
                }
                result = sbr.toString()
            }

            if (result != null) {
                // 替換已有變數字元
                val set: MutableSet<*> = setEnvironmentList!!.entries
                val it: MutableIterator<*> = set.iterator()
                while (it.hasNext()) {
                    val entry = it.next() as MutableMap.MutableEntry<*, *>
                    if (!(result!!.startsWith("\"") && result.endsWith("\""))) {
                        result = Conversion.Companion.replaceMatch(
                            result!!, entry.key as String,
                            entry.value.toString()
                        )
                    }
                }
                // 當為普通字串時
                if (result!!.startsWith("\"") && result.endsWith("\"")) {
                    setEnvironmentList!!.put(
                        temps.get(1), result.substring(
                            1,
                            result.length - 1
                        )
                    )
                } else if (Conversion.Companion.isChinese(result) || Conversion.Companion.isEnglishAndNumeric(
                        result
                    )
                ) {
                    setEnvironmentList!!.put(temps.get(1), result)
                } else {
                    // 當為數學運算式時
                    setEnvironmentList!!.put(temps.get(1), compute.parse(result))
                }
            }
            addCommand = false
        }
    }

    /**
     * 亂數處理
     * 
     // */
    private fun setupRandom() {
        // 亂數判定
        if (commandString.indexOf(Expression.Companion.RAND_TAG) != -1) {
            randTags = Companion.getNameTags(
                commandString!!,
                Expression.Companion.RAND_TAG
                        + Expression.Companion.BRACKET_LEFT_TAG,
                Expression.Companion.BRACKET_RIGHT_TAG
            )
            if (randTags != null) {
                val it = randTags!!.iterator()
                while (it.hasNext()) {
                    val key = it.next() as String?
                    val value: Any? = setEnvironmentList!!.get(key)
                    // 已存在變數
                    if (value != null) {
                        commandString = Conversion.Companion.replaceMatch(
                            commandString,
                            (Expression.Companion.RAND_TAG + Expression.Companion.BRACKET_LEFT_TAG + key + Expression.Companion.BRACKET_RIGHT_TAG)
                                .intern(), value.toString()
                        )
                        // 設定有亂數產生範圍
                    } else if (Conversion.Companion.isNumber(key)) {
                        commandString = Conversion.Companion.replaceMatch(
                            commandString,
                            (Expression.Companion.RAND_TAG + Expression.Companion.BRACKET_LEFT_TAG + key + Expression.Companion.BRACKET_RIGHT_TAG)
                                .intern(),
                            Expression.Companion.GLOBAL_RAND
                                .nextInt(key!!.toInt()).toString()
                        )
                        // 無設定
                    } else {
                        commandString = Conversion.Companion.replaceMatch(
                            commandString,
                            (Expression.Companion.RAND_TAG + Expression.Companion.BRACKET_LEFT_TAG + key + Expression.Companion.BRACKET_RIGHT_TAG)
                                .intern(), Expression.Companion.GLOBAL_RAND.nextInt().toString()
                        )
                    }
                }
            }
        }
    }

    private fun innerCallTrue() {
        isCall = true
        isInnerCommand = true
    }

    private fun innerCallFalse() {
        isCall = false
        isInnerCommand = false
        innerCommand = null
    }

    /**
     * 逐行執行指令碼命令
     * 
     * @return
     // */
    @Synchronized
    fun doExecute(): String? {
        executeCommand = null

        addCommand = true

        isInnerCommand = (innerCommand != null)

        if_bool = false

        elseif_bool = false

        try {
            // 執行call命令
            if (isInnerCommand && isCall) {
                this.variables = innerCommand!!.variables
                if (innerCommand!!.next()) {
                    return innerCommand!!.doExecute()
                } else {
                    innerCallFalse()
                    return executeCommand
                }
                // 執行內部腳本
            } else if (isInnerCommand && !isCall) {
                this.variables = innerCommand!!.variables
                if (innerCommand!!.next()) {
                    return innerCommand!!.doExecute()
                } else {
                    innerCommand = null
                    isInnerCommand = false
                    return executeCommand
                }
            }

            nowPosFlagName = offsetPos.toString()
            val length: Int = conditionEnvironmentList!!.size
            if (length > 0) {
                val ifResult: Any? = conditionEnvironmentList!!.get(length - 1)
                if (ifResult != null) {
                    backIfBool = (ifResult as Boolean)
                }
            }

            // 獲得全行命令
            commandString = (scriptList!!.get(offsetPos) as String)
            // 清空腳本緩存
            if (commandString.startsWith(Expression.Companion.RESET_CACHE_TAG)) {
                resetCache()
                return executeCommand
            }

            if (isCache) {
                // 獲得緩存命令列名
                cacheCommandName = nowCacheOffsetName()
                // 讀取緩存的腳本
                val cache: Any? = cacheScript!!.get(cacheCommandName)
                if (cache != null) {
                    return cache as String
                }
            }

            // 注釋中
            if (flaging) {
                flaging =
                    !(commandString.startsWith(Expression.Companion.FLAG_LS_E_TAG) || commandString
                        .endsWith(Expression.Companion.FLAG_LS_E_TAG))
                return executeCommand
            }

            if (!flaging) {
                // 全域注釋
                if (commandString.startsWith(Expression.Companion.FLAG_LS_B_TAG)
                    && !commandString.endsWith(Expression.Companion.FLAG_LS_E_TAG)
                ) {
                    flaging = true
                    return executeCommand
                } else if (commandString.startsWith(Expression.Companion.FLAG_LS_B_TAG)
                    && commandString.endsWith(Expression.Companion.FLAG_LS_E_TAG)
                ) {
                    return executeCommand
                }
            }

            // 執行亂數標記
            setupRandom()

            // 執行獲取變數標記
            setupSET()

            // 結束腳本中程式碼片段標記
            if (commandString.endsWith(Expression.Companion.END_TAG)) {
                functioning = false
                return executeCommand
            }

            // 標注腳本中程式碼片段標記
            if (commandString.startsWith(Expression.Companion.BEGIN_TAG)) {
                temps = Companion.commandSplit(commandString!!)
                if (temps!!.size == 2) {
                    functioning = true
                    functions!!.put(temps!!.get(1), ArrayList<Any?>(10))
                    return executeCommand
                }
            }

            // 開始記錄程式碼片段
            if (functioning) {
                val function = functions!!
                    .get(functions!!.size - 1) as ArrayList<Any?>
                function.add(commandString)
                return executeCommand
            }

            // 執行程式碼片段調用標記
            if (commandString.startsWith(Expression.Companion.CALL_TAG) && !isCall) {
                temps = Companion.commandSplit(commandString!!)
                if (temps!!.size == 2) {
                    val functionName = temps!!.get(1) as String?
                    val funs: MutableList<Any?>? = functions!!.get(functionName) as ArrayList<Any?>?
                    if (funs != null) {
                        innerCommand = Command(
                            (scriptName + Expression.Companion.FLAG
                                    + functionName), funs
                        )
                        innerCommand!!.closeCache()
                        innerCommand!!.variables = this.variables
                        innerCallTrue()
                        return null
                    }
                }
            }

            if (!if_bool && !elseif_bool) {
                // 獲得循序結構條件
                if_bool = commandString.startsWith(Expression.Companion.IF_TAG)
                elseif_bool = commandString.startsWith(Expression.Companion.ELSE_TAG)
            }

            // 條件判斷a
            if (if_bool) {
                esleflag = setupIF(
                    commandString!!, nowPosFlagName,
                    setEnvironmentList!!, conditionEnvironmentList!!
                )
                addCommand = false
                ifing = true
                // 條件判斷b
            } else if (elseif_bool) {
                val value: Array<String?> = Conversion.Companion.split(commandString, " ")
                if (!backIfBool && !esleflag) {
                    // 存在if判斷
                    if (value.size > 1 && Expression.Companion.IF_TAG == value[1]) {
                        esleflag = setupIF(
                            commandString.replace(
                                Expression.Companion.ELSE_TAG.toRegex(),
                                ""
                            ).trim { it <= ' ' }, nowPosFlagName, setEnvironmentList!!,
                            conditionEnvironmentList!!
                        )
                        addCommand = false
                        // 單純的else
                    } else if (value.size == 1 && Expression.Companion.ELSE_TAG == value[0]) {
                        esleflag = setupIF(
                            "if 1==1", nowPosFlagName,
                            setEnvironmentList!!, conditionEnvironmentList!!
                        )
                        addCommand = false
                    }
                } else {
                    addCommand = false
                    conditionEnvironmentList!!.put(nowPosFlagName, false)
                }
            }
            // 分支結束
            if (commandString.startsWith(Expression.Companion.IF_END_TAG)) {
                conditionEnvironmentList!!.clear()
                backIfBool = false
                addCommand = false
                ifing = false
                if_bool = false
                elseif_bool = false
                return null
            }
            if (backIfBool) {
                // 載入內部腳本
                if (commandString.startsWith(Expression.Companion.INCLUDE_TAG)) {
                    temps = Companion.commandSplit(commandString!!)
                    val fileName = temps!!.get(1) as String?
                    if (fileName != null) {
                        innerCommand = Command(fileName)
                        isInnerCommand = true
                        return null
                    }
                }
            } else if (commandString.startsWith(Expression.Companion.INCLUDE_TAG) && !ifing && !backIfBool && !esleflag) {
                temps = Companion.commandSplit(commandString!!)
                val fileName = temps!!.get(1) as String?
                if (fileName != null) {
                    innerCommand = Command(fileName)
                    isInnerCommand = true
                    return null
                }
            }
            // 選擇項列表結束
            if (commandString.startsWith(Expression.Companion.OUT_TAG)) {
                isRead = false
                addCommand = false
                executeCommand = (Expression.Companion.SELECTS_TAG + " " + reader.toString())
                    .intern()
            }
            // 累計選擇項
            if (isRead) {
                reader!!.append(commandString)
                reader!!.append(Expression.Companion.FLAG)
                addCommand = false
            }
            // 選擇項列表
            if (commandString.startsWith(Expression.Companion.IN_TAG)) {
                reader!!.delete(0, reader!!.length)
                isRead = true
                return executeCommand
            }

            // 輸出腳本判斷
            if (addCommand && ifing) {
                if (backIfBool && esleflag) {
                    executeCommand = commandString
                }
            } else if (addCommand) {
                executeCommand = commandString
            }

            // 替換腳本字串內容
            if (executeCommand != null) {
                printTags = Companion.getNameTags(
                    executeCommand!!,
                    Expression.Companion.PRINT_TAG
                            + Expression.Companion.BRACKET_LEFT_TAG,
                    Expression.Companion.BRACKET_RIGHT_TAG
                )
                if (printTags != null) {
                    val it = printTags!!.iterator()
                    while (it.hasNext()) {
                        val key = it.next() as String?
                        val value: Any? = setEnvironmentList!!.get(key)
                        if (value != null) {
                            executeCommand = Conversion.Companion.replaceMatch(
                                executeCommand!!,
                                (Expression.Companion.PRINT_TAG + Expression.Companion.BRACKET_LEFT_TAG + key + Expression.Companion.BRACKET_RIGHT_TAG)
                                    .intern(), value.toString()
                            )
                        } else {
                            executeCommand = Conversion.Companion.replaceMatch(
                                executeCommand!!,
                                (Expression.Companion.PRINT_TAG + Expression.Companion.BRACKET_LEFT_TAG + key + Expression.Companion.BRACKET_RIGHT_TAG)
                                    .intern(), key ?: ""
                            )
                        }
                    }
                }

                if (isCache) {
                    // 注入腳本緩存
                    cacheScript!!.put(cacheCommandName, executeCommand)
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        } finally {
            if (!isInnerCommand) {
                offsetPos++
            }
        }

        return executeCommand
    }

    companion object {
        /**
         * 
         // */
        private const val serialVersionUID = 1L

        // 腳本緩存
        private var cacheScript: MutableMap<Any?, Any?>? = null

        // 函數清單
        private var functions: ArrayMap? = null

        // 變數清單
        private var setEnvironmentList: MutableMap<Any?, Any?>? = null

        // 條件分支清單
        private var conditionEnvironmentList: ArrayMap? = null

        // 讀入連續資料
        private var reader: StringBuffer? = null

        fun initCommand() {
            if (cacheScript == null) {
                cacheScript = Collections.synchronizedMap<Any?, Any?>(HashMap<Any?, Any?>(1500))
                functions = ArrayMap(20)
                setEnvironmentList =
                    Collections.synchronizedMap<Any?, Any?>(HashMap<Any?, Any?>(20))
                conditionEnvironmentList = ArrayMap(30)
                reader = StringBuffer(3000)
            }
        }

        /**
         * 重啟腳本緩存
         * 
         // */
        fun resetCache() {
            if (cacheScript != null) {
                cacheScript!!.clear()
            }
        }

        /**
         * 截取第一次出現的指定標記
         * 
         * @param messages
         * @param startString
         * @param endString
         * @return
         // */
        fun getNameTag(
            messages: String, startString: String,
            endString: String
        ): String? {
            val results: MutableList<Any?> = getNameTags(messages, startString, endString)
            return if (results == null || results.size == 0)
                null
            else
                results.get(0) as String?
        }

        /**
         * 截取指定標記內容為list
         * 
         * @param messages
         * @param startString
         * @param endString
         * @return
         // */
        fun getNameTags(
            messages: String, startString: String,
            endString: String
        ): MutableList<Any?> {
            return getNameTags(
                messages.toCharArray(), startString
                    .toCharArray(), endString.toCharArray()
            )
        }

        /**
         * 截取指定標記內容為list
         * 
         * @param messages
         * @param startString
         * @param endString
         * @return
         // */
        fun getNameTags(
            messages: CharArray, startString: CharArray,
            endString: CharArray
        ): MutableList<Any?> {
            val dlength = messages.size
            val slength = startString.size
            val elength = endString.size
            val tagList: MutableList<Any?> = ArrayList<Any?>(10)
            var lookup = false
            var lookupStartIndex = 0
            var lookupEndIndex = 0
            var length: Int
            val sbr = StringBuffer(100)
            for (i in 0..<dlength) {
                val tag = messages[i]
                if (tag == startString[lookupStartIndex]) {
                    lookupStartIndex++
                }
                if (lookupStartIndex == slength) {
                    lookupStartIndex = 0
                    lookup = true
                }
                if (lookup) {
                    sbr.append(tag)
                }
                if (tag == endString[lookupEndIndex]) {
                    lookupEndIndex++
                }
                if (lookupEndIndex == elength) {
                    lookupEndIndex = 0
                    lookup = false
                    length = sbr.length
                    if (length > 0) {
                        tagList.add(sbr.substring(1, sbr.length - elength))
                        sbr.delete(0, length)
                    }
                }
            }
            return tagList
        }

        /**
         * 包含指定腳本內容
         * 
         * @param fileName
         * @return
         // */
        private fun includeFile(fileName: String?): MutableList<Any?> {
            var `in`: InputStream? = null
            var reader: BufferedReader? = null
            val result: MutableList<Any?> = ArrayList<Any?>(1000)
            try {
                `in` = Resources.Companion.openResource(fileName)
                reader = BufferedReader(
                    InputStreamReader(
                        `in`,
                        LSystem.encoding
                    )
                )
                var record: String? = null
                while ((reader.readLine().also { record = it }) != null) {
                    record = record!!.trim { it <= ' ' }
                    if (record.length > 0) {
                        if (!(record.startsWith(Expression.Companion.FLAG_L_TAG)
                                    || record.startsWith(Expression.Companion.FLAG_C_TAG) || record
                                .startsWith(Expression.Companion.FLAG_I_TAG))
                        ) {
                            result.add(record)
                        }
                    }
                }
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return result
        }

        /**
         * 過濾指定指令檔內容為list
         * 
         * @param src
         * @return
         // */
        fun commandSplit(src: String): MutableList<Any?> {
            val cmds: Array<String?>?
            var result = src.trim { it <= ' ' }
            result = result.replace("\r".toRegex(), "")
            result = Expression.Companion.FLAG + result
            result = result.replace("\t".toRegex(), Expression.Companion.FLAG)
            if (StringUtils.Companion.charCount(result, '=') == 1) {
                result = result.replace(" ".toRegex(), Expression.Companion.FLAG)
                result = result.replace(
                    "=".toRegex(),
                    (Expression.Companion.FLAG + "=" + Expression.Companion.FLAG).intern()
                )
            } else {
                result = result.replace(" ".toRegex(), Expression.Companion.FLAG)
                result = result.replace(
                    "<=".toRegex(),
                    (Expression.Companion.FLAG + "<=" + Expression.Companion.FLAG).intern()
                )
                result = result.replace(
                    ">=".toRegex(),
                    (Expression.Companion.FLAG + ">=" + Expression.Companion.FLAG).intern()
                )
                result = result.replace(
                    "==".toRegex(),
                    (Expression.Companion.FLAG + "==" + Expression.Companion.FLAG).intern()
                )
                result = result.replace(
                    "!=".toRegex(),
                    (Expression.Companion.FLAG + "!=" + Expression.Companion.FLAG).intern()
                )
                if (result.indexOf("<=") == -1) {
                    result = result.replace(
                        "<".toRegex(),
                        (Expression.Companion.FLAG + "<" + Expression.Companion.FLAG).intern()
                    )
                }
                if (result.indexOf(">=") == -1) {
                    result = result.replace(
                        ">".toRegex(),
                        (Expression.Companion.FLAG + ">" + Expression.Companion.FLAG).intern()
                    )
                }
            }
            result = result.replace(
                (Expression.Companion.FLAG + "{2,}").intern().toRegex(),
                Expression.Companion.FLAG
            )
            result = result.substring(1)
            cmds = result.split(Expression.Companion.FLAG.toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            return ArrayList<Any?>(Arrays.asList<String?>(*cmds))
        }
    }
}
