package math.graph

import com.github.matto1matteo.math.graph.AdjacencyList
import com.github.matto1matteo.math.graph.Node
import com.github.matto1matteo.math.graph.iterators.AdjacencyListBreadth
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class AdjacencyListBreadthIteratorTest: ShouldSpec({
    context("empty graph") {
        val graph = AdjacencyList<Int, Int>(hashMapOf())
        val iterator = AdjacencyListBreadth(graph)

        should("have no next") {
            iterator.hasNext() shouldBe false
            val ex = shouldThrow<NoSuchElementException> {
                iterator.next()
            }

            ex.message shouldBe "No more nodes to iterate from the given root"
        }
    }

    context("graph with one element") {
        val graph = AdjacencyList<Int, Int>(hashMapOf(
            Node(1,1) to mutableListOf()
        ))
        val iterator = AdjacencyListBreadth(graph)

        should("have next") {
            iterator.hasNext() shouldBe true
            iterator.next() shouldBe Node(1, 1)
            iterator.hasNext() shouldBe false
        }
    }
})