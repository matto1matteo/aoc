package com.github.matto1matteo.math.graph.iterators

import com.github.matto1matteo.math.graph.Node

abstract class BreadthIterator<L>: Iterator<Node<L>> {
    abstract override fun hasNext(): Boolean
    abstract override fun next(): Node<L>
}

abstract class DepthIterator<T, L>: Iterator<Node<L>> {
    abstract override fun hasNext(): Boolean
    abstract override fun next(): Node<L>
}
