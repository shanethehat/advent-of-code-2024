package com.shaneauckland.adventofcode2024

class Day3 {
    companion object {
        val mulRegex = """mul\((?<a>\d+),(?<b>\d+)\)""".toRegex()
    }

    fun run() {
        val memoryContent = Utils.resourcesFileAsList("/day3.txt").joinToString()

        val fullSum = runMuls(memoryContent)
        println(fullSum)

        val conditionalSum = runMuls(memoryContent, useConditional = true)
        println(conditionalSum)
    }

    tailrec fun runMuls(memory: String, total: Int = 0, useConditional: Boolean = false, isDisabled: Boolean = false): Int =
        if (memory.isEmpty()) total
        else {
            when (memory.first()) {
                'd' -> {
                    val position = memory.indexOf(')') + 1
                    when (memory.take(position)) {
                        "don't()" -> runMuls(memory.drop(position), total, useConditional, isDisabled = true)
                        "do()" -> runMuls(memory.drop(position), total, useConditional, isDisabled = false)
                        else -> runMuls(memory.drop(1), total, useConditional, isDisabled)
                    }
                }
                'm' -> {
                    if (isDisabled) {
                        runMuls(memory.drop(1), total, useConditional, isDisabled)
                    } else {
                        val position = memory.indexOf(')') + 1
                        val section = memory.take(position)
                        when (val result = mulRegex.find(section)) {
                            null -> runMuls(memory.drop(1), total, useConditional, isDisabled)
                            else -> {
                                runMuls(
                                    memory.drop(position),
                                    total + result.groups["a"]!!.value.toInt() * result.groups["b"]!!.value.toInt(),
                                    useConditional,
                                    isDisabled
                                )
                            }
                        }
                    }
                }
                else -> runMuls(memory.drop(1), total, useConditional, isDisabled)
            }
        }

}

fun main() {
    Day3().run()
}