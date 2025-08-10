package com.github.matto1matteo.math.graph

class Node<T, L>(val value: T, val label: L & Any) {
    var visited: Boolean = false
}
