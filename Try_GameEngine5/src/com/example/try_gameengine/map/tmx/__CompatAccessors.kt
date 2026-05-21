@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.map.tmx

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.example.try_gameengine.avg.GraphicsUtils
import com.example.try_gameengine.avg.Resources
import com.example.try_gameengine.framework.Config
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import org.xml.sax.EntityResolver
import org.xml.sax.InputSource
import org.xml.sax.SAXException

internal fun SpriteSheet.getHorizontalCount() = this.horizontalCount
internal fun SpriteSheet.getMargin() = this.margin
internal fun SpriteSheet.getSpacing() = this.spacing
internal fun SpriteSheet.getTextures() = this.textures
internal fun SpriteSheet.getVerticalCount() = this.verticalCount
internal fun SpriteSheet.setMargin(value: Int) { this.margin = value }
internal fun SpriteSheet.setSpacing(value: Int) { this.spacing = value }
internal fun TMXTile.getHeight() = this.height
internal fun TMXTile.getImage() = this.image
internal fun TMXTile.getIndex() = this.index
internal fun TMXTile.getName() = this.name
internal fun TMXTile.getProps() = this.props
internal fun TMXTile.getType() = this.type
internal fun TMXTile.getWidth() = this.width
internal fun TMXTile.getX() = this.x
internal fun TMXTile.getY() = this.y
internal fun TMXTile.setHeight(value: Int) { this.height = value }
internal fun TMXTile.setImage(value: String?) { this.image = value }
internal fun TMXTile.setIndex(value: Int) { this.index = value }
internal fun TMXTile.setName(value: String?) { this.name = value }
internal fun TMXTile.setProps(value: TMXProperty?) { this.props = value }
internal fun TMXTile.setType(value: String?) { this.type = value }
internal fun TMXTile.setWidth(value: Int) { this.width = value }
internal fun TMXTile.setX(value: Int) { this.x = value }
internal fun TMXTile.setY(value: Int) { this.y = value }
internal fun TMXTileGroup.getHeight() = this.height
internal fun TMXTileGroup.getIndex() = this.index
internal fun TMXTileGroup.getName() = this.name
internal fun TMXTileGroup.getObjects() = this.objects
internal fun TMXTileGroup.getProps() = this.props
internal fun TMXTileGroup.getWidth() = this.width
internal fun TMXTileGroup.setHeight(value: Int) { this.height = value }
internal fun TMXTileGroup.setIndex(value: Int) { this.index = value }
internal fun TMXTileGroup.setName(value: String?) { this.name = value }
internal fun TMXTileGroup.setObjects(value: ArrayList<TMXTile?>) { this.objects = value }
internal fun TMXTileGroup.setProps(value: TMXProperty?) { this.props = value }
internal fun TMXTileGroup.setWidth(value: Int) { this.width = value }
internal fun TMXTileSet.getFirstGID() = this.firstGID
internal fun TMXTileSet.getIndex() = this.index
internal fun TMXTileSet.getLastGID() = this.lastGID
internal fun TMXTileSet.getName() = this.name
internal fun TMXTileSet.getTileHeight() = this.tileHeight
internal fun TMXTileSet.getTileWidth() = this.tileWidth
internal fun TMXTileSet.getTiles() = this.tiles
internal fun TMXTileSet.getTilesAcross() = this.tilesAcross
internal fun TMXTileSet.getTilesDown() = this.tilesDown
internal fun TMXTileSet.setFirstGID(value: Int) { this.firstGID = value }
internal fun TMXTileSet.setIndex(value: Int) { this.index = value }
internal fun TMXTileSet.setLastGID(value: Int) { this.lastGID = value }
internal fun TMXTileSet.setName(value: String?) { this.name = value }
internal fun TMXTileSet.setTileHeight(value: Int) { this.tileHeight = value }
internal fun TMXTileSet.setTileWidth(value: Int) { this.tileWidth = value }
internal fun TMXTileSet.setTiles(value: SpriteSheet?) { this.tiles = value }
internal fun TMXTileSet.setTilesAcross(value: Int) { this.tilesAcross = value }
internal fun TMXTileSet.setTilesDown(value: Int) { this.tilesDown = value }
internal fun TMXTiledMap.getLayerCount() = this.layerCount
internal fun TMXTiledMap.getObjectGroupCount() = this.objectGroupCount
internal fun TMXTiledMap.getScreenHeight() = this.screenHeight
internal fun TMXTiledMap.getScreenWidth() = this.screenWidth
internal fun TMXTiledMap.getTileSetCount() = this.tileSetCount
internal fun TMXTiledMap.getTilesLocation() = this.tilesLocation
internal fun TMXTiledMap.setHeight(value: Int) { this.height = value }
internal fun TMXTiledMap.setTileHeight(value: Int) { this.tileHeight = value }
internal fun TMXTiledMap.setTileWidth(value: Int) { this.tileWidth = value }
internal fun TMXTiledMap.setTilesLocation(value: String?) { this.tilesLocation = value }
internal fun TMXTiledMap.setWidth(value: Int) { this.width = value }
