package dataTypes.tile

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.Parent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import dataTypes.tile.TileType.*
import javafx.scene.shape.StrokeType

class Tile(tileType: TileType, var xIndex: SimpleIntegerProperty, var yIndex: SimpleIntegerProperty, private val mapHeight: ReadOnlyDoubleProperty, private val mapWidth: ReadOnlyDoubleProperty) : View() {
    var type: TileType = tileType
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
                mapWidth.divide(3).multiply(xIndex)
            )
            yProperty().bind(
                mapHeight.divide(3).multiply(yIndex)
            )
            fill = when (type) {
                WALL -> Color.BLACK
                ROOM -> Color.WHITE
                NONEXISTENT -> Color.RED
            }
            strokeProperty().bind(fillProperty())
            strokeType = StrokeType.INSIDE
            /*if(fill == Color.BLACK) {
                strokeProperty().bind(fillProperty())
            } else if(xIndex.value in 0..2 && yIndex.value in 0..2) {
                strokeProperty().bind(fillProperty())
            } else {
                stroke = Color.BLACK
            }*/
        }
    }
}