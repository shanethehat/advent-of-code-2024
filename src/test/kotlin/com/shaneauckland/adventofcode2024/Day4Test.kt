package com.shaneauckland.adventofcode2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day4Test : StringSpec ({
    "North can search from the center" {
        North(simpleGrid).searchFrom(4, 3) shouldBe true
    }

    "NorthEast can search from the center" {
        NorthEast(simpleGrid).searchFrom(4, 3) shouldBe true
    }

    "East can search from the center" {
        East(simpleGrid).searchFrom(4, 3) shouldBe true
    }

    "SouthEast can search from the center" {
        SouthEast(simpleGrid).searchFrom(4, 3) shouldBe true
    }

    "Searcher finds all directions" {
        Searcher(simpleGrid).searchFrom(4, 3) shouldBe true
    }

    "it finds a x-mas" {
        Day4().findXmasAt(2, 1, listOf("QMQS", "QQAQ", "QMQS")) shouldBe true
        Day4().findXmasAt(2, 1, listOf("QSQS", "QQAQ", "QMQM")) shouldBe true
    }

}) {
    companion object {
        val simpleGrid = listOf(
            ".S..S..S.",
            "..A.A.A..",
            "...MMM...",
            ".SAMXMAS.",
            "...MMM...",
            "..A.A.A..",
            ".S..S..S.",
        )
    }
}