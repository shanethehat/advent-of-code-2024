package com.shaneauckland.adventofcode2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test : StringSpec ({
    "it detects a total using a single addition operand" {
        Day7().makesTotal(6, listOf(4,2)) shouldBe true
    }

    "it detects a failing attempt using a single addition operand" {
        Day7().makesTotal(7, listOf(4,2)) shouldBe false
    }

    "it detects a total using a single multiplication operand" {
        Day7().makesTotal(8, listOf(4,2)) shouldBe true
    }

    "it detects a total using multiple addition operands" {
        Day7().makesTotal(7, listOf(4,2,1)) shouldBe true
    }

    "it detects a total using multiple multiplication operands" {
        Day7().makesTotal(24, listOf(4,2,3)) shouldBe true
    }

    "it detects a total using concatenation" {
        Day7().makesTotal(156, listOf(15, 6)) shouldBe true
    }
})