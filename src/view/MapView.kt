package view

import controler.MapController
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import dataTypes.tile.Tile

class MapView(val mapHeight: ReadOnlyDoubleProperty, val mapWidth: ReadOnlyDoubleProperty) : View() {
    val controller: MapController = MapController(this)
    private val elements = FXCollections.observableArrayList<Tile>().apply {
        bind(controller.elements) {
            it
        }
    }

    override val root: Parent = pane {
        shortcut("w") {
            controller.inputNorth()
        }
        shortcut("d") {
            controller.inputEast()
        }
        shortcut("s") {
            controller.inputSouth()
        }
        shortcut("a") {
            controller.inputWest()
        }
        bindChildren(elements) {
            it.root
        }
    }
}