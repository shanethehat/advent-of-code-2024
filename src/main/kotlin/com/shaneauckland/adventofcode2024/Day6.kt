package com.shaneauckland.adventofcode2024

class Day6 {
    companion object {
        const val SPACE = '.'
        const val OBSTACLE = '#'
        const val GUARD = '^'


    }

    fun run() {
        val map = Utils.resourcesFileAsList("/day6.txt").toMutableList()

        val spacePositions = map.foldIndexed(listOf<Pair<Int, Int>>()) { y, acc, row ->
            val xPositions = row.foldIndexed(listOf<Pair<Int, Int>>()) { x, a, char ->
                if (char == SPACE) a + Pair(x, y) else a
            }
            acc + xPositions
        }

        println(spacePositions)

        val startingPosition = findStartingPosition(map)!!
        println("Starting position: $startingPosition")

        val loopingCauseLocations = spacePositions.map { pos ->
            println(pos)
            map[pos.second] = map[pos.second].replaceRange(pos.first, pos.first + 1, "$OBSTACLE")
            val isLoopingCause = findPositions(map, startingPosition).isEmpty()
            map[pos.second] = map[pos.second].replaceRange(pos.first, pos.first + 1, "$SPACE")
            isLoopingCause
        }.filter { it }

        val visitedPositions = findPositions(map, startingPosition)

        val visitedPositionsWithoutDirection = visitedPositions.map { Pair(it.x, it.y) }.distinct()

        println("Part 1 answer: ${visitedPositionsWithoutDirection.size}") // 4939

        println(visitedPositions)

//        val loopCausingLocations = findLoopCausingLocations(map, visitedPositions.toList())
//
//        println("Part 2 answer: ${loopCausingLocations.size}")
//
//        // not 1530
//
//        val loopingVariations = mapVariations.fold(0) { acc, variation ->
//            if (findPositions(variation, startingPosition).isEmpty()) acc + 1
//            else acc
//        }

        println("Part 2 alt answer: ${loopingCauseLocations.size}")
    }

    tailrec fun findStartingPosition(map: List<String>, x: Int = 0, y: Int = 0): Point? =
        when {
            y >= map.size -> null
            x >= map[y].length -> findStartingPosition(map, 0, y + 1)
            map[y][x] == GUARD -> Point(x, y, Direction.UP)
            else -> findStartingPosition(map, x + 1, y)
        }

    fun findPositions(map: List<String>, currentPosition: Point): Set<Point> {
        tailrec fun findPositionsInternal(
            map: List<String>,
            currentPosition: Point,
            positions: Set<Point>
        ): Set<Point> {
            val newPosition = currentPosition.transformBy(currentPosition.direction)
            return when {
                newPosition.y < 0 || newPosition.y >= map.size -> positions
                newPosition.x < 0 || newPosition.x >= map[newPosition.y].length -> positions
                positions.contains(newPosition) -> emptySet() // nasty hack
                map[newPosition.y][newPosition.x] == OBSTACLE -> findPositionsInternal(
                    map,
                    currentPosition.copy(direction = currentPosition.direction.next()),
                    positions
                )

                else -> findPositionsInternal(map, newPosition, positions + newPosition)
            }
        }
        return findPositionsInternal(map, currentPosition, setOf(currentPosition))
    }

    tailrec fun findLoopCausingLocations(
        map: List<String>,
        positionsToCheck: List<Point>,
        obstacleLocations: Set<Pair<Int, Int>> = emptySet()
    ): Set<Pair<Int, Int>> {
        println("Positions left to check: ${positionsToCheck.size}")
        return if (positionsToCheck.isEmpty()) obstacleLocations
        else {
            val positionToCheck = positionsToCheck.first()
            val remainingPositions = positionsToCheck.drop(1)

            val distanceToPosition2 = distanceToNextObstacle(map, positionToCheck)
            if (distanceToPosition2 == null) findLoopCausingLocations(map, remainingPositions, obstacleLocations)
            else {
                val position2 = positionToCheck.transformBy(distanceToPosition2).copy(direction = positionToCheck.direction.next())
                val distanceToPosition3 = distanceToNextObstacle(map, position2)

                if (distanceToPosition3 == null) findLoopCausingLocations(map, remainingPositions, obstacleLocations)
                else {
                    val position3 = position2.transformBy(distanceToPosition3).copy(direction = position2.direction.next())
                    val distanceToPosition4 = distanceToNextObstacle(map, position3)

                    if (distanceToPosition4 == null) findLoopCausingLocations(map, remainingPositions, obstacleLocations)
                    else {
                        val position4 = position3.transformBy(distanceToPosition4).copy(direction = position3.direction.next())
                        val targetCoords = positionToCheck.toPair()
                        val obstacleLocation = position4.transformBy(distanceToPosition2).toPair()
                        println("Starting from: $positionToCheck, position4: $position4")
                        if (isClearToPoint(map, position4, targetCoords)) {
                            println(obstacleLocation)
                            findLoopCausingLocations(map, remainingPositions, obstacleLocations + obstacleLocation)
                        }
                        else findLoopCausingLocations(map, remainingPositions, obstacleLocations)
                    }
                }
            }
        }
    }

    tailrec fun distanceToNextObstacle(map: List<String>, currentPosition: Point, count: Int = 0): Int? {
        val newPosition = currentPosition.transformBy(currentPosition.direction)
        return when {
            newPosition.y < 0 || newPosition.y >= map.size || newPosition.x < 0 || newPosition.x >= map[newPosition.y].length -> null
            map[newPosition.y][newPosition.x] == OBSTACLE -> count
            else -> distanceToNextObstacle(map, newPosition, count + 1)
        }
    }

    tailrec fun isClearToPoint(map: List<String>, currentPosition: Point, targetCoords: Pair<Int, Int>): Boolean =
        when {
            currentPosition.toPair() == targetCoords -> true
            currentPosition.y < 0 || currentPosition.y >= map.size -> false
            currentPosition.x < 0 || currentPosition.x >= map[currentPosition.y].length -> false
            map[currentPosition.y][currentPosition.x] == OBSTACLE -> false
            else -> isClearToPoint(map, currentPosition.transformBy(1), targetCoords)
        }
}

data class Point(val x: Int, val y: Int, val direction: Direction) {
    fun transformBy(newDirection: Direction): Point = Point(x + newDirection.movement.first, y + newDirection.movement.second, newDirection)
    fun transformBy(distance: Int): Point = when(direction) {
        Direction.UP -> copy(y = y - distance)
        Direction.LEFT -> copy(x = x - distance)
        Direction.DOWN -> copy(y = y + distance)
        Direction.RIGHT -> copy(x = x + distance)
    }
    fun toPair(): Pair<Int, Int> = Pair(x, y)
}

enum class Direction(val movement: Pair<Int, Int>) {
    UP(Pair(0, -1)),
    DOWN(Pair(0, 1)),
    RIGHT(Pair(1, 0)),
    LEFT(Pair(-1, 0));

    fun next(): Direction = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}


fun main() {
    Day6().run()
}