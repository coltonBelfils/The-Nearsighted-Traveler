package utils.mazeGenerator

import dataTypes.EdgeModel
import dataTypes.QuadrantModel
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
    private val allVertices: HashSet<QuadrantModel> = HashSet()
    private val vertices = Array(size * size) {
        QuadrantModel(it / size, it % size)
    }
    private val unionFind: UnionFind
    var count = true
    init {
        allVertices.addAll(vertices)
        unionFind = UnionFind(allVertices)
        vertices.forEach {
            var up: QuadrantModel? = null
            var right: QuadrantModel? = null
            var down: QuadrantModel? = null
            var left: QuadrantModel? = null
            if (it.y > 0) {
                up = QuadrantModel(it.x, it.y - 1)
            }
            if(it.x < size - 1) {
                right = QuadrantModel(it.x + 1, it.y)
            }
            if(it.y < size - 1) {
                down = QuadrantModel(it.x, it.y + 1)
            }
            if(it.x > 0) {
                left = QuadrantModel(it.x - 1, it.y)
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
            val downLink = QuadrantModel(it.x, it.y + 1)
            val downEdge = EdgeModel(it, downLink)
            val rightLink = QuadrantModel(it.x + 1, it.y)
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
                    val vertex = QuadrantModel(mazeX, mazeY)
                    val xPlusOne = QuadrantModel(mazeX + 1, mazeY)
                    val yPlusOne = QuadrantModel(mazeX, mazeY + 1)
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

    private fun roomToTile(room: QuadrantModel): QuadrantModel {
        val xPos = if(room.x in 0 until size) {
            (room.x * 2) + 1
        } else {
            return QuadrantModel(-1, -1)
        }

        val yPos = if(room.y in 0 until size) {
            (room.y * 2) + 1
        } else {
            return QuadrantModel(-1, -1)
        }
        return QuadrantModel(xPos, yPos)
    }

    /*
    fun tileToRoom(tile: QuadrantModel): QuadrantModel {
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