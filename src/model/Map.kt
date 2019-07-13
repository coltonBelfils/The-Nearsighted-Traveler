package model

import dataTypes.tile.TileType.*

class Map(size: Int) {
    val grid = Array(size) {
        Array(size) {
            ROOM
        }
    }
}