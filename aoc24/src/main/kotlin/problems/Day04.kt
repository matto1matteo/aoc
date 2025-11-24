package com.github.matto1matteo.problems

import com.github.matto1matteo.math.vectors.Vec2


class Puzzle(private val nodes: List<List<Char>>) {
    private fun find(word: String, position: Vec2, direction: Vec2) : Boolean {
        if (word.isEmpty()) {
            return true
        }

        // Check bounds
        if (
            position.y == -1
            || position.x == -1
            || position.y >= nodes.size
            || position.x >= nodes[position.y].size
        )
        {
            return false
        }

        // check first char
        if (nodes[position.y][position.x] != word[0]) {
            return false
        }

        return find(word.substring(1), position + direction, direction)
    }

    fun visit() : Int {
        // V direzione cerco(MAS, direzione)
        var counter = 0
        for ((y, line) in nodes.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char == 'X') {
                    val pos = Vec2(x, y)
                    for (direction in Vec2.directions) {
                        if (find("MAS", pos + direction, direction)) {
                            counter++
                        }
                    }
                }
            }
        }
        return counter
    }

    private fun findCross(position: Vec2, diagonal: List<Vec2>) : Boolean {
        val sums = diagonal.map { it + position }
        if (
            sums[0].y  == -1
            || sums[0].x == -1
            || sums[0].y >= nodes.size
            || sums[0].x >= nodes[sums[0].y].size
            || sums[1].y  == -1
            || sums[1].x == -1
            || sums[1].y >= nodes.size
            || sums[1].x >= nodes[sums[1].y].size
        )
        {
            return false
        }
        return nodes[sums[0].y][sums[0].x] == 'M' && nodes[sums[1].y][sums[1].x] == 'S'
                || nodes[sums[0].y][sums[0].x] == 'S' && nodes[sums[1].y][sums[1].x] == 'M'
    }

    fun visit2() : Int {
        // V direzione cerco(MAS, direzione)
        var counter = 0
        for ((y, line) in nodes.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char == 'A') {
                    val pos = Vec2(x, y)
                    if (findCross(pos, Vec2.topL) && findCross(pos, Vec2.bottomL)) {
                        counter++
                    }
                }
            }
        }
        return counter
    }
}

class Day04(override var fileName: String) : Problem {
    override fun firstSolution(): String {
        val reader = readResource()
        val puzzleInput : MutableList<List<Char>> = mutableListOf()
        var i = 0
        var line = reader.readLine()
        while (line != null) {
            puzzleInput.add(line.toList())
            i++
            line = reader.readLine()
        }

        val puzzle = Puzzle(puzzleInput)

        return puzzle.visit().toString()
    }

    override fun secondSolution(): String {
        val reader = readResource()
        val puzzleInput : MutableList<List<Char>> = mutableListOf()
        var i = 0
        var line = reader.readLine()
        while (line != null) {
            puzzleInput.add(line.toList())
            i++
            line = reader.readLine()
        }

        val puzzle = Puzzle(puzzleInput)

        return puzzle.visit2().toString()
    }
}