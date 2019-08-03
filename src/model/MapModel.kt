package model

import dataTypes.QuadrantModel
import dataTypes.tile.TileType
import dataTypes.tile.TileType.*
import utils.mazeGenerator.MazeGenerator

class MapModel(size: Int) {
    private val mazeGenerator = MazeGenerator(size)
    private val map = mazeGenerator.generate()

    fun getQuadrantValue(quadrant: QuadrantModel): TileType {
        return if(quadrant.y in 0 until map.size && quadrant.x in 0 until map[quadrant.y].size) {
            map[quadrant.y][quadrant.x]
        } else {
            NONEXISTENT
        }
    }
}