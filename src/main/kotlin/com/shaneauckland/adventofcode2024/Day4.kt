package com.shaneauckland.adventofcode2024

class Day4 {
    fun run() {
        val data = Utils.resourcesFileAsList("/day4.txt")

        val searcher = Searcher(data)

        val occurrences = data.foldIndexed(0) { y, count, row ->
            count + row.foldIndexed(0) { x, innerCount, char ->
                if (char == 'X') innerCount + searcher.searchFrom(x, y)
                else innerCount
            }
        }

        println(occurrences)

        val xmases = data.foldIndexed(0) { y, count, row ->
            count + row.foldIndexed(0) { x, innerCount, char ->
                if (x == 0 || y == 0 || x == row.length - 1 || y == data.size - 1) innerCount
                else if (char == 'A' && findXmasAt(x, y, data)) innerCount + 1
                else innerCount
            }
        }

        println(xmases)
    }

    fun findXmasAt(x: Int, y: Int, grid: List<String>): Boolean {
        val topSection = grid[y-1].substring(x-1..x+1)
        val bottomSection = grid[y+1].substring(x-1..x+1)

        return (topSection[0] == 'M' && bottomSection[2] == 'S' || topSection[0] == 'S' && bottomSection[2] == 'M')
            && (topSection[2] == 'M' && bottomSection[0] == 'S' || topSection[2] == 'S' && bottomSection[0] == 'M')
    }

}

class Searcher(grid: List<String>) {
    private val searchers = listOf(
        North(grid),
        South(grid),
        East(grid),
        West(grid),
        NorthEast(grid),
        SouthEast(grid),
        NorthWest(grid),
        SouthWest(grid)
    )

    fun searchFrom(x: Int, y: Int): Int =
        searchers.map { if (it.searchFrom(x, y)) 1 else 0 }.sum()
}

interface DirectionalSearcher {
    val minX: Int
    val minY: Int
    val maxX: Int
    val maxY: Int
    val mPos: Pair<Int, Int>
    val aPos: Pair<Int, Int>
    val sPos: Pair<Int, Int>
    val width: Int
    val height: Int
    val grid: List<String>

    fun searchFrom(x: Int, y: Int): Boolean =
        if (canSearch(x, y)) {
            grid[y + mPos.second][x + mPos.first] == 'M'
                    && grid[y + aPos.second][x + aPos.first] == 'A'
                    && grid[y + sPos.second][x + sPos.first] == 'S'
        } else false

    fun canSearch(x: Int, y: Int): Boolean =
        x >= minX && y >= minY && height - maxY > y && width - maxX > x
}

abstract class AbstractDirectionalSearcher(final override val grid: List<String>) : DirectionalSearcher {
    override val width: Int = grid[0].length
    override val height: Int = grid.size
}

class North(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 0
    override val minY: Int = 3
    override val maxX: Int = 0
    override val maxY: Int = 0

    override val mPos: Pair<Int, Int> = Pair(0, -1)
    override val aPos: Pair<Int, Int> = Pair(0, -2)
    override val sPos: Pair<Int, Int> = Pair(0, -3)
}

class NorthEast(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 0
    override val minY: Int = 3
    override val maxX: Int = 3
    override val maxY: Int = 0

    override val mPos: Pair<Int, Int> = Pair(1, -1)
    override val aPos: Pair<Int, Int> = Pair(2, -2)
    override val sPos: Pair<Int, Int> = Pair(3, -3)
}

class East(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 0
    override val minY: Int = 0
    override val maxX: Int = 3
    override val maxY: Int = 0

    override val mPos: Pair<Int, Int> = Pair(1, 0)
    override val aPos: Pair<Int, Int> = Pair(2, 0)
    override val sPos: Pair<Int, Int> = Pair(3, 0)
}

class SouthEast(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 0
    override val minY: Int = 0
    override val maxX: Int = 3
    override val maxY: Int = 3

    override val mPos: Pair<Int, Int> = Pair(1, 1)
    override val aPos: Pair<Int, Int> = Pair(2, 2)
    override val sPos: Pair<Int, Int> = Pair(3, 3)
}

class South(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 0
    override val minY: Int = 0
    override val maxX: Int = 0
    override val maxY: Int = 3

    override val mPos: Pair<Int, Int> = Pair(0, 1)
    override val aPos: Pair<Int, Int> = Pair(0, 2)
    override val sPos: Pair<Int, Int> = Pair(0, 3)
}

class SouthWest(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 3
    override val minY: Int = 0
    override val maxX: Int = 0
    override val maxY: Int = 3

    override val mPos: Pair<Int, Int> = Pair(-1, 1)
    override val aPos: Pair<Int, Int> = Pair(-2, 2)
    override val sPos: Pair<Int, Int> = Pair(-3, 3)
}

class West(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 3
    override val minY: Int = 0
    override val maxX: Int = 0
    override val maxY: Int = 0

    override val mPos: Pair<Int, Int> = Pair(-1, 0)
    override val aPos: Pair<Int, Int> = Pair(-2, 0)
    override val sPos: Pair<Int, Int> = Pair(-3, 0)
}

class NorthWest(grid: List<String>) : AbstractDirectionalSearcher(grid) {
    override val minX: Int = 3
    override val minY: Int = 3
    override val maxX: Int = 0
    override val maxY: Int = 0

    override val mPos: Pair<Int, Int> = Pair(-1, -1)
    override val aPos: Pair<Int, Int> = Pair(-2, -2)
    override val sPos: Pair<Int, Int> = Pair(-3, -3)
}

fun main() {
    Day4().run()
}