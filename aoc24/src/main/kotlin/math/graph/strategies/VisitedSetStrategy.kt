package com.github.matto1matteo.math.graph.strategies

import com.github.matto1matteo.math.graph.Node

class VisitedSetStrategy<L>: VisitStrategy<L> {
    private var visited = mutableSetOf<Node<L>>()
    override fun act( node: Node<L>, ) {
        visited.add(node)
    }

    override fun visited(node: Node<L>): Boolean {
        return visited.contains(node)
    }

}