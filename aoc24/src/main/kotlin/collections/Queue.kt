package com.github.matto1matteo.collections

interface Queue<T> {
    fun append(item: T)
    fun append(vararg items: T)
    fun append(items: Collection<T>)
    fun pop(): T?
}