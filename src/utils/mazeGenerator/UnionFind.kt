package utils.mazeGenerator

import dataTypes.QuadrantModel

class UnionFind(vertices: Set<QuadrantModel>) {
    private val connectedVertices: HashSet<HashSet<QuadrantModel>> = HashSet()
    init {
        vertices.forEach { vertex ->
            connectedVertices.add(HashSet<QuadrantModel>().apply {
                add(vertex)
            })
        }
    }

    fun find(vertex: QuadrantModel): HashSet<QuadrantModel> {
        for (set in connectedVertices) {
            if(set.contains(vertex)) {
                return set
            }
        }
        return HashSet()
    }

    fun union(vertex1: QuadrantModel, vertex2: QuadrantModel) {
        val set1 = find(vertex1)
        val set2 = find(vertex2)

        if(set1 != HashSet<QuadrantModel>() && set2 != HashSet<QuadrantModel>()) {
            val newSet = HashSet(set1)
            newSet.addAll(set2)
            connectedVertices.remove(set1)
            connectedVertices.remove(set2)
            connectedVertices.add(newSet)
        }
    }
}
