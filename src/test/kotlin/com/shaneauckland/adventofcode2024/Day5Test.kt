package com.shaneauckland.adventofcode2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day5Test : StringSpec ({
//    75,47,61,53,29
//    97,61,53,29,13
//    75,29,13
//    75,97,47,61,53
//    61,13,29
//    97,13,75,29,47

    "it checks a valid ordering" {
        val valid = listOf(
            listOf(75,47,61,53,29),
            listOf(97,61,53,29,13),
            listOf(75,29,13)
        )

        Day5().findValidOrderings(rules, valid) shouldBe Pair(valid, emptyList())
    }

    "it rejects invalid orderings" {
        val invalid = listOf(
            listOf(75,97,47,61,53),
            listOf(61,13,29),
            listOf(97,13,75,29,47)
        )

        invalid.forEach {
            Day5().findValidOrderings(rules, listOf(it)) shouldBe Pair(emptyList(), listOf(it))
        }

        Day5().findValidOrderings(rules, invalid) shouldBe Pair(emptyList(), invalid)
    }

    "it corrects an invalid ordering" {
        Day5().fixInvalidOrdering(listOf(75,97,47,61,53), rules) shouldBe listOf(97, 75, 47, 61, 53)
        Day5().fixInvalidOrdering(listOf(61,13,29), rules) shouldBe listOf(61,29, 13)
        Day5().fixInvalidOrdering(listOf(97,13,75,29,47), rules) shouldBe listOf(97,75,47,29,13)
    }

}) {
    companion object {
        val rules = listOf(
            Pair(47,53),
            Pair(97,13),
            Pair(97,61),
            Pair(97,47),
            Pair(75,29),
            Pair(61,13),
            Pair(75,53),
            Pair(29,13),
            Pair(97,29),
            Pair(53,29),
            Pair(61,53),
            Pair(97,53),
            Pair(61,29),
            Pair(47,13),
            Pair(75,47),
            Pair(97,75),
            Pair(47,61),
            Pair(75,61),
            Pair(47,29),
            Pair(75,13),
            Pair(53,13)
        )

    }
}

