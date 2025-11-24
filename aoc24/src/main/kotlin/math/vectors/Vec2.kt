package com.github.matto1matteo.math.vectors

class Vec2(val x: Int, val y: Int) {
    companion object {
        val directions = listOf(
            Vec2(0, -1),
            Vec2(1, -1),
            Vec2(1, 0),
            Vec2(1, 1),
            Vec2(0, 1),
            Vec2(-1, 1),
            Vec2(-1, 0),
            Vec2(-1, -1),
        )

        val topL = listOf(
            Vec2(-1, -1),
            Vec2(1, 1)
        )
        val bottomL = listOf(
            Vec2(-1, 1),
            Vec2(1, -1)
        )
    }

    override fun toString(): String {
        return "Vec2(x: $x, y: $y)"
    }

    operator fun plus(vec: Vec2) : Vec2 {
        return Vec2(this.x + vec.x, this.y + vec.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vec2) {
            return false
        }
        return other.x == this.x && other.y == this.y
    }

    override fun hashCode(): Int {
        return (x.hashCode() + y.hashCode()).hashCode()
    }
}
