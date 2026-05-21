package com.example.try_gameengine.framework

import java.util.Stack

class CompositeIterator(iterator: MutableIterator<*>?) : MutableIterator<Any?> {
    var stack: Stack<MutableIterator<*>?> = Stack<MutableIterator<*>?>()

    init {
        stack.push(iterator)
    }

    override fun hasNext(): Boolean {
        // TODO Auto-generated method stub
        if (stack.empty()) {
            return false
        } else {
            val iterator = stack.peek() as MutableIterator<*>
            if (!iterator.hasNext()) {
                stack.pop()
                return hasNext()
            } else {
                return true
            }
        }
    }

    override fun next(): Any? {
        // TODO Auto-generated method stub
        if (hasNext()) {
            val iterator = stack.peek() as MutableIterator<*>
            val layer = iterator.next() as ALayer
            stack.push(layer.createIterator())
            return layer
        } else {
            return null
        }
    }

    override fun remove() {
        // TODO Auto-generated method stub
        throw UnsupportedOperationException()
    }
}
