package controler

import dataTypes.CoordinateModel
import javafx.animation.Animation
import javafx.animation.Timeline
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import tornadofx.*
import view.MapView
import dataTypes.tile.Tile
import dataTypes.tile.TileType
import dataTypes.tile.TileType.ROOM
import dataTypes.tile.TileType.WALL
import javafx.scene.paint.Color
import model.MapModel

class MapController(private val mapView: MapView) : Controller() {
    private val mapSize = 5
    private val model = MapModel(mapSize)
    val elements: ObservableList<Tile> = FXCollections.observableArrayList()
    private val mapHeight: ReadOnlyDoubleProperty
        get() = mapView.mapHeight
    private val mapWidth: ReadOnlyDoubleProperty
        get() = mapView.mapWidth
    private var pos = CoordinateModel(1, 1)
    private var animation: Timeline = Timeline()
    private var animationSpeed = .25.seconds

    init {
        updateElements()
    }

    fun updateElements() {
        elements.clear()
        for (y in -2..2) {
            for (x in -2..2) {
                val currentPos = CoordinateModel(pos.x + x, pos.y + y)
                elements.add(
                    Tile(model.getCoordinateValue(currentPos), SimpleIntegerProperty(x + 1), SimpleIntegerProperty(y + 1), mapHeight, mapWidth).apply {
                        if(currentPos.x == 1 && currentPos.y == 1) {
                            rectangle.fill = Color.BLUE
                        } else if(currentPos.x == (mapSize * 2) - 1 && currentPos.y == (mapSize * 2) - 1) {
                            rectangle.fill = Color.ORANGE
                        }
                    }
                )
            }
        }
    }

    fun inputNorth() {
        val newPos = CoordinateModel(pos.x, pos.y - 1)
        if(model.getCoordinateValue(newPos) == ROOM) {
            val whenDone = EventHandler<ActionEvent> {
                pos = newPos
                updateElements()
            }
            animateNorth(whenDone)
        }
    }

    fun inputEast() {
        val newPos = CoordinateModel(pos.x + 1, pos.y)
        if(model.getCoordinateValue(newPos) == ROOM) {
            val whenDone = EventHandler<ActionEvent> {
                pos = newPos
                updateElements()
            }
            animateEast(whenDone)
        }
    }

    fun inputSouth() {
        val newPos = CoordinateModel(pos.x, pos.y + 1)
        if(model.getCoordinateValue(newPos) == ROOM) {
            val whenDone = EventHandler<ActionEvent> {
                pos = newPos
                updateElements()
            }
            animateSouth(whenDone)
        }
    }

    fun inputWest() {
        val newPos = CoordinateModel(pos.x - 1, pos.y)
        if(model.getCoordinateValue(newPos) == ROOM) {
            val whenDone = EventHandler<ActionEvent> {
                pos = newPos
                updateElements()
            }
            animateWest(whenDone)
        }
    }

    fun animate(timeline: Timeline) {
        if (animation.status == Animation.Status.STOPPED) {
            animation = timeline
            animation.play()
        }
    }

    fun animateNorth(whenDone: EventHandler<ActionEvent>) {
        elements.forEach { tile ->
            tile.rectangle.yProperty().unbind()
        }
        val timeline = Timeline()
        timeline.apply {
            keyframe(animationSpeed) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.y + mapHeight.divide(3).value
                    keyvalue(tile.rectangle.yProperty(), newPos)
                }
            }
            onFinished = whenDone
        }
        animate(timeline)
    }

    fun animateEast(whenDone: EventHandler<ActionEvent>) {
        elements.forEach { tile ->
            tile.rectangle.xProperty().unbind()
        }
        val timeline = Timeline()
        timeline.apply {
            keyframe(animationSpeed) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.x - mapWidth.divide(3).value
                    keyvalue(tile.rectangle.xProperty(), newPos)
                }
            }
            onFinished = whenDone
        }
        animate(timeline)
    }

    fun animateSouth(whenDone: EventHandler<ActionEvent>) {
        elements.forEach { tile ->
            tile.rectangle.yProperty().unbind()
        }
        val timeline = Timeline()
        timeline.apply {
            keyframe(animationSpeed) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.y - mapHeight.divide(3).value
                    keyvalue(tile.rectangle.yProperty(), newPos)
                }
            }
            onFinished = whenDone
        }
        animate(timeline)
    }

    fun animateWest(whenDone: EventHandler<ActionEvent>) {
        elements.forEach { tile ->
            tile.rectangle.xProperty().unbind()
        }
        val timeline = Timeline()
        timeline.apply {
            keyframe(animationSpeed) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.x + mapWidth.divide(3).value
                    keyvalue(tile.rectangle.xProperty(), newPos)
                }
            }
            onFinished = whenDone
        }
        animate(timeline)
    }
}
