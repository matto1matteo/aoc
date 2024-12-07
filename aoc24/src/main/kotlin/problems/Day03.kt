package com.github.matto1matteo.problems

class Day03(override var fileName: String) : Problem {
    override fun firstSolution(): String {
        val reader = readResource()
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val text = reader.readText()
        return regex.findAll(text)
            .map { it.groups[1]!!.value.toInt() * it.groups[2]!!.value.toInt() }
            .sum()
            .toString()
    }

    override fun secondSolution(): String {
        val reader = readResource()
        // (mul\([0-999],[0-999]\)|do\(\)|don't\(\))
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
        val text = reader.readText()
        var acc = 0
        var enabled = true
        for (match in regex.findAll(text)) {
            if (match.value == "do()") {
                enabled = true
            } else if (match.value == "don't()") {
                enabled = false
            } else if (enabled) {
                acc += match.groups[1]!!.value.toInt() * match.groups[2]!!.value.toInt()
            }
        }
        return acc.toString()
    }
}