package com.github.matto1matteo.math.graph

import com.github.matto1matteo.math.graph.strategies.VisitStrategy
import com.github.matto1matteo.math.graph.strategies.VisitedSetStrategy
import kotlin.collections.Iterable
import kotlin.collections.Iterator

interface Graph<L>: Iterable<Node<L>> {
    val nodes: List<Node<L>>
    val edges: List<Pair<Node<L>, Node<L>>>

    fun hasCycle(node: Node<L>): Boolean {
        return hasCycle(node.label)
    }
    fun hasPath(source: L, destination: L, strategy: VisitStrategy<L>, iterator: Iterator<Node<L>>): Boolean
    fun hasPath(source: L, destination: L): Boolean{
        return hasPath(source, destination, VisitedSetStrategy<L>(), iterator())
    }
    fun hasCycle(label: L): Boolean {
        val strategy = VisitedSetStrategy<L>()
        return hasCycle(label, strategy, iterator())
    }
    fun hasCycle(label: L, visitStrategy: VisitStrategy<L>, iterator: Iterator<Node<L>>): Boolean
    fun visit(node: Node<L>) {
        return visit(node, VisitedSetStrategy<L>())
    }
    fun visit(node: Node<L>, strategy: VisitStrategy<L>)
}

