package com.example.try_gameengine.map.tmx

import android.graphics.Bitmap
import com.example.try_gameengine.avg.GraphicsUtils
import com.example.try_gameengine.avg.Resources
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

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
class TMXTileSet(map: TMXTiledMap, element: Element, loadImage: Boolean) {
    // 基础地图
    private val map: TMXTiledMap

    // 瓦片索引
    var index: Int = 0

    var name: String?

    var firstGID: Int

    var lastGID: Int = Int.Companion.MAX_VALUE

    var tileWidth: Int

    var tileHeight: Int

    var tiles: SpriteSheet? = null

    var tilesAcross: Int = 0

    var tilesDown: Int = 0

    private val props = HashMap<Int?, TMXProperty?>()

    var tileSpacing: Int = 0
        protected set

    var tileMargin: Int = 0
        protected set

    init {
        var element = element
        this.map = map
        name = element.getAttribute("name")
        firstGID = element.getAttribute("firstgid").toInt()
        val source = element.getAttribute("source")

        if ((source != null) && (source != "")) {
            try {
                val `in`: InputStream? = Resources.Companion.openResource(
                    (map.getTilesLocation()
                            + "/" + source)
                )
                val builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                val doc = builder.parse(`in`)
                val docElement = doc.getDocumentElement()
                element = docElement
            } catch (e: Exception) {
                throw RuntimeException(
                    (this.map.tilesLocation + "/"
                            + source)
                )
            }
        }
        val tileWidthString = element.getAttribute("tilewidth")
        val tileHeightString = element.getAttribute("tileheight")
        if (tileWidthString.length == 0 || tileHeightString.length == 0) {
            throw RuntimeException(
                "tileWidthString.length == 0 || tileHeightString.length == 0"
            )
        }
        tileWidth = tileWidthString.toInt()
        tileHeight = tileHeightString.toInt()

        val sv = element.getAttribute("spacing")
        if ((sv != null) && ("" != sv)) {
            tileSpacing = sv.toInt()
        }

        val mv = element.getAttribute("margin")
        if ((mv != null) && ("" != mv)) {
            tileMargin = mv.toInt()
        }

        val list = element.getElementsByTagName("image")
        val imageNode = list.item(0) as Element
        val fileName = imageNode.getAttribute("source")

        if (loadImage) {
//			LTexture image = new LTexture(map.getTilesLocation() + "/"
//					+ fileName);
            val image = GraphicsUtils.loadImage(
                (map.getTilesLocation() + "/"
                        + fileName), false
            )
            setTileSetImage(image)
        }

        val pElements = element.getElementsByTagName("tile")
        for (i in 0..<pElements.getLength()) {
            val tileElement = pElements.item(i) as Element

            var id = tileElement.getAttribute("id").toInt()
            id += firstGID
            val tileProps = TMXProperty()

            val propsElement = tileElement.getElementsByTagName(
                "properties"
            ).item(0) as Element
            val properties = propsElement.getElementsByTagName("property")
            for (p in 0..<properties.getLength()) {
                val propElement = properties.item(p) as Element

                val name = propElement.getAttribute("name")
                val value = propElement.getAttribute("value")

                tileProps.setProperty(name, value)
            }

            props.put(id, tileProps)
        }
    }

    fun setTileSetImage(image: Bitmap) {
        tiles = SpriteSheet(
            image, tileWidth, tileHeight, tileSpacing,
            tileMargin
        )
        tilesAcross = tiles!!.getHorizontalCount()
        tilesDown = tiles!!.getVerticalCount()

        if (tilesAcross <= 0) {
            tilesAcross = 1
        }
        if (tilesDown <= 0) {
            tilesDown = 1
        }

        lastGID = (tilesAcross * tilesDown) + firstGID - 1
    }

    fun getProperties(globalID: Int): TMXProperty? {
        return props.get(globalID)
    }

    fun getTileX(id: Int): Int {
        return id % tilesAcross
    }

    fun getTileY(id: Int): Int {
        return id / tilesAcross
    }

    fun setLimit(limit: Int) {
        lastGID = limit
    }

    fun contains(gid: Int): Boolean {
        return (gid >= firstGID) && (gid <= lastGID)
    }
}