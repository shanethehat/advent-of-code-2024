package com.shaneauckland.adventofcode2024.day1

class Main {
    fun run() {
        val data = object {}.javaClass.getResourceAsStream("/part1.txt")
            ?.bufferedReader()
            ?.readLines()!!
            .map { it.split("   ") }
            .map { it.first().toInt() to it.last().toInt() }
            .fold(Pair(mutableListOf(), mutableListOf())) { lists: Pair<MutableList<Int>, MutableList<Int>>, (left, right) ->
                lists.first.add(left)
                lists.second.add(right)
                lists
            }

        data.first.sort()
        data.second.sort()

        val result1 = data.first.zip(data.second)
            .map { if (it.first > it.second) Pair(it.first, it.second) else Pair(it.second, it.first) }
            .sumOf { it.first - it.second }

        println(result1)

        val rightSums = data.second.fold(mutableMapOf<Int, Int>()) { sums, value ->
            sums.compute(value) { _, v ->
                if (v == null) 1 else v + 1
            }
            sums
        }

        val result2 = data.first.sumOf { rightSums.getOrDefault(it, 0) * it }

        println(result2)
    }
}

fun main(args: Array<String>) {
    Main().run()
}