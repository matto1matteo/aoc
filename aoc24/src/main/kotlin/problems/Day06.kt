package com.github.matto1matteo.problems

import com.github.matto1matteo.math.graph.AdjacencyList
import com.github.matto1matteo.math.graph.Node
import com.github.matto1matteo.math.vectors.Vec2
import java.io.BufferedReader

class Day06(override val fileName: String) : Problem {
    private var grid: Grid? = null

    override fun firstSolution(): String {
        val reader = readResource()
        var line : String?
        var lineIndex = 0
        var guard = Guard(Direction.DOWN, Vec2(0, 0))
        val gridMap = mutableListOf<MutableList<Cell>>()
        while (reader.readLine().also { line = it } != null) {
            val row = line!!.toCharArray()
            val gridRow = mutableListOf<Cell>()
            for ((index, cellChar) in row.withIndex()) {
                if (Direction.entries.map { it.d }.contains(cellChar)) {
                    guard = Guard(Direction.fromChar(cellChar), Vec2(index, lineIndex))
                    gridRow.add(Cell(CellStatus.FREE))
                    continue
                }

                gridRow.add(Cell(CellStatus.fromChar(cellChar)))
            }
            lineIndex++
            gridMap.add(gridRow)
        }

        grid = Grid(gridMap, guard)

        while (grid!!.canMove()) {
            grid!!.moveGuard()
        }

        return grid!!.visited.toString()
    }

    fun createGrid(reader: BufferedReader): Grid {
        var line : String?
        var lineIndex = 0
        var guard = Guard(Direction.DOWN, Vec2(0, 0))
        val gridMap = mutableListOf<MutableList<Cell>>()

        while (reader.readLine().also { line = it } != null) {
            val row = line!!.toCharArray()
            val gridRow = mutableListOf<Cell>()
            for ((index, cellChar) in row.withIndex()) {
                if (Direction.entries.map { it.d }.contains(cellChar)) {
                    guard = Guard
                        .fromDirectionChar(cellChar)
                        .withPosition(Vec2(index, lineIndex))
                    gridRow.add(Cell(CellStatus.FREE))
                    continue
                }

                gridRow.add(Cell(CellStatus.fromChar(cellChar)))
            }
            lineIndex++
            gridMap.add(gridRow)
        }

        return Grid(
            positions = gridMap,
            guard = guard
        )
    }
    override fun secondSolution(): String {
        val reader = readResource()
        val grid = createGrid(reader)
        val guard = grid.guard
        val startPosition = guard.position
        val graph = grid.createGraph()
        while(grid.canMove()) {
            val node = Node(guard.nextPosition())
            if (node.label != startPosition) {
                graph.addObstacles(node)
                if (graph.hasCycle(node)) {
                    grid.newObstacles++
                }
                graph.removeObstacles(node)
            }
            grid.moveGuard2()
        }

        return grid.newObstacles.toString()
    }


}

/**
 * `addObstacles` add `node` to the list of nodes with the obstacles the guard will meet and will also add it to the
 * appropriate adjacency list:
 * coming from below -> it's to the left
 * coming from above -> it's to the right
 * coming from its right -> it's above
 * coming from its left -> it's below
 */
fun AdjacencyList<Vec2>.addObstacles(node: Node<Vec2>) {
    // old obstacles new edges with new node
    Direction.entries.forEach { direction ->
        when(direction) {
            Direction.DOWN -> {
                val n = nodes
                    .filter { (label) -> label.x == (node.label.x - 1) && label.y > node.label.y }
                    .minByOrNull { it.label.y }
                map[n]?.add(node)
            }
            Direction.UP -> {
                val n = nodes
                    .filter { (label) -> label.x == (node.label.x + 1) && label.y < node.label.y}
                    .maxByOrNull { it.label.y }
                map[n]?.add(node)
            }
            Direction.LEFT -> {
                val n = nodes
                    .filter { (label) -> label.y == (node.label.y - 1) && label.x < node.label.x}
                    .maxByOrNull { it.label.x }
                map[n]?.add(node)
            }
            Direction.RIGHT -> {
                val n = nodes
                    .filter { (label) -> label.y == (node.label.y + 1) && label.x > node.label.x}
                    .minByOrNull { it.label.x }
                map[n]?.add(node)
            }
        }
    }

    // new obstacles edges
    val obstacles = Direction.entries.map { direction ->
        when(direction) {
            Direction.DOWN -> nodes
                .filter { it.label.y == (node.label.y + 1) && it.label.x > node.label.x }
                .minByOrNull { it.label.x }
            Direction.UP -> nodes
                .filter { it.label.y == (node.label.y) - 1 && it.label.x < node.label.x }
                .maxByOrNull { it.label.x }
            Direction.LEFT -> nodes
                .filter { it.label.x == (node.label.x + 1) && it.label.y < node.label.y }
                .maxByOrNull { it.label.y }
            Direction.RIGHT -> nodes
                .filter { it.label.x == (node.label.x - 1) && it.label.y > node.label.y }
                .minByOrNull { it.label.y }
        }
    }
    this.map[node] = obstacles.filterNotNull().toMutableList()
}

fun AdjacencyList<Vec2>.removeObstacles(node: Node<Vec2>) {
    map.remove(node)
    for ((_, v) in map) {
        v.remove(node)
    }
}

class Cell(var status: CellStatus, var direction: Direction? = null) {
    fun visit(direction: Direction? = null) {
        if (status == CellStatus.VISITED) {
            return
        }
        this.direction = direction
        status = CellStatus.VISITED
    }
}

class Grid(private val positions: List<List<Cell>>, val guard: Guard) {
    var visited = 1
    var newObstacles = 0

    fun canMove() : Boolean {
        val indexes = guard.position + guard.direction.vec
        return !(indexes.x < 0
                || indexes.y < 0
                || indexes.y >= positions.count()
                || indexes.x >= positions[indexes.y].count())
    }

    private fun at(x: Int, y: Int) : Cell {
        return positions[y][x]
    }

    private fun at(vec: Vec2) : Cell {
        return at(vec.x, vec.y)
    }

    fun moveGuard() {
        val cell = at(guard.position)
        if (at(guard.nextPosition()).status == CellStatus.OCCUPIED) {
            visited++
            guard.rotate()
            cell.visit(guard.direction)
            return
        }

        if (cell.status != CellStatus.VISITED) {
            cell.visit(guard.direction)
            visited++
        }
        guard.move()
    }

    fun moveGuard2() {
        val cell = at(guard.position)
        if (at(guard.nextPosition()).status == CellStatus.OCCUPIED) {
            visited++
            guard.rotate()
            cell.visit(guard.direction)
            return
        }

        if (cell.status != CellStatus.VISITED) {
            cell.visit(guard.direction)
            visited++
        }
        guard.move()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (line in positions) {
            for (cell in line) {
                if (cell.status == CellStatus.VISITED) {
                    builder.append(cell.direction!!.d)
                } else {
                    builder.append(cell.status.c)
                }
            }
            builder.append('\n')
        }
        return builder.toString()
    }

    fun createGraph(): AdjacencyList<Vec2> {
        val nodes = hashMapOf<Node<Vec2>, MutableList<Node<Vec2>>>()
        for ((i, line) in positions.withIndex()) {
            for ((j, cell) in line.withIndex()) {
                if (cell.status == CellStatus.OCCUPIED) {
                    val pair = addObstacles(Vec2(j, i))
                    nodes[pair.first] = pair.second
                }
            }
        }
        return AdjacencyList(nodes)
    }

    fun addObstacles(position: Vec2): Pair<Node<Vec2>, MutableList<Node<Vec2>>> {
        val pair = Pair(Node(position), mutableListOf<Node<Vec2>>())
        for (d in Direction.entries) {
            when (d) {
                Direction.UP -> {
                    val obstacle = obstacleUp(position)
                    if (obstacle != null) {
                        pair.second.add(obstacle)
                    }
                }
                Direction.DOWN -> {
                    val obstacle = obstacleDown(position)
                    if (obstacle != null) {
                        pair.second.add(obstacle)
                    }
                }
                Direction.LEFT -> {
                    val obstacle = obstacleLeft(position)
                    if (obstacle != null) {
                        pair.second.add(obstacle)
                    }
                }
                Direction.RIGHT -> {
                    val obstacle = obstacleRight(position)
                    if (obstacle != null) {
                        pair.second.add(obstacle)
                    }
                }
            }
        }
        return  pair
    }
    private fun obstacleUp(position: Vec2): Node<Vec2>? {
        val y = position.y - 1
        if (y < 0) {
            return  null
        }

        for (j in position.x downTo 0) {
            if (at(j, y).status == CellStatus.OCCUPIED) {
                return Node(Vec2(j, position.y-1))
            }
        }
        return null
    }
    private fun obstacleDown(position: Vec2): Node<Vec2>? {
        if (position.y + 1 >= positions.size) {
            return null
        }

        for (j in position.x until positions.size) {
            if (at(j, position.y+1).status == CellStatus.OCCUPIED) {
                return Node(Vec2(j, position.y+1))
            }
        }

        return null
    }
    private fun obstacleLeft(position: Vec2): Node<Vec2>? {
        val x = position.x - 1
        if (x < 0) {
            return null
        }

        for (j in position.y until positions.first().size) {
            if (at(x, j).status == CellStatus.OCCUPIED) {
                return Node(Vec2(position.x-1, j))
            }
        }

        return null
    }
    private fun obstacleRight(position: Vec2): Node<Vec2>? {
        if (position.x + 1 >= positions.first().size) {
            return null
        }

        for (j in position.y downTo 0) {
            if (at(position.x+1, j).status == CellStatus.OCCUPIED) {
                return Node(Vec2(position.x+1, j))
            }
        }

        return null
    }
}

enum class CellStatus(val c: Char) {
    OCCUPIED('#'),
    VISITED('X'),
    FREE('.');

    companion object {
        fun fromChar(c: Char) : CellStatus {
            return when (c) {
                '#' -> OCCUPIED
                'X' -> VISITED
                '.' -> FREE
                else -> throw Exception("Invalid cell state")
            }
        }
    }
}

class Guard(var direction: Direction, var position: Vec2) {

    companion object {
        fun fromDirectionChar(c: Char): Guard {
            return Guard(Direction.fromChar(c), Vec2(0, 0))
        }
    }
    fun rotate() {
        direction = direction.rotate()
    }

    fun move() {
        position += direction.vec
    }

    fun withPosition(position: Vec2): Guard {
        this.position = position
        return this
    }

    fun nextPosition(): Vec2 {
        return position + direction.vec
    }
}

enum class Direction(val d: Char) {
    UP('^') {
        override fun rotate() = RIGHT
        override val vec: Vec2
            get() = Vec2(0, -1)
    },
    RIGHT('>') {
        override fun rotate() = DOWN
        override val vec: Vec2
            get() = Vec2(1, 0)
    },
    DOWN('v') {
        override fun rotate() = LEFT
        override val vec: Vec2
            get() = Vec2(0, 1)
    },
    LEFT('<') {
        override fun rotate() = UP
        override val vec: Vec2
            get() = Vec2(-1, 0)
    };

    abstract fun rotate() : Direction
    abstract val vec : Vec2

    companion object {
        fun fromChar(c: Char) = when (c) {
            '^' -> UP
            'v' -> DOWN
            '>' -> RIGHT
            '<' -> LEFT
            else -> throw Exception("Invalid guard direction")
        }
    }
}
