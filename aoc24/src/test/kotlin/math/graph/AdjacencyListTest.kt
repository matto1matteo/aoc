package math.graph

import com.github.matto1matteo.math.graph.AdjacencyList
import com.github.matto1matteo.math.graph.Graph
import com.github.matto1matteo.math.graph.Node
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class GraphPathTest : ShouldSpec({
    context("Empty graph") {
        val graph : Graph<String> = AdjacencyList(kotlin.collections.HashMap())
        should("have no path") {
            graph.hasPath("mele", "pere") shouldBe false
        }
    }

    context("Sparse graph") {
        var nodes : HashMap<Node<String>, MutableList<Node<String>>> = HashMap(
            mapOf(
                Node("1") to mutableListOf(),
                Node("2") to mutableListOf(Node("1"))
            )
        )
        val graph : Graph<String> = AdjacencyList(nodes)

        should("have a path from 2 to 1") {
            graph.hasPath("2", "1") shouldBe true
            graph.hasPath("1", "2") shouldBe false
        }

        should("have no cycles") {
            graph.hasCycle("1") shouldBe false
            graph.hasCycle("2") shouldBe false
        }
    }

    context("Graph with a cycle") {
        var nodes : HashMap<Node<String>, MutableList<Node<String>>> = HashMap(
            mapOf(
                Node("1") to mutableListOf(Node("2")),
                Node("2") to mutableListOf(Node("1"))
            )
        )
        val graph : Graph<String> = AdjacencyList(map = nodes)

        should("show cycle") {
            graph.hasCycle("1") shouldBe true
            graph.hasCycle("2") shouldBe true
        }
    }
})

