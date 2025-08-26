package com.github.matto1matteo.collections

class ListQueue<T>: Queue<T> {
    private var items = mutableListOf<T>()

    constructor(vararg items: T) {
        this.items = items.toMutableList()
    }

    constructor(items: MutableCollection<T>) {
        this.items = items.toMutableList()
    }

    override fun append(item: T) {
        items.add(items.lastIndex + 1, item)
    }

    override fun append(vararg items: T) {
        this.items.addAll(items)
    }

    override fun append(items: Collection<T>) {
        this.items.addAll(items)
    }

    override fun pop(): T? {
        val item = items.firstOrNull()

        if (item != null) {
            items.removeFirst()
        }

        return item
    }
}