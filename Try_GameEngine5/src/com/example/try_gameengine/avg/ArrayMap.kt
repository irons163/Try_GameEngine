package com.example.try_gameengine.avg

import java.io.Externalizable
import java.io.IOException
import java.io.ObjectInput
import java.io.ObjectOutput
import java.util.LinkedHashMap

/**
 * Map with stable insertion order plus the original engine's index based helpers.
 */
class ArrayMap @JvmOverloads constructor(
    initialCapacity: Int = CollectionUtils.INITIAL_CAPACITY
) : LinkedHashMap<Any?, Any?>(initialCapacity.coerceAtLeast(1)), Externalizable, Cloneable {

    constructor(map: MutableMap<*, *>) : this((map.size / LOAD_FACTOR).toInt() + 1) {
        for (entry in map.entries) {
            put(entry.key, entry.value)
        }
    }

    fun iterator(): MutableIterator<MutableMap.MutableEntry<Any?, Any?>> {
        return entries.iterator()
    }

    fun indexOf(value: Any?): Int {
        var index = 0
        for (entry in entries) {
            if (entry.value == value) {
                return index
            }
            index++
        }
        return -1
    }

    fun get(index: Int): Any? {
        return getEntry(index).value
    }

    fun getKey(index: Int): Any? {
        return getEntry(index).key
    }

    fun getEntry(index: Int): MutableMap.MutableEntry<Any?, Any?> {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index:$index, Size:$size")
        }
        return entries.elementAt(index)
    }

    fun set(index: Int, value: Any?) {
        getEntry(index).setValue(value)
    }

    fun remove(index: Int): Any? {
        val key = getKey(index)
        return remove(key)
    }

    fun toArray(): Array<Any?> {
        return values.toTypedArray()
    }

    public override fun clone(): Any {
        return ArrayMap(this)
    }

    @Throws(IOException::class)
    override fun writeExternal(out: ObjectOutput) {
        out.writeInt(size)
        for (entry in entries) {
            out.writeObject(entry.key)
            out.writeObject(entry.value)
        }
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    override fun readExternal(input: ObjectInput) {
        clear()
        val size = input.readInt()
        for (i in 0..<size) {
            put(input.readObject(), input.readObject())
        }
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val LOAD_FACTOR = 0.75f
    }
}
