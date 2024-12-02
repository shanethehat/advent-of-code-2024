package com.shaneauckland.adventofcode2024

object Utils {
    fun resourcesFileAsList(filename: String): List<String> =
        object {}.javaClass.getResourceAsStream("/day1.txt")
            ?.bufferedReader()
            ?.readLines()!!
}