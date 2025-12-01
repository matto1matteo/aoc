package com.github.matto1matteo.math.graph.iterators

import com.github.matto1matteo.collections.ListQueue
import com.github.matto1matteo.collections.Queue
import com.github.matto1matteo.math.graph.AdjacencyList
import com.github.matto1matteo.math.graph.Node

class AdjacencyListBreadth<L> : BreadthIterator<L> {
    private val graph: AdjacencyList<L>
    private val queue: Queue<Node<L>>
    private val visited: MutableSet<Node<L>>
    private var cursor: Node<L>?

    constructor(graph: AdjacencyList<L>) : super() {
        this.graph = graph
        this.queue = ListQueue()
        this.visited = mutableSetOf()
        this.cursor = graph.map.keys.firstOrNull()
    }


    override fun hasNext(): Boolean {
        return cursor != null
    }

    override fun next(): Node<L> {
        if (!hasNext()) {
            throw NoSuchElementException("No more nodes to iterate from the given root")
        }

        val node = cursor!!
        val adjacentNodes = graph
            .getAdjacentNodes(node)
        queue.append(adjacentNodes)
        cursor = queue.pop()

        // Update current
        return node
    }
}