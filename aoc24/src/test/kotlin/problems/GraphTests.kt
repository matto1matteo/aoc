package problems

import com.github.matto1matteo.math.graph.Node
import com.github.matto1matteo.math.vectors.Vec2
import com.github.matto1matteo.problems.Day06
import com.github.matto1matteo.problems.addObstacles
import com.github.matto1matteo.problems.removeObstacles
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.BufferedReader
import java.io.StringReader

class GridTests: ShouldSpec({
    context("day 6 input") {
        val input = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""
        val reader = BufferedReader(StringReader(input))
        val dailyProblem = Day06("")
        val grid = dailyProblem.createGrid(reader)
        val graph = grid.createGraph()

        should("graph should have an edge on 1,6 -> 2->3") {
            val edges = graph.map.filter { it.key == Node(Vec2(1, 6)) }
            edges.values.first() shouldContain Node(Vec2(2, 3))
        }

    }
})

class GraphTests : ShouldSpec({
    context("day 6 input") {
        val input = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""
        val reader = BufferedReader(StringReader(input))
        val dailyProblem = Day06("")
        val grid = dailyProblem.createGrid(reader)
        val graph = grid.createGraph()
        val node = Node(Vec2(3, 6))
        val target = Node(Vec2(8, 7))

        should("have edges after addObstacles(Node(3, 6))") {
            graph.addObstacles(node)
            val filtered = graph.map.filter { it.key == node }

            filtered.values.first() shouldHaveSize 2
            filtered.values.first() shouldContain Node(Vec2(4, 0))
            graph.map[target] shouldNotBe emptyList<Node<Vec2>>()
            graph.map[target]!! shouldContain node
        }

        should("have edges removed after removeObstacle(node)") {
            val l = graph.map[node]
            graph.removeObstacles(node)
            graph.map[node] shouldBe null
        }

        should("have cycle in node") {
            graph.removeObstacles(node)
            graph.addObstacles(node)
            graph.hasCycle(node) shouldBe true
        }
    }
})