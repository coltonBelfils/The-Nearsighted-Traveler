package dataTypes

class QuadrantModel(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is QuadrantModel) return false
        if(other.x == x && other.y == y) return true
        return false
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}