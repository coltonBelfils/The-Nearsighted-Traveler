package model

import dataTypes.CoordinateModel
import dataTypes.tile.TileType
import dataTypes.tile.TileType.*
import utils.mazeGenerator.MazeGenerator

class MapModel(size: Int) {
    private val mazeGenerator = MazeGenerator(size)
    private val map = mazeGenerator.generate()

    fun getCoordinateValue(coordinate: CoordinateModel): TileType {
        return if(coordinate.y in 0 until map.size && coordinate.x in 0 until map[coordinate.y].size) {
            map[coordinate.y][coordinate.x]
        } else {
            NONEXISTENT
        }
    }
}