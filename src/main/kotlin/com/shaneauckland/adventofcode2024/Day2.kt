package com.shaneauckland.adventofcode2024

class Day2 {
    fun run(reports: List<List<Int>>) {
        val (safeReports, unsafeReports) = reports.partition(::checkReportWithNoDampener)

        println(safeReports.size)

        val safelyDampenedReports = unsafeReports.filter { levels ->
            if (levels[0] == levels[1]) checkReportWithNoDampener(levels.drop(1))
            else checkReportWithDampener(levels)
        }

        println(safeReports.size + safelyDampenedReports.size)
    }

    fun checkReportWithNoDampener(levels: List<Int>): Boolean {
        val operation = if (levels[0] > levels[1]) Int::safelyGreaterThan else Int::safelyLessThan
        return isSafeReport(levels, 0, false, operation)
    }

    fun checkReportWithDampener(levels: List<Int>): Boolean {
        val weightedOperationDirection = levels.windowed(2, 1).sumOf { it[0] - it[1] }
        val operation = if (weightedOperationDirection > 0) Int::safelyGreaterThan else Int::safelyLessThan
        return isSafeReport(levels, 0, true, operation)
    }

    private fun isSafeReport(levels:List<Int>, index: Int, dampen: Boolean, operation: (Int, Int) -> Boolean): Boolean =
        if (levels.size == index + 1) true
        else {
            val result = operation.invoke(levels[index], levels[index + 1])
            if (!result) {
                if (dampen) {
                    // if this is the last pair then we are safe to ignore a final failure
                    if (levels.size == index + 2) true
                    // we don't know which level is the problematic one without more context, let's just be brutal
                    else if (isSafeReport(levels.filterIndexed { i, _ -> i != index }, 0, false, operation)) true
                    else isSafeReport(levels.filterIndexed { i, _ -> i != index + 1 }, 0, false, operation)
                } else false
            } else {
                isSafeReport(levels, index + 1, dampen, operation)
            }
        }
}

fun Int.safelyLessThan(other:Int): Boolean {
    val diff = this - other
    return diff in -3 .. -1
}

fun Int.safelyGreaterThan(other:Int): Boolean {
    val diff = this - other
    return diff in 1..3
}

fun main() {
    Day2().run(Utils.resourcesFileAsList("/day2.txt")
        .map { it.split(" ") }
        .map { it.map(String::toInt) })
}