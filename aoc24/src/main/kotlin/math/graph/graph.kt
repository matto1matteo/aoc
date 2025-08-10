package com.github.matto1matteo.math.graph

import com.github.matto1matteo.math.graph.strategies.SimpleStrategy
import com.github.matto1matteo.math.graph.strategies.VisitStrategy
import kotlin.collections.Iterable
import kotlin.collections.Iterator

interface Graph<T, L>: Iterable<Node<T, L>> {
    fun hasCycle(node: Node<T, L>): Boolean {
        return hasCycle(node.label)
    }
    fun hasPath(source: L, destination: L, strategy: VisitStrategy<T, L>, iterator: Iterator<Node<T, L>>): Boolean
    fun hasPath(source: L, destination: L): Boolean{
        return hasPath(source, destination, SimpleStrategy<T, L>(), iterator())
    }
    fun hasCycle(label: L): Boolean {
        val strategy = SimpleStrategy<T, L>()
        return hasCycle(label, strategy, iterator())
    }
    fun hasCycle(label: L, visitStrategy: VisitStrategy<T, L>, iterator: Iterator<Node<T, L>>): Boolean
    fun visit(node: Node<T, L>) {
        return visit(node, SimpleStrategy<T, L>(), iterator())
    }
    fun visit(node: Node<T, L>, strategy: VisitStrategy<T, L>, iterator: Iterator<Node<T, L>>)
}

