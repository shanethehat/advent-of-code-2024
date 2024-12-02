package com.shaneauckland.adventofcode2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Tests : StringSpec({
    "a safe sequence is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(7, 6, 4, 2, 1)) shouldBe true
    }

    "an ascending sequence with a large step is unsafe with a dampener" {
        Day2().checkReportWithDampener(listOf(1, 2, 7, 8, 9)) shouldBe false
    }

    "an descending sequence with a large step is unsafe with a dampener" {
        Day2().checkReportWithDampener(listOf(9, 7, 6, 2, 1)) shouldBe false
    }

    "a sequence containing a duplicate is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(7, 6, 6, 4, 2, 1)) shouldBe true
    }

    "a sequence starting with a duplicate is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(7, 7, 6, 4, 2, 1)) shouldBe true
    }

    "a sequence ending with a duplicate is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(7, 6, 4, 2, 1, 1)) shouldBe true
    }

    "a sequence containing an erroneous direction is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(1, 3, 2, 4, 5,)) shouldBe true
    }

    "a sequence starting with an erroneous direction is safe with a dampener" {
        Day2().checkReportWithDampener(listOf(2, 1, 3, 4, 5,)) shouldBe true
    }

    "an ascending sequence with a drop at the end is safe with the dampener" {
        Day2().checkReportWithDampener(listOf(44, 47, 50, 51, 53, 54, 53)) shouldBe true
    }

    "an ascending sequence with a drop near the end is safe with the dampener" {
        Day2().checkReportWithDampener(listOf(44, 47, 50, 51, 53, 54, 53, 55)) shouldBe true
    }
})