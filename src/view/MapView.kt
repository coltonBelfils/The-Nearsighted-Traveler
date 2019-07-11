package view

import controler.MapController
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.layout.Pane
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import tornadofx.*
import view.tile.Tile
import view.tile.TileType.*
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

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