package com.example.try_gameengine.avg

import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.URL
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
 * @email ceponline@yahoo.com.cn
 * @version 0.1.0
 // */
abstract class Resources {
    fun finalize() {
        destroy()
    }

    companion object {
        /**
         * 獲得當前系統的ClassLoader
         * 
         * @return
         // */
        var classLoader: ClassLoader? = null
            private set

        private val lock = Any()

        private val lazyResources: MutableMap<String?, Any?> = HashMap<String?, Any?>(
            LSystem.DEFAULT_MAX_CACHE_SIZE
        )

        init {
            try {
                // 在Android中Thread.currentThread()方式等於被廢|||……
                // classLoader = Thread.currentThread().getContextClassLoader();
                classLoader = Resources::class.java.getClassLoader()
            } catch (ex: Throwable) {
                classLoader = null
            }
        }

        val names: MutableIterator<String?>
            /**
             * 獲得資源名反覆運算器
             * 
             * @return
             // */
            get() {
                synchronized(lock) {
                    return lazyResources.keys.iterator()
                }
            }

        /**
         * 檢查指定資源名是否存在
         * 
         * @param resName
         * @return
         // */
        fun contains(resName: String?): Boolean {
            synchronized(lock) {
                return (lazyResources.get(resName) != null)
            }
        }

        /**
         * 刪除指定名稱的資源
         * 
         * @param resName
         // */
        fun remove(resName: String?) {
            synchronized(lock) {
                lazyResources.remove(resName)
            }
        }

        fun destroy() {
            lazyResources.clear()
        }

        /**
         * 獲得指定類的ClassLoader
         * 
         * @param clazz
         * @return
         // */
        fun getClassLoader(clazz: Class<*>): ClassLoader? {
            return clazz.getClassLoader()
        }

        /**
         * 打開一個指定的ClassLoader資源
         * 
         * @param resName
         * @param cl
         * @return
         * @throws IOException
         // */
        @Throws(IOException::class)
        fun openResource(resName: String?, c: ClassLoader): InputStream {
            val result = c.getResourceAsStream(resName)
            if (result == null) {
                throw IOException(
                    ("Exception to load resource [" + resName
                            + "] .")
                )
            }
            return result
        }

        /**
         * 打開當前類載入器下的資源檔
         * 
         * @param resName
         * @return
         * @throws IOException
         // */
        @Throws(IOException::class)
        fun openResource(resName: String?): InputStream {
            var `in`: BufferedInputStream? = null
            try {
                `in` = BufferedInputStream(
                    classLoader!!
                        .getResourceAsStream(resName)
                )
            } catch (e: Exception) {
                throw RuntimeException(resName + " not found!")
            }
            return `in`
        }

        /**
         * 載入資源檔
         * 
         * @param resName
         * @return
         // */
        fun getResource(resName: String?): ArrayByte? {
            var resName = resName
            if (resName == null) {
                return null
            }
            resName = if (resName.startsWith("/")) resName.substring(1) else resName
            var innerName: String? = resName
            val keyName = innerName!!.replace(" ".toRegex(), "").lowercase(Locale.getDefault())
            synchronized(lock) {
                if (lazyResources.size > LSystem.DEFAULT_MAX_CACHE_SIZE) {
                    lazyResources.clear()
                    LSystem.gc()
                }
                val data = lazyResources.get(keyName) as ByteArray?
                if (data != null) {
                    return ArrayByte(data)
                }
            }
            var `in`: InputStream? = null
            // 外部檔標誌
            val filePath = innerName!!.startsWith("$")
            if (filePath || isExists(resName)) {
                try {
                    innerName = innerName!!.substring(1, innerName!!.length)
                    `in` = BufferedInputStream(
                        FileInputStream(
                            File(
                                innerName
                            )
                        )
                    )
                } catch (ex: FileNotFoundException) {
                }
            } else {
                `in` = BufferedInputStream(
                    classLoader!!
                        .getResourceAsStream(innerName)
                )
            }
            var byteArray: ArrayByte? = ArrayByte()
            try {
                byteArray!!.write(`in`!!)
                `in`!!.close()
                byteArray.reset()
                lazyResources.put(keyName, byteArray.data)
            } catch (ex: IOException) {
                byteArray = null
            }
            if (byteArray == null) {
                throw RuntimeException(resName + " file not found !")
            }
            return byteArray
        }

        /**
         * 載入資源檔(無緩存)
         * 
         * @param resName
         * @return
         // */
        fun getNotCacheResource(resName: String?): ArrayByte? {
            var resName = resName
            if (resName == null) {
                return null
            }
            resName = if (resName.startsWith("/")) resName.substring(1) else resName
            var `in`: InputStream? = null
            // 外部檔標誌
            val filePath = resName.startsWith("$")
            if (filePath || isExists(resName)) {
                try {
                    resName = resName.substring(1, resName.length)
                    `in` = BufferedInputStream(
                        FileInputStream(
                            File(
                                resName
                            )
                        )
                    )
                } catch (ex: FileNotFoundException) {
                }
            } else {
                `in` = BufferedInputStream(
                    classLoader!!
                        .getResourceAsStream(resName)
                )
            }
            var byteArray: ArrayByte? = ArrayByte()
            try {
                byteArray!!.write(`in`!!)
                `in`!!.close()
                byteArray.reset()
            } catch (ex: IOException) {
                byteArray = null
            }
            if (byteArray == null) {
                throw RuntimeException(resName + " file not found !")
            }
            return byteArray
        }

        /**
         * 載入資源檔為InputStream格式
         * 
         * @param fileName
         * @return
         // */
        fun getResourceAsStream(fileName: String): InputStream? {
            if ((fileName.indexOf("file:") >= 0) || (fileName.indexOf(":/") > 0)) {
                try {
                    val url = URL(fileName)
                    return BufferedInputStream(url.openStream())
                } catch (e: Exception) {
                    return null
                }
            }
            return ByteArrayInputStream(getResource(fileName)!!.data)
        }

        /**
         * 載入資源檔為InputStream格式(無緩存)
         * 
         * @param fileName
         * @return
         // */
        fun getNotCacheResourceAsStream(fileName: String): InputStream? {
            if ((fileName.indexOf("file:") >= 0) || (fileName.indexOf(":/") > 0)) {
                try {
                    val url = URL(fileName)
                    return BufferedInputStream(url.openStream())
                } catch (e: Exception) {
                    return null
                }
            }
            return ByteArrayInputStream(getNotCacheResource(fileName)!!.data)
        }

        /**
         * 將InputStream轉為byte[]
         * 
         * @param is
         * @return
         // */
        fun getDataSource(`is`: InputStream?): ByteArray? {
            var `is` = `is`
            if (`is` == null) {
                return null
            }
            var byteArrayOutputStream: ByteArrayOutputStream? = ByteArrayOutputStream()
            var bytes = ByteArray(8192)
            try {
                var read: Int
                while ((`is`.read(bytes).also { read = it }) >= 0) {
                    byteArrayOutputStream!!.write(bytes, 0, read)
                }
                bytes = byteArrayOutputStream!!.toByteArray()
            } catch (e: IOException) {
                return null
            } finally {
                try {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.flush()
                        byteArrayOutputStream = null
                    }
                    if (`is` != null) {
                        `is`.close()
                        `is` = null
                    }
                } catch (e: IOException) {
                }
            }
            return bytes
        }

        fun getResource(clazz: Class<*>, resName: String): InputStream? {
            return clazz.getResourceAsStream(resName)
        }

        private fun isExists(fileName: String): Boolean {
            return File(fileName).exists()
        }

        /**
         * 通過url讀取網路檔流
         * 
         * @param uri
         * @return
         // */
        fun getHttpStream(uri: String?): ByteArray? {
            val url: URL?
            try {
                url = URL(uri)
            } catch (e: Exception) {
                return null
            }
            var `is`: InputStream? = null
            try {
                `is` = url.openStream()
            } catch (e: Exception) {
                return null
            }
            var os: ByteArrayOutputStream? = ByteArrayOutputStream()
            var arrayByte: ByteArray? = null
            try {
                arrayByte = ByteArray(4096)
                var read: Int
                while ((`is`.read(arrayByte).also { read = it }) >= 0) {
                    os!!.write(arrayByte, 0, read)
                }
                arrayByte = os!!.toByteArray()
            } catch (e: IOException) {
                return null
            } finally {
                try {
                    if (os != null) {
                        os.close()
                        os = null
                    }
                    if (`is` != null) {
                        `is`.close()
                        `is` = null
                    }
                } catch (e: IOException) {
                }
            }

            return arrayByte
        }
    }
}
