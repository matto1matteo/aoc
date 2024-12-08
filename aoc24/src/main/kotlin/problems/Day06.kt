package com.github.matto1matteo.problems

class Day06(override val fileName: String) : Problem {
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

        val grid = Grid(gridMap, guard)

        while (grid.canMove()) {
            grid.moveGuard()
        }

        return grid.visited.toString()
    }

    override fun secondSolution(): String {
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

        val grid = Grid(gridMap, guard)
        while(grid.canMove()) {
            grid.moveGuard2()
        }


        return grid.newObstacles.toString()
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

class Grid(private val positions: List<List<Cell>>, private val guard: Guard) {
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
        val nextPosition = guard.position + guard.direction.vec
        val cell = at(guard.position)
        if (at(nextPosition).status == CellStatus.OCCUPIED) {
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
        val nextPosition = guard.position + guard.direction.vec
        val cell = at(guard.position)
        if (at(guard.position + guard.direction.rotate().vec).status == CellStatus.VISITED) {
            newObstacles++
        }
        if (at(nextPosition).status == CellStatus.OCCUPIED) {
            cell.visit(guard.direction)
            guard.rotate()
            newObstacles--
            return
        }

        if (cell.status != CellStatus.VISITED) {
            cell.visit(guard.direction)
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
    fun rotate() {
        direction = direction.rotate()
    }

    fun move() {
        position += direction.vec
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
