package com.github.matto1matteo.math.graph.strategies

import com.github.matto1matteo.math.graph.Node

class SimpleStrategy<T, L>: VisitStrategy<T, L> {
    override fun act(node: Node<T, L>, iterator: Iterator<Node<T, L>>) {
        node.visited = true
    }

}