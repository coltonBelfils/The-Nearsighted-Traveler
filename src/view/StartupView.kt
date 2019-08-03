package view

import javafx.scene.Parent
import javafx.scene.input.KeyCode
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import tornadofx.View
import tornadofx.borderpane
import tornadofx.center
import tornadofx.pane
import utils.mazeGenerator.MazeGenerator
import kotlin.system.measureTimeMillis

const val TITLE_BAR_HEIGHT = 74

class StartupView : View() {
    lateinit var mapView: MapView
    override val root: Parent = borderpane {
        shortcut("w") {
            mapView.controller.inputNorth()
        }
        shortcut("UP") {
            mapView.controller.inputNorth()
        }
        shortcut("d") {
            mapView.controller.inputEast()
        }
        shortcut("RIGHT") {
            mapView.controller.inputEast()
        }
        shortcut("s") {
            mapView.controller.inputSouth()
        }
        shortcut("DOWN") {
            mapView.controller.inputSouth()
        }
        shortcut("a") {
            mapView.controller.inputWest()
        }
        shortcut("LEFT") {
            mapView.controller.inputWest()
        }
        center {
            pane {
                mapView = MapView(heightProperty(), widthProperty())
                add(mapView)
            }
        }
    }
}