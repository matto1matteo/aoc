package math.graph

import com.github.matto1matteo.math.graph.AdiacenceList
import com.github.matto1matteo.math.graph.Graph
import com.github.matto1matteo.math.graph.Node
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NodeTest {
    private val sample = Node(1, "Mele")

    @Test
    fun testVisited() {
        val expected = false
        assertEquals(expected, sample.visited)
    }
}

class NodeTestKotest : AnnotationSpec() {
    val sample = Node(1, "mele")
    @AnnotationSpec.Test
    fun testVisited() {
        val expected = false

        sample.visited shouldBe expected
    }
}

class GraphPathTest : ShouldSpec({
    context("Empty graph") {
        val graph : Graph<Int, String> = AdiacenceList(kotlin.collections.HashMap())
        should("have no path") {
            graph.hasPath("mele", "pere") shouldBe false
        }
    }

    context("Sparse graph") {
        var nodes : HashMap<Node<Int, String>, MutableList<Node<Int, String>>> = HashMap(
            mapOf(
                Node(1, "1") to mutableListOf(),
                Node(2, "2") to mutableListOf(Node(1, "1"))
            )
        )
        val graph : Graph<Int, String> = AdiacenceList(nodes = nodes)

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
        var nodes : HashMap<Node<Int, String>, MutableList<Node<Int, String>>> = HashMap(
            mapOf(
                Node(1, "1") to mutableListOf(Node(2, "2")),
                Node(2, "2") to mutableListOf(Node(1, "1"))
            )
        )
        val graph : Graph<Int, String> = AdiacenceList(nodes = nodes)

        should("show cycle") {
            graph.hasCycle("1") shouldBe true
            graph.hasCycle("2") shouldBe true
        }
    }
})

class GraphWithRoot : ShouldSpec({
    context("Can construct with root") {
        should("have no problem") {
            var nodes : HashMap<Node<Int, String>, MutableList<Node<Int, String>>> = HashMap(
                mapOf(
                    Node(1, "1") to mutableListOf(Node(2, "2")),
                    Node(2, "2") to mutableListOf(Node(1, "1"))
                )
            )
            val g = AdiacenceList(nodes)
            val last = nodes.keys.last()
            val graph = AdiacenceList.withRoot(g, last)

            shouldNotBeNull { graph }
        }
    }
})
