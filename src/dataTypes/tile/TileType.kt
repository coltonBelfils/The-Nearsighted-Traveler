package dataTypes.tile

enum class TileType(type: Int) {
    WALL(0),
    ROOM(1),
    NONEXISTENT(-1)
}