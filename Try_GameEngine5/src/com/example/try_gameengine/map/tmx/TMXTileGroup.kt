package com.example.try_gameengine.map.tmx

import org.w3c.dom.Element

/**
 * 
 * Copyright 2008 - 2011
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
class TMXTileGroup(element: Element) {
    var index: Int = 0

    var name: String?

    var objects: ArrayList<TMXTile?>

    var width: Int

    var height: Int

    var props: TMXProperty? = null

    init {
        name = element.getAttribute("name")
        width = element.getAttribute("width").toInt()
        height = element.getAttribute("height").toInt()
        objects = ArrayList<TMXTile?>()

        val propsElement = element.getElementsByTagName(
            "properties"
        ).item(0) as Element?
        if (propsElement != null) {
            val properties = propsElement.getElementsByTagName("property")
            if (properties != null) {
                props = TMXProperty()
                for (p in 0..<properties.getLength()) {
                    val propElement = properties.item(p) as Element

                    val name = propElement.getAttribute("name")
                    val value = propElement.getAttribute("value")
                    props!!.setProperty(name, value)
                }
            }
        }

        val objectNodes = element.getElementsByTagName("object")
        for (i in 0..<objectNodes.getLength()) {
            val objElement = objectNodes.item(i) as Element
            val `object` = TMXTile(objElement)
            `object`.index = i
            objects.add(`object`)
        }
    }
}
