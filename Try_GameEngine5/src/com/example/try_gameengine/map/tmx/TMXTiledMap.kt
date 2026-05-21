package com.example.try_gameengine.map.tmx

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.try_gameengine.avg.Resources
import com.example.try_gameengine.framework.Config
import org.w3c.dom.Element
import org.xml.sax.EntityResolver
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.ByteArrayInputStream
import java.io.IOException
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
class TMXTiledMap {
    var width: Int = 0

    var height: Int = 0

    var tileWidth: Int = 0

    var tileHeight: Int = 0

    private var screenRect: Rect? = null

    var tilesLocation: String? = null

    protected var props: TMXProperty? = null

    protected var tileSets: ArrayList<TMXTileSet?> = ArrayList<TMXTileSet?>()

    protected var layers: ArrayList<TMXLayer?> = ArrayList<TMXLayer?>()

    protected var objectGroups: ArrayList<TMXTileGroup?> = ArrayList<TMXTileGroup?>()

    private var loadTileSets = true

    var screenWidth: Int = 0
        private set
    var screenHeight: Int = 0
        private set

    @JvmOverloads
    constructor(fileName: String, loadTileSets: Boolean = true) {
        var fileName = fileName
        this.loadTileSets = loadTileSets
        fileName = fileName.replace('\\', '/')
        val res = fileName.substring(0, fileName.lastIndexOf("/"))
        try {
            this.load(Resources.Companion.openResource(fileName), res)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    constructor(fileName: String?, tileSetsLocation: String?) {
        try {
            load(Resources.Companion.openResource(fileName), tileSetsLocation)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    constructor(`in`: InputStream?) {
        load(`in`, "")
    }

    constructor(`in`: InputStream?, tileSetsLocation: String?) {
        load(`in`, tileSetsLocation)
    }

    fun getLayerIndex(name: String?): Int {
        for (i in layers.indices) {
            val layer = layers.get(i) as TMXLayer

            if (layer.name == name) {
                return i
            }
        }

        return -1
    }

    fun getTileImage(x: Int, y: Int, layerIndex: Int): Bitmap? {
        val layer = layers.get(layerIndex) as TMXLayer

        val tileSetIndex = layer.data[x]!![y]!![0]
        if ((tileSetIndex >= 0) && (tileSetIndex < tileSets.size)) {
            val tileSet = tileSets.get(tileSetIndex) as TMXTileSet

            val sheetX = tileSet.getTileX(layer.data[x]!![y]!![1])
            val sheetY = tileSet.getTileY(layer.data[x]!![y]!![1])

            return tileSet.tiles!!.getSubImage(sheetX, sheetY)
        }

        return null
    }

    fun getTileId(x: Int, y: Int, layerIndex: Int): Int {
        val layer = layers.get(layerIndex) as TMXLayer
        return layer.getTileID(x, y)
    }

    fun setTileId(x: Int, y: Int, layerIndex: Int, tileid: Int) {
        val layer = layers.get(layerIndex) as TMXLayer
        layer.setTileID(x, y, tileid)
    }

    fun getMapProperty(propertyName: String?, def: String?): String? {
        if (props == null) return def
        return props!!.getProperty(propertyName, def)
    }

    fun getLayerProperty(
        layerIndex: Int, propertyName: String?,
        def: String?
    ): String? {
        val layer = layers.get(layerIndex)
        val props = layer?.props ?: return def
        return props.getProperty(propertyName, def)
    }

    fun getTileProperty(tileID: Int, propertyName: String?, def: String?): String? {
        if (tileID == 0) {
            return def
        }

        val set = findTileSet(tileID)

        val props = set.getProperties(tileID)
        if (props == null) {
            return def
        }
        return props.getProperty(propertyName, def)
    }

    fun draw(g: Canvas?, tx: Int, ty: Int) {
        draw(g, 0, 0, tx, ty)
    }

    fun draw(g: Canvas?, x: Int, y: Int, tx: Int, ty: Int) {
        draw(g, x, y, tx, ty, this.screenWidth, this.screenHeight, false)
    }

    fun draw(g: Canvas?, x: Int, y: Int, layer: Int) {
        draw(g, x, y, 0, 0, this.width, this.height, layer, false)
    }

    fun draw(
        g: Canvas?, x: Int, y: Int, sx: Int, sy: Int, width: Int,
        height: Int, l: Int, lineByLine: Boolean
    ) {
        g ?: return
        val layer = layers.get(l) as TMXLayer
        for (ty in 0..<height) {
            layer.draw(
                g, x, y, sx, sy, width, ty, lineByLine, tileWidth,
                tileHeight
            )
        }
    }

    @JvmOverloads
    fun draw(
        g: Canvas?, x: Int, y: Int, sx: Int, sy: Int, width: Int,
        height: Int, lineByLine: Boolean = false
    ) {
        g ?: return
        for (ty in 0..<height) {
            for (i in layers.indices) {
                val layer = layers.get(i) as TMXLayer
                layer.draw(
                    g, x, y, sx, sy, width, ty, lineByLine, tileWidth,
                    tileHeight
                )
            }
        }
    }

    val layerCount: Int
        get() = layers.size

    @Throws(RuntimeException::class)
    private fun load(`in`: InputStream?, tileSetsLocation: String?) {
        //		screenRect = LSystem.screenRect;

        tilesLocation = tileSetsLocation

        try {
            val factory = DocumentBuilderFactory
                .newInstance()
            factory.setValidating(false)
            val builder = factory.newDocumentBuilder()
            builder.setEntityResolver(object : EntityResolver {
                @Throws(SAXException::class, IOException::class)
                override fun resolveEntity(
                    publicId: String?,
                    systemId: String?
                ): InputSource {
                    return InputSource(
                        ByteArrayInputStream(ByteArray(0))
                    )
                }
            })

            val doc = builder.parse(`in`)
            val docElement = doc.getDocumentElement()

            val orient = docElement.getAttribute("orientation")
            if (orient != "orthogonal") {
                throw RuntimeException(
                    "Only orthogonal maps supported, found " + orient
                )
            }

            width = docElement.getAttribute("width").toInt()
            height = docElement.getAttribute("height").toInt()
            tileWidth = docElement.getAttribute("tilewidth").toInt()
            tileHeight = docElement.getAttribute("tileheight").toInt()

            val propsElement = docElement.getElementsByTagName(
                "properties"
            ).item(0) as Element?
            if (propsElement != null) {
                val properties = propsElement
                    .getElementsByTagName("property")
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

            if (loadTileSets) {
                var tileSet: TMXTileSet? = null
                var lastSet: TMXTileSet? = null

                val setNodes = docElement.getElementsByTagName("tileset")
                for (i in 0..<setNodes.getLength()) {
                    val current = setNodes.item(i) as Element

                    tileSet = TMXTileSet(this, current, true)
                    tileSet.index = i

                    if (lastSet != null) {
                        lastSet.setLimit(tileSet.firstGID - 1)
                    }
                    lastSet = tileSet

                    tileSets.add(tileSet)
                }
            }

            val layerNodes = docElement.getElementsByTagName("layer")
            for (i in 0..<layerNodes.getLength()) {
                val current = layerNodes.item(i) as Element
                val layer = TMXLayer(this, current)
                layer.index = i

                layers.add(layer)
            }

            val objectGroupNodes = docElement
                .getElementsByTagName("objectgroup")

            for (i in 0..<objectGroupNodes.getLength()) {
                val current = objectGroupNodes.item(i) as Element
                val objectGroup = TMXTileGroup(current)
                objectGroup.index = i

                objectGroups.add(objectGroup)
            }

            //			defWidth = screenRect.getWidth() / tileWidth;
//			defHeight = screenRect.getHeight() / tileHeight;
            this.screenWidth = (Config.currentScreenWidth / tileWidth).toInt()
            this.screenHeight = (Config.currentScreenHeight / tileHeight).toInt()
        } catch (ex: Exception) {
            throw RuntimeException("Failed to parse map", ex)
        }
    }

    val tileSetCount: Int
        get() = tileSets.size

    fun getTileSet(index: Int): TMXTileSet? {
        return tileSets.get(index)
    }

    fun getTileSetByGID(gid: Int): TMXTileSet? {
        for (i in tileSets.indices) {
            val set = tileSets.get(i) as TMXTileSet
            if (set.contains(gid)) {
                return set
            }
        }

        throw RuntimeException("No tileset found for gid: $gid")
    }

    fun findTileSet(gid: Int): TMXTileSet {
        for (i in tileSets.indices) {
            val set = tileSets.get(i) as TMXTileSet

            if (set.contains(gid)) {
                return set
            }
        }

        throw RuntimeException("No tile set found for gid: $gid")
    }

    fun rendered(visualY: Int, mapY: Int, layer: Int) {
    }

    val objectGroupCount: Int
        get() = objectGroups.size

    fun getObjectCount(groupID: Int): Int {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            return grp.objects.size
        }
        return -1
    }

    fun getObjectName(groupID: Int, objectID: Int): String? {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.name
            }
        }
        return null
    }

    fun getObjectType(groupID: Int, objectID: Int): String? {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.type
            }
        }
        return null
    }

    fun getObjectX(groupID: Int, objectID: Int): Int {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.x
            }
        }
        return -1
    }

    fun getObjectY(groupID: Int, objectID: Int): Int {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.y
            }
        }
        return -1
    }

    fun getObjectWidth(groupID: Int, objectID: Int): Int {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.width
            }
        }
        return -1
    }

    fun getObjectHeight(groupID: Int, objectID: Int): Int {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID) as TMXTile
                return `object`.height
            }
        }
        return -1
    }

    fun getObjectImage(groupID: Int, objectID: Int): String? {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID)

                if (`object` == null) {
                    return null
                }

                return `object`.image
            }
        }

        return null
    }

    fun getObjectProperty(
        groupID: Int, objectID: Int,
        propertyName: String?, def: String?
    ): String? {
        if (groupID >= 0 && groupID < objectGroups.size) {
            val grp = objectGroups.get(groupID) as TMXTileGroup
            if (objectID >= 0 && objectID < grp.objects.size) {
                val `object` = grp.objects.get(objectID)

                if (`object` == null) {
                    return def
                }
                if (`object`.props == null) {
                    return def
                }

                return `object`.props!!.getProperty(propertyName, def)
            }
        }
        return def
    }
}
