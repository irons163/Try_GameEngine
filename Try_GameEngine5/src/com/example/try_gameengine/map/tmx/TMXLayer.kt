package com.example.try_gameengine.map.tmx

import android.graphics.Canvas
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.zip.GZIPInputStream

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
class TMXLayer(// 基础地图
    private val map: TMXTiledMap, element: Element
) {
    // 图层索引
    var index: Int = 0

    // XML文件名
    var name: String?

    // 图层数据
    var data: Array<Array<IntArray?>?>

    // 图层宽度(TMX格式的宽，即实际宽/瓦片大小)
    var width: Int

    // 图层高度(TMX格式的高，即实际高/瓦片大小)
    var height: Int

    // 图层属性
    var props: TMXProperty? = null

    /**
     * 根据TMX地图描述创建一个新层
     * 
     * @param map
     * @param element
     * @throws RuntimeException
     // */
    init {
        name = element.getAttribute("name")
        width = element.getAttribute("width").toInt()
        height = element.getAttribute("height").toInt()
        data = Array<Array<IntArray?>?>(width) { Array<IntArray?>(height) { IntArray(3) } }

        // 获得当前图层属性
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

        val dataNode = element.getElementsByTagName("data").item(
            0
        ) as Element
        val encoding = dataNode.getAttribute("encoding")
        val compression = dataNode.getAttribute("compression")

        // 进行base64的压缩解码
        if ("base64" == encoding && "gzip" == compression) {
            try {
                val cdata = dataNode.getFirstChild()
                val enc = cdata.getNodeValue().trim { it <= ' ' }.toCharArray()
                val dec = decodeBase64(enc)
                val `is` = GZIPInputStream(
                    ByteArrayInputStream(dec)
                )

                for (y in 0..<height) {
                    for (x in 0..<width) {
                        var tileId = 0
                        tileId = tileId or `is`.read()
                        tileId = tileId or (`is`.read() shl 8)
                        tileId = tileId or (`is`.read() shl 16)
                        tileId = tileId or (`is`.read() shl 24)

                        if (tileId == 0) {
                            data[x]!![y]!![0] = -1
                            data[x]!![y]!![1] = 0
                            data[x]!![y]!![2] = 0
                        } else {
                            val set = map.findTileSet(tileId)

                            if (set != null) {
                                data[x]!![y]!![0] = set.index
                                data[x]!![y]!![1] = tileId - set.firstGID
                            }
                            data[x]!![y]!![2] = tileId
                        }
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("Unable to decode base64 !")
            }
        } else {
            throw RuntimeException(
                ("Unsupport tiled map type " + encoding
                        + "," + compression + " only gzip base64 Support !")
            )
        }
    }

    /**
     * 获得指定位置的瓦片ID
     * 
     * @param x
     * @param y
     * @return
     // */
    fun getTileID(x: Int, y: Int): Int {
        return data[x]!![y]!![2]
    }

    /**
     * 设置指定位置的瓦片ID
     * 
     * @param x
     * @param y
     * @param tile
     // */
    fun setTileID(x: Int, y: Int, tile: Int) {
        if (tile == 0) {
            data[x]!![y]!![0] = -1
            data[x]!![y]!![1] = 0
            data[x]!![y]!![2] = 0
        } else {
            val set = map.findTileSet(tile)

            data[x]!![y]!![0] = set.index
            data[x]!![y]!![1] = tile - set.firstGID
            data[x]!![y]!![2] = tile
        }
    }

    /**
     * 渲染当前层画面到LGraphics之上
     * 
     * @param g
     * @param x
     * @param y
     * @param sx
     * @param sy
     * @param width
     * @param ty
     * @param isLine
     * @param mapTileWidth
     * @param mapTileHeight
     // */
    fun draw(
        g: Canvas, x: Int, y: Int, sx: Int, sy: Int, width: Int,
        ty: Int, isLine: Boolean, mapTileWidth: Int, mapTileHeight: Int
    ) {
        val tileCount = map.getTileSetCount()

        var nx: Int
        var ny: Int
        var sheetX: Int
        var sheetY: Int
        var tileOffsetY: Int

        for (tileset in 0..<tileCount) {
            var set: TMXTileSet? = null

            for (tx in 0..<width) {
                nx = sx + tx
                ny = sy + ty

                if ((nx < 0) || (ny < 0)) {
                    continue
                }
                if ((nx >= this.width) || (ny >= this.height)) {
                    continue
                }

                if (data[nx]!![ny]!![0] == tileset) {
                    if (set == null) {
                        set = map.getTileSet(tileset)
                    }

                    sheetX = set!!.getTileX(data[nx]!![ny]!![1])
                    sheetY = set!!.getTileY(data[nx]!![ny]!![1])

                    tileOffsetY = set!!.tileHeight - mapTileHeight

                    set!!.tiles!!.draw(
                        g, (x + (tx * mapTileWidth)).toFloat(), (y
                                + (ty * mapTileHeight) - tileOffsetY).toFloat(), sheetX,
                        sheetY
                    )
                }
            }

            if (isLine) {
                if (set != null) {
                    set = null
                }
                map.rendered(ty, ty + sy, index)
            }
        }
    }

    /**
     * 进行base64格式解码以获得相关的图层数据
     * 
     * @param data
     * @return
     // */
    private fun decodeBase64(data: CharArray): ByteArray {
        var temp = data.size
        for (ix in data.indices) {
            if ((data[ix].code > 255) || base64[data[ix].code] < 0) {
                --temp
            }
        }

        var len = (temp / 4) * 3
        if ((temp % 4) == 3) {
            len += 2
        }
        if ((temp % 4) == 2) {
            len += 1
        }
        val out = ByteArray(len)

        var shift = 0
        var accum = 0
        var index = 0

        for (ix in data.indices) {
            val value = (if (data[ix].code > 255) -1 else base64[data[ix].code]).toInt()

            if (value >= 0) {
                accum = accum shl 6
                shift += 6
                accum = accum or value
                if (shift >= 8) {
                    shift -= 8
                    out[index++] = ((accum shr shift) and 0xff).toByte()
                }
            }
        }

        if (index != out.size) {
            throw RuntimeException("index != " + out.size)
        }

        return out
    }

    companion object {
        private val base64 = ByteArray(256)

        init {
            for (i in 0..255) {
                base64[i] = -1
            }
            run {
                var i = 'A'.code
                while (i <= 'Z'.code) {
                    Companion.base64[i] = (i - 'A'.code).toByte()
                    i++
                }
            }
            run {
                var i = 'a'.code
                while (i <= 'z'.code) {
                    Companion.base64[i] = (26 + i - 'a'.code).toByte()
                    i++
                }
            }
            var i = '0'.code
            while (i <= '9'.code) {
                base64[i] = (52 + i - '0'.code).toByte()
                i++
            }
            base64['+'.code] = 62
            base64['/'.code] = 63
        }
    }
}
