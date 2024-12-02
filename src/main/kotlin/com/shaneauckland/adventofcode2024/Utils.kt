package com.shaneauckland.adventofcode2024

object Utils {
    fun resourcesFileAsList(filename: String): List<String> =
        object {}.javaClass.getResourceAsStream(filename)
            ?.bufferedReader()
            ?.readLines()!!
}