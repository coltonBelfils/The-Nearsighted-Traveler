package dataTypes

class EdgeModel(val e1: QuadrantModel, val e2: QuadrantModel) {
    override fun equals(other: Any?): Boolean {
        if(other !is EdgeModel) return false
        return when (e1) {
            other.e1 -> {
                e2 == other.e2
            }
            other.e2 -> {
                e2 == other.e1
            }
            else -> {
                false
            }
        }
    }

    override fun hashCode(): Int {
        return (e1.hashCode() * e2.hashCode()) * 31
    }

    override fun toString(): String {
        return "{$e1 - $e2}"
    }
}