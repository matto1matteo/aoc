package com.github.matto1matteo.math.graph.strategies

import com.github.matto1matteo.math.graph.Node

interface VisitStrategy<L> {
    fun act(node: Node<L>): Unit
    fun visited(node: Node<L>): Boolean
}