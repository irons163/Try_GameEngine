package com.example.try_gameengine.avg

/**
 * 
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @emaileponline@yahoo.com.cn
 * @version 0.1
 // */
class CommandIterator(items: Array<Any?>) : MutableIterator<Any?> {
    private val items: Array<Any?>?

    private var index = 0

    private var size = 0

    init {
        this.items = items
        this.size = items.size
    }

    override fun hasNext(): Boolean {
        return index < size
    }

    override fun next(): Any? {
        return items!![index++]
    }

    override fun remove() {
    }
}
