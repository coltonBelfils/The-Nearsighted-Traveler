package utils.mazeGenerator

import dataTypes.CoordinateModel

class UnionFind(vertices: Set<CoordinateModel>) {
    private val connectedVertices: HashSet<HashSet<CoordinateModel>> = HashSet()
    init {
        vertices.forEach { vertex ->
            connectedVertices.add(HashSet<CoordinateModel>().apply {
                add(vertex)
            })
        }
    }

    fun find(vertex: CoordinateModel): HashSet<CoordinateModel> {
        for (set in connectedVertices) {
            if(set.contains(vertex)) {
                return set
            }
        }
        return HashSet()
    }

    fun union(vertex1: CoordinateModel, vertex2: CoordinateModel) {
        val set1 = find(vertex1)
        val set2 = find(vertex2)

        if(set1 != HashSet<CoordinateModel>() && set2 != HashSet<CoordinateModel>()) {
            val newSet = HashSet(set1)
            newSet.addAll(set2)
            connectedVertices.remove(set1)
            connectedVertices.remove(set2)
            connectedVertices.add(newSet)
        }
    }
}
