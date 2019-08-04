import com.sun.javafx.tk.Toolkit
import javafx.application.Application
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.*
import view.StartupView

fun main(args: Array<String>) {
    Application.launch(AppLaunch::class.java, *args)
}

class AppLaunch : App(StartupView::class) {
    override fun start(stage: Stage) {
        stage.minWidth = 800.0
        stage.minHeight = 800.0
        stage.x = Screen.getPrimary().bounds.maxX / 2
        stage.y = Screen.getPrimary().bounds.maxY / 2
        super.start(stage)
    }
}