package com.github.matto1matteo.math.graph

import com.github.matto1matteo.math.graph.iterators.AdiacenceListBreadth
import com.github.matto1matteo.math.graph.strategies.VisitStrategy

class AdiacenceList<T, L>(val nodes: HashMap<Node<T, L>, MutableList<Node<T, L>>>): Graph<T, L> {
    var root: Node<T, L> = nodes.keys.first()
        get() = field
        private set(value) {
            field = value
        }
    companion object {
        fun <T, L>withRoot(graph: AdiacenceList<T, L>, node: Node<T, L>): AdiacenceList<T, L> {
            val n = graph.nodes.keys
                .firstOrNull { n1 -> n1.label == node.label }
            if (n == null)
                return graph

            graph.root = n
            return graph
        }
    }
    fun findWithLabel(label: L): Node<T, L> {
        return nodes.keys.first { n -> n.label == label }
    }

    fun hasIncident(source: L, dest: L): Boolean {
        return nodes
            .filter { n -> n.key.label == source }
            .values.single()
            .any { n -> n.label == dest }
    }

    override fun hasPath(
        source: L,
        destination: L,
        strategy: VisitStrategy<T, L>,
        iterator: Iterator<Node<T, L>>
    ): Boolean {
        val incidents = nodes
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
        val toCheck = nodes
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
        return AdiacenceListBreadth<T, L>(this)
    }

    override fun visit(
        node: Node<T, L>,
        strategy: VisitStrategy<T, L>,
        iterator: Iterator<Node<T, L>>
    ) {
        strategy.act(node, iterator)
    }

}
