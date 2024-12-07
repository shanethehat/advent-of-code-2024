package com.shaneauckland.adventofcode2024

typealias Operation = (Long, Long) -> Long

class Day7 {
    val operations = listOf<Operation>(
        Math::multiplyExact,
        Math::addExact,
        ::combine
    )

    fun run() {
        val data = Utils.resourcesFileAsList("/day7.txt")

        val total = data.fold(0L) { total, equation ->
            val split = equation.split(" ")
            val target = split.first().dropLast(1).toLong()
            val values = split.drop(1).map { it.toLong() }
            if (makesTotal(target, values)) total + target else total
        }

        println(total)
    }

    fun makesTotal(target: Long, values: List<Long>): Boolean {
        fun makesTotalInternal(target: Long, current: Long, values: List<Long>): Boolean =
            when {
                values.isEmpty() -> false
                values.size == 1 -> operations.any { it(current, values.first()) == target }
                else -> operations.any { makesTotalInternal(target, it(current, values.first()), values.drop(1)) }
            }
        return makesTotalInternal(target, values.first(), values.drop(1))
    }
}

fun combine(a: Long, b:Long): Long =
    "$a$b".toLong()

fun main() {
    Day7().run()
}