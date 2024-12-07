package com.shaneauckland.adventofcode2024

import kotlin.math.floor

class Day5{
    fun run() {
        val (rules, orderings) = Utils.resourcesFileAsList("/day5.txt")
            .fold(Pair(mutableListOf<Pair<Int, Int>>(), mutableListOf<List<Int>>())) { acc, line ->
                when {
                    line.contains('|') -> {
                        val split = line.split('|')
                        acc.first.add(Pair(split[0].toInt(), split[1].toInt()))
                    }
                    line.contains(',') -> acc.second.add(line.split(',').map(String::toInt))
                }
                acc
            }

        val (validOrderings, invalidOrderings) = findValidOrderings(rules, orderings)

        val total = validOrderings.sumOf { it.getFromMiddleIndex() }

        println(total)

        val invalidTotal = invalidOrderings.map { fixInvalidOrdering(it, rules) }.sumOf { it.getFromMiddleIndex() }

        println(invalidTotal)
    }

    fun findValidOrderings(rules: List<Pair<Int, Int>>, orderings: List<List<Int>>): Pair<List<List<Int>>, List<List<Int>>> =
        orderings.partition { checkOrdering(rules, it, emptyList()) == null }

    tailrec fun checkOrdering(rules: List<Pair<Int, Int>>, pages: List<Int>, previous: List<Int>): Pair<Int, Int>? =
        if (pages.isEmpty()) null
        else {
            val currentPage = pages.first()
            previous.forEach { p ->
                val rule = rules.find { it == Pair(currentPage, p) }
                if (rule != null) return rule
            }
            checkOrdering(rules, pages.drop(1), previous + currentPage)
        }

    tailrec fun fixInvalidOrdering(ordering: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val orderingCheckResult = checkOrdering(rules, ordering, emptyList())
        return if (orderingCheckResult == null) ordering
        else {
            val safePart = ordering.takeWhile { it != orderingCheckResult.second }
            val nextPart = ordering.drop(safePart.size + 1).takeWhile { it != orderingCheckResult.first }
            val endPart = ordering.drop(safePart.size + nextPart.size + 2)

            fixInvalidOrdering(safePart + orderingCheckResult.first + nextPart + orderingCheckResult.second + endPart, rules)
        }
    }


}

fun main() {
    Day5().run()
}

fun <T> List<T>.getFromMiddleIndex(): T = get(floor(this.size.toFloat() / 2).toInt())