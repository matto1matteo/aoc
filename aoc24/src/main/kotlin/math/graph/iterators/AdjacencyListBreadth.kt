package com.github.matto1matteo.math.graph.iterators

import com.github.matto1matteo.collections.ListQueue
import com.github.matto1matteo.collections.Queue
import com.github.matto1matteo.math.graph.AdiacenceList
import com.github.matto1matteo.math.graph.Node

class AdjacencyListBreadth<T, L> : BreadthIterator<T, L> {
    private val graph: AdiacenceList<T, L>
    private val queue: Queue<Node<T, L>>
    private val visited: Set<Node<T, L>>

    constructor(graph: AdiacenceList<T, L>) : super() {
        this.graph = graph
        this.queue = ListQueue()
        this.visited = setOf()
        if (graph.root != null) {
            this.queue.append(graph.root!!)
        }
    }


    override fun hasNext(): Boolean {
        return graph.nodes.any{n -> !n.key.visited }
    }

    override fun next(): Node<T, L> {
        if (!hasNext()) {
            throw NoSuchElementException("No more nodes to iterate from the given root")
        }
        var node = queue.pop()!!
        while (node.visited) {
            node = queue.pop()!!
        }
        node.visited = true
        val nodes = graph.nodes[node] ?: mutableListOf()
        queue.append(nodes)
        return node
    }
}