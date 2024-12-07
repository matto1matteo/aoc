package com.github.matto1matteo.problems

class Day05(override val fileName: String) : Problem {
    override fun firstSolution(): String {
        val reader = readResource()

        var line: String
        val rules: MutableMap<Int, HashSet<Int>> = mutableMapOf()
        while (reader.readLine().also { line = it }.isNotBlank()) {
            val xy = line.split("|").map { it.toInt() }
            if (rules[xy[0]] == null) {
                rules[xy[0]] = HashSet()
            }
            rules[xy[0]]!!.add(xy[1])
        }

        val middlePages =  reader.readLines()
            .map { update -> update.split(",").map { it.toInt() } }
            .filter { isOrderOk(it, rules) }
            .map { it[it.count() / 2] }
        return middlePages
            .sum()
            .toString()
    }

    override fun secondSolution(): String {
        val reader = readResource()

        var line: String
        val rules: MutableMap<Int, HashSet<Int>> = mutableMapOf()
        while (reader.readLine().also { line = it }.isNotBlank()) {
            val xy = line.split("|").map { it.toInt() }
            if (rules[xy[0]] == null) {
                rules[xy[0]] = HashSet()
            }
            rules[xy[0]]!!.add(xy[1])
        }

        val unordered =  reader.readLines()
            .map { update -> update.split(",").map { it.toInt() } }
            .filter { !isOrderOk(it, rules) }
        return order(unordered, rules).sumOf { it[it.count() / 2] }.toString()
    }
}

private fun isOrderOk(update: List<Int>, rules: Map<Int, Set<Int>>) : Boolean {
    for ((i, page) in update.withIndex()) {
        val subList = update.subList(0, i)
        val rulesSet = rules[page] ?: setOf()
        val union = subList.intersect(rulesSet)
        if (union.isNotEmpty()) {
            return false
        }
    }
    return true
}

class Page(val p: Int)
class PageComparator(val rules: Map<Int, Set<Int>>) : Comparator<Page> {
    override fun compare(p0: Page?, p1: Page?): Int {
        if (rules[p0?.p]?.contains(p1?.p) == true) {
            return -1
        }
        if (rules[p1?.p]?.contains(p0?.p) == true) {
            return 1
        }
        return 0
    }
}

private fun order(updates: List<List<Int>>, rules: Map<Int, Set<Int>>) : List<List<Int>> {
    return updates
        .map { update -> update.map { Page(it) }.sortedWith(PageComparator(rules)) }
        .map { update -> update.map { it.p } }
}
