package com.example.try_gameengine.avg

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UTFDataFormatException
import kotlin.Boolean
import kotlin.Byte
import kotlin.ByteArray
import kotlin.IndexOutOfBoundsException
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.Throws
import kotlin.code
import kotlin.math.min

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
class ArrayByte @JvmOverloads constructor(var data: ByteArray = ByteArray(0)) {
    private var position = 0

    var byteOrder: Int = 0

    constructor(length: Int) : this(ByteArray(length))

    init {
        reset()
    }

    fun reset() {
        position = 0
        byteOrder = BIG_ENDIAN
    }

    fun length(): Int {
        return data.size
    }

    fun setLength(length: Int) {
        if (length != data.size) {
            val oldData = data
            data = ByteArray(length)
            System.arraycopy(
                oldData, 0, data, 0, min(
                    oldData.size,
                    length
                )
            )
            if (position > length) {
                position = length
            }
        }
    }

    fun position(): Int {
        return position
    }

    @Throws(IndexOutOfBoundsException::class)
    fun setPosition(position: Int) {
        if (position < 0 || position > data.size) {
            throw IndexOutOfBoundsException()
        }

        this.position = position
    }

    fun truncate() {
        setLength(position)
    }

    fun available(): Int {
        return length() - position()
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun checkAvailable(length: Int) {
        if (available() < length) {
            throw IndexOutOfBoundsException()
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readByte(): Byte {
        checkAvailable(1)
        return data[position++]
    }

    @JvmOverloads
    @Throws(IndexOutOfBoundsException::class)
    fun read(buffer: ByteArray, offset: Int = 0, length: Int = buffer.size): Int {
        if (length == 0) {
            return 0
        }
        checkAvailable(length)
        System.arraycopy(data, position, buffer, offset, length)
        position += length
        return length
    }

    @Throws(IOException::class)
    fun read(out: OutputStream) {
        out.write(data, position, data.size - position)
        position = data.size
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readBoolean(): Boolean {
        return (readByte().toInt() != 0)
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readShort(): Short {
        checkAvailable(2)
        if (byteOrder == LITTLE_ENDIAN) {
            return ((data[position++].toInt() and 0xff) or ((data[position++].toInt() and 0xff) shl 8)).toShort()
        } else {
            return (((data[position++].toInt() and 0xff) shl 8) or (data[position++].toInt() and 0xff)).toShort()
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readInt(): Int {
        checkAvailable(4)
        if (byteOrder == LITTLE_ENDIAN) {
            return ((data[position++].toInt() and 0xff) or ((data[position++].toInt() and 0xff) shl 8)
                    or ((data[position++].toInt() and 0xff) shl 16)
                    or ((data[position++].toInt() and 0xff) shl 24))
        } else {
            return (((data[position++].toInt() and 0xff) shl 24)
                    or ((data[position++].toInt() and 0xff) shl 16)
                    or ((data[position++].toInt() and 0xff) shl 8)
                    or (data[position++].toInt() and 0xff))
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readLong(): Long {
        checkAvailable(8)
        if (byteOrder == LITTLE_ENDIAN) {
            return ((readInt().toLong() and 0xffffffffL)
                    or ((readInt().toLong() and 0xffffffffL) shl 32L.toInt()))
        } else {
            return (((readInt().toLong() and 0xffffffffL) shl 32L.toInt())
                    or (readInt().toLong() and 0xffffffffL))
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readInt())
    }

    @Throws(IndexOutOfBoundsException::class)
    fun readDouble(): Double {
        return java.lang.Double.longBitsToDouble(readLong())
    }

    @Throws(IndexOutOfBoundsException::class, UTFDataFormatException::class)
    fun readUTF(): String {
        checkAvailable(2)
        val utfLength = readShort().toInt() and 0xffff
        checkAvailable(utfLength)

        val goalPosition = position() + utfLength

        val string = StringBuffer(utfLength)
        while (position() < goalPosition) {
            val a = readByte().toInt() and 0xff
            if ((a and 0x80) == 0) {
                string.append(a.toChar())
            } else {
                val b = readByte().toInt() and 0xff
                if ((b and 0xc0) != 0x80) {
                    throw UTFDataFormatException()
                }

                if ((a and 0xe0) == 0xc0) {
                    val ch = (((a and 0x1f) shl 6) or (b and 0x3f)).toChar()
                    string.append(ch)
                } else if ((a and 0xf0) == 0xe0) {
                    val c = readByte().toInt() and 0xff
                    if ((c and 0xc0) != 0x80) {
                        throw UTFDataFormatException()
                    }
                    val ch =
                        (((a and 0x0f) shl 12) or ((b and 0x3f) shl 6) or (c and 0x3f)).toChar()
                    string.append(ch)
                } else {
                    throw UTFDataFormatException()
                }
            }
        }
        return string.toString()
    }

    private fun ensureCapacity(dataSize: Int) {
        if (position + dataSize > data.size) {
            setLength(position + dataSize)
        }
    }

    fun writeByte(v: Int) {
        ensureCapacity(1)
        data[position++] = v.toByte()
    }

    @JvmOverloads
    fun write(buffer: ByteArray, offset: Int = 0, length: Int = buffer.size) {
        if (length == 0) {
            return
        }
        ensureCapacity(length)
        System.arraycopy(buffer, offset, data, position, length)
        position += length
    }

    @Throws(IOException::class)
    fun write(`in`: InputStream) {
        val buffer = ByteArray(8192)
        while (true) {
            val bytesRead = `in`.read(buffer)
            if (bytesRead == -1) {
                return
            }
            write(buffer, 0, bytesRead)
        }
    }

    fun writeBoolean(v: Boolean) {
        writeByte(if (v) -1 else 0)
    }

    fun writeShort(v: Int) {
        ensureCapacity(2)
        if (byteOrder == LITTLE_ENDIAN) {
            data[position++] = (v and 0xff).toByte()
            data[position++] = ((v shr 8) and 0xff).toByte()
        } else {
            data[position++] = ((v shr 8) and 0xff).toByte()
            data[position++] = (v and 0xff).toByte()
        }
    }

    fun writeInt(v: Int) {
        ensureCapacity(4)
        if (byteOrder == LITTLE_ENDIAN) {
            data[position++] = (v and 0xff).toByte()
            data[position++] = ((v shr 8) and 0xff).toByte()
            data[position++] = ((v shr 16) and 0xff).toByte()
            data[position++] = (v ushr 24).toByte()
        } else {
            data[position++] = (v ushr 24).toByte()
            data[position++] = ((v shr 16) and 0xff).toByte()
            data[position++] = ((v shr 8) and 0xff).toByte()
            data[position++] = (v and 0xff).toByte()
        }
    }

    fun writeLong(v: Long) {
        ensureCapacity(8)
        if (byteOrder == LITTLE_ENDIAN) {
            writeInt((v and 0xffffffffL).toInt())
            writeInt((v ushr 32).toInt())
        } else {
            writeInt((v ushr 32).toInt())
            writeInt((v and 0xffffffffL).toInt())
        }
    }

    fun writeFloat(v: kotlin.Float) {
        writeInt(java.lang.Float.floatToIntBits(v))
    }

    fun writeDouble(v: kotlin.Double) {
        writeLong(java.lang.Double.doubleToLongBits(v))
    }

    @Throws(UTFDataFormatException::class)
    fun writeUTF(s: String) {
        var utfLength = 0
        for (i in 0..<s.length) {
            val ch = s.get(i)
            if (ch.code > 0 && ch.code < 0x80) {
                utfLength++
            } else if (ch.code == 0 || (ch.code >= 0x80 && ch.code < 0x800)) {
                utfLength += 2
            } else {
                utfLength += 3
            }
        }

        if (utfLength > 65535) {
            throw UTFDataFormatException()
        }

        ensureCapacity(2 + utfLength)
        writeShort(utfLength)

        for (i in 0..<s.length) {
            val ch = s.get(i).code
            if (ch > 0 && ch < 0x80) {
                writeByte(ch)
            } else if (ch == 0 || (ch >= 0x80 && ch < 0x800)) {
                writeByte(0xc0 or (0x1f and (ch shr 6)))
                writeByte(0x80 or (0x3f and ch))
            } else {
                writeByte(0xe0 or (0x0f and (ch shr 12)))
                writeByte(0x80 or (0x3f and (ch shr 6)))
                writeByte(0x80 or (0x3f and ch))
            }
        }
    }

    companion object {
        const val BIG_ENDIAN: Int = 0

        const val LITTLE_ENDIAN: Int = 1
    }
}
