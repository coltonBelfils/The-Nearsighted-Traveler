package view

import controler.MapController
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.collections.FXCollections
import javafx.scene.Parent
import tornadofx.*
import dataTypes.tile.Tile
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class MapView(val mapHeight: ReadOnlyDoubleProperty, val mapWidth: ReadOnlyDoubleProperty) : View() {
    val controller: MapController = MapController(this)
    private val elements = FXCollections.observableArrayList<Tile>().apply {
        bind(controller.elements) {
            it
        }
    }

    override val root: Parent = pane {
        bindChildren(elements) {
            it.root
        }
    }
}