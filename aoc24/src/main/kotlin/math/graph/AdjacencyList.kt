package com.github.matto1matteo.math.graph

import com.github.matto1matteo.math.graph.iterators.AdjacencyListBreadth
import com.github.matto1matteo.math.graph.strategies.VisitStrategy

class AdjacencyList<L>(val map: HashMap<Node<L>, MutableList<Node<L>>>): Graph<L> {

    override val nodes: List<Node<L>>
        get() = map.keys.toList()
    override val edges: List<Pair<Node<L>, Node<L>>>
        get() = map.flatMap {
            n -> n.value.map { Pair(n.key, it) }
        }

    fun getAdjacentNodes(node: Node<L>): List<Node<L>> {
        return map[node]?.toList() ?: listOf()
    }

    override fun hasPath(
        source: L,
        destination: L,
        strategy: VisitStrategy<L>,
        iterator: Iterator<Node<L>>
    ): Boolean {
        val node = map.keys.first { it.label == source }
        val destinationNode = map.keys.first { it.label == destination }

        visit(node)

        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next == destinationNode) return true
        }
        return false
    }

    override fun hasCycle(
        label: L,
        visitStrategy: VisitStrategy<L>,
        iterator: Iterator<Node<L>>
    ): Boolean {
        val toCheck = map
            .filter { t -> t.key.label == label }
            .values.firstOrNull()
            // ignore trivial loop
            ?.filter { t -> t.label != label }

        if (toCheck == null)
            return false

        return toCheck
            .any { n -> hasPath(n.label, label, visitStrategy, iterator) }
    }

    override fun iterator(): Iterator<Node<L>> {
        return AdjacencyListBreadth(this)
    }

    override fun visit(
        node: Node<L>,
        strategy: VisitStrategy<L>,
    ) {
        strategy.act(node)
    }

}
