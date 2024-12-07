package com.github.matto1matteo.problems

import java.io.BufferedReader

interface Problem {
    val fileName: String;
    fun firstSolution(): String;
    fun secondSolution(): String;
}

fun Problem.readResource() : BufferedReader {
    return {}.javaClass.getResourceAsStream("/$fileName")?.bufferedReader() ?: throw Exception("Resource not found")
}