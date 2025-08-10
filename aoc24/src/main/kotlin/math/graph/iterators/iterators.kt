package com.github.matto1matteo.math.graph.iterators

import com.github.matto1matteo.math.graph.Node

abstract class BreadthIterator<T, L>: Iterator<Node<T, L>> {
    abstract override fun hasNext(): Boolean
    abstract override fun next(): Node<T, L>
}

abstract class DepthIterator<T, L>: Iterator<Node<T, L>> {
    abstract override fun hasNext(): Boolean
    abstract override fun next(): Node<T, L>
}
