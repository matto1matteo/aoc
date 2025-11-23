package com.github.matto1matteo.math.graph

import com.github.matto1matteo.math.graph.iterators.AdjacencyListBreadth
import com.github.matto1matteo.math.graph.strategies.VisitStrategy

class AdjacencyList<T, L>(val map: HashMap<Node<T, L>, MutableList<Node<T, L>>>): Graph<T, L> {

    override val nodes: List<Node<T, L>>
        get() = map.keys.toList()
    override val edges: List<Pair<Node<T, L>, Node<T, L>>>
        get() = map.flatMap {
            n -> n.value.map { Pair(n.key, it) }
        }

    fun getAdjacentNodes(node: Node<T, L>): List<Node<T,L>> {
        return map[node]?.toList() ?: listOf()
    }

    override fun hasPath(
        source: L,
        destination: L,
        strategy: VisitStrategy<T, L>,
        iterator: Iterator<Node<T, L>>
    ): Boolean {
        val incidents = map
            .filter { t -> t.key.label == source }
            .values.firstOrNull()

        if (incidents == null) {
            return false
        }

        return incidents.any { n -> n.label == destination}
                || incidents.any { n -> hasPath(n.label, destination, strategy, iterator)}
    }

    override fun hasCycle(
        label: L,
        visitStrategy: VisitStrategy<T, L>,
        iterator: Iterator<Node<T, L>>
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

    override fun iterator(): Iterator<Node<T, L>> {
        return AdjacencyListBreadth(this)
    }

    override fun visit(
        node: Node<T, L>,
        strategy: VisitStrategy<T, L>,
        iterator: Iterator<Node<T, L>>
    ) {
        strategy.act(node, iterator)
    }

}
