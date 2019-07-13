package controler

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

class MapController(private val mapView: MapView) : Controller() {
    //private val model = MapModel()
    val elements: ObservableList<Tile> = FXCollections.observableArrayList()
    private val mapHeight: ReadOnlyDoubleProperty
        get() = mapView.mapHeight
    private val mapWidth: ReadOnlyDoubleProperty
        get() = mapView.mapWidth
    private var xPos = 2 //Fake Model
    private var yPos = 2 //Fake Model
    private var animation: Timeline = Timeline()

    init {
        updateElements(xPos, yPos)
    }

    fun updateElements(xPos: Int, yPos: Int) { //Fake Model
        elements.clear()
        for (y in 0 until 5) {
            for (x in 0 until 5) {
                elements.add(
                    if (x == xPos || y == yPos) {
                        Tile(ROOM, SimpleIntegerProperty(x), SimpleIntegerProperty(y), mapHeight, mapWidth)
                    } else {
                        Tile(TileType.WALL, SimpleIntegerProperty(x), SimpleIntegerProperty(y), mapHeight, mapWidth)
                    }
                )
            }
        }
    }

    fun inputNorth() {
        val whenDone = EventHandler<ActionEvent> {
            yPos-- //Fake Model
            updateElements(xPos, yPos) //Fake Model
        }
        animateNorth(whenDone)
    }

    fun inputEast() {
        val whenDone = EventHandler<ActionEvent> {
            xPos++ //Fake Model
            updateElements(xPos, yPos) //Fake Model
        }
        animateEast(whenDone)
    }

    fun inputSouth() {
        val whenDone = EventHandler<ActionEvent> {
            yPos++ //Fake Model
            updateElements(xPos, yPos) //Fake Model
        }
        animateSouth(whenDone)
    }

    fun inputWest() {
        val whenDone = EventHandler<ActionEvent> {
            xPos-- //Fake Model
            updateElements(xPos, yPos) //Fake Model
        }
        animateWest(whenDone)
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
            keyframe(.5.seconds) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.y - mapHeight.divide(3).value
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
            keyframe(.5.seconds) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.x + mapWidth.divide(3).value
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
            keyframe(.5.seconds) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.y + mapHeight.divide(3).value
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
            keyframe(.5.seconds) {
                elements.forEach { tile ->
                    val newPos = tile.rectangle.x - mapWidth.divide(3).value
                    keyvalue(tile.rectangle.xProperty(), newPos)
                }
            }
            onFinished = whenDone
        }
        animate(timeline)
    }
}
