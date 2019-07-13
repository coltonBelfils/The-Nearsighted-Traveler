package dataTypes.tile

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.Parent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import dataTypes.tile.TileType.*

class Tile(type: TileType, var xIndex: SimpleIntegerProperty, var yIndex: SimpleIntegerProperty, private val mapHeight: ReadOnlyDoubleProperty, private val mapWidth: ReadOnlyDoubleProperty) : View() {
    var type: TileType = WALL
    lateinit var rectangle: Rectangle
        private set
    override val root: Parent = pane {
        rectangle = rectangle {
            widthProperty().bind(
                mapWidth.divide(3)
            )
            heightProperty().bind(
                mapHeight.divide(3)

            )
            xProperty().bind(
                mapWidth.divide(3).multiply(xIndex - 1)
            )
            yProperty().bind(
                mapHeight.divide(3).multiply(yIndex - 1)
            )
            fill = when (type) {
                WALL -> Color.BLACK
                ROOM -> Color.WHITE
            }
            strokeProperty().bind(fillProperty())
        }
    }
}