package com.github.matto1matteo.math.graph.strategies

import com.github.matto1matteo.math.graph.Node

interface VisitStrategy<T, L> {
    fun act(node: Node<T, L>, iterator: Iterator<Node<T, L>>): Unit
}