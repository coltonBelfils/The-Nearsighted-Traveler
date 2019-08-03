package utils.mazeGenerator

import dataTypes.EdgeModel
import dataTypes.CoordinateModel
import dataTypes.tile.TileType
import dataTypes.tile.TileType.*

class MazeGenerator(private val size: Int) {
    private val map = Array((size * 2) + 1) {
        Array((size * 2) + 1) {
            WALL
        }
    }
    private val allEdges: HashSet<EdgeModel> = HashSet()
    private val allEdgesInMaze: HashSet<EdgeModel> = HashSet()
    private val allVertices: HashSet<CoordinateModel> = HashSet()
    private val vertices = Array(size * size) {
        CoordinateModel(it / size, it % size)
    }
    private val unionFind: UnionFind
    var count = true
    init {
        allVertices.addAll(vertices)
        unionFind = UnionFind(allVertices)
        vertices.forEach {
            var up: CoordinateModel? = null
            var right: CoordinateModel? = null
            var down: CoordinateModel? = null
            var left: CoordinateModel? = null
            if (it.y > 0) {
                up = CoordinateModel(it.x, it.y - 1)
            }
            if(it.x < size - 1) {
                right = CoordinateModel(it.x + 1, it.y)
            }
            if(it.y < size - 1) {
                down = CoordinateModel(it.x, it.y + 1)
            }
            if(it.x > 0) {
                left = CoordinateModel(it.x - 1, it.y)
            }
            for(vertex in listOfNotNull(up, right, down, left)) {
                allEdges.add(EdgeModel(it, vertex))
            }
        }
    }

    fun generate(): Array<Array<TileType>> {
        var edges = allEdges.toMutableList().apply { shuffle() }
        for(edge in edges) {
            if(unionFind.find(edge.e1) != unionFind.find(edge.e2)) {
                unionFind.union(edge.e1, edge.e2)
                allEdgesInMaze.add(edge)
            }
        }
        allEdgesInMaze.forEach {
            println(it)
        }
        formatArray()
        val str = StringBuilder()
        for(y in map.indices) {
            for(x in map[y].indices) {
                if(map[y][x] == WALL) {
                    str.append("#")
                } else {
                    str.append(" ")
                }
            }
            str.append("\n")
        }
        println(str)
        return map
    }

    private fun formatArray() {
        vertices.forEach {
            val tilePos = roomToTile(it)
            map[tilePos.y][tilePos.x] = ROOM
            val downLink = CoordinateModel(it.x, it.y + 1)
            val downEdge = EdgeModel(it, downLink)
            val rightLink = CoordinateModel(it.x + 1, it.y)
            val rightEdge = EdgeModel(it, rightLink)
            if(allEdgesInMaze.contains(downEdge)) {
                map[tilePos.y + 1][tilePos.x] = ROOM
            }
            if(allEdgesInMaze.contains(rightEdge)) {
                map[tilePos.y][tilePos.x + 1] = ROOM
            }
        }

        /*for(y in 0 until (size * 2) + 1) {
            for(x in 0 until (size * 2) + 1) {
                if(y == 0 || x == 0 || y % (size * 2) == 0 || x % (size * 2) == 0) {
                    map[y][x] = WALL
                } else if(y % 2 == 0 && x % 2 == 0) {
                    val mazeX = (x - 1) / 2
                    val mazeY = (y - 1) / 2
                    val vertex = CoordinateModel(mazeX, mazeY)
                    val xPlusOne = CoordinateModel(mazeX + 1, mazeY)
                    val yPlusOne = CoordinateModel(mazeX, mazeY + 1)
                    map[y][x] = ROOM
                    map[xPlusOne.y][xPlusOne.x] = if(allEdgesInMaze.contains(EdgeModel(vertex, xPlusOne))) {
                        ROOM
                    } else {
                        WALL
                    }
                    map[yPlusOne.y][yPlusOne.x] = if(allEdgesInMaze.contains(EdgeModel(vertex, yPlusOne))) {
                        ROOM
                    } else {
                        WALL
                    }
                }
            }
        }*/
    }

    private fun roomToTile(room: CoordinateModel): CoordinateModel {
        val xPos = if(room.x in 0 until size) {
            (room.x * 2) + 1
        } else {
            return CoordinateModel(-1, -1)
        }

        val yPos = if(room.y in 0 until size) {
            (room.y * 2) + 1
        } else {
            return CoordinateModel(-1, -1)
        }
        return CoordinateModel(xPos, yPos)
    }

    /*
    fun tileToRoom(tile: CoordinateModel): CoordinateModel {
        val xPos = when {
            tile.x % 2 == 0 ->  {
                -1
            }
            tile.x in 0 until ((tile.x - 1) / 2) -> {
                -1
            }
            else -> -1
        }
    }

     */
}