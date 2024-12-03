package com.shaneauckland.adventofcode2024

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test : StringSpec ({
    "an empty memory string returns 0" {
        Day3().runMuls("") shouldBe 0
    }

    "an entirely invalid memory string returns 0" {
        Day3().runMuls("mfdsahykjdsfa)") shouldBe 0
    }

    "a valid single digit mul call returns the multiplied value" {
        Day3().runMuls("mul(2,3)") shouldBe 6
    }

    "two valid mul calls return the sum of the multiplied values" {
        Day3().runMuls("mul(2,3)mul(4,4)") shouldBe 22
    }

    "two valid mul calls with some junk return the sum of the multiplied values" {
        Day3().runMuls("adfgmul(2,3)mulfgsdfmul(4,4)fff") shouldBe 22
    }

    "invalid muls are ignored" {
        Day3().runMuls("adfgmul(2|3)mulfgsdfmul(4,4 )fff") shouldBe 0
    }

    "conditional execution starts enabled" {
        Day3().runMuls("mul(2,3)", useConditional = true) shouldBe 6
    }

    "a don't condition disables addition" {
        Day3().runMuls("mul(2,3)don't()mul(4,4)") shouldBe 6
    }

    "a do condition enables addition" {
        Day3().runMuls("mul(2,3)don't()mul(4,4)fdsafasddo()fasdfasmul(1,2)") shouldBe 8
    }
})