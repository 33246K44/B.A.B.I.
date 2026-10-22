package com.example.babi.translator

import kotlin.text.lowercase

data class RuneInfo(
    val rune: String,
    val name: String,
    val meaning: String
)

object FutharkTranslator {

    private val elderFutharkMap = mapOf(
        'a' to "ᚨ",
        'b' to "ᛒ",
        'c' to "ᚲ",
        'd' to "ᛞ",
        'e' to "ᛖ",
        'f' to "ᚠ",
        'g' to "ᚷ",
        'h' to "ᚺ",
        'i' to "ᛁ",
        'j' to "ᛃ",
        'k' to "ᚲ",
        'l' to "ᛚ",
        'm' to "ᛗ",
        'n' to "ᚾ",
        'o' to "ᛟ",
        'p' to "ᛈ",
        'q' to "ᚲ",
        'r' to "ᚱ",
        's' to "ᛊ",
        't' to "ᛏ",
        'u' to "ᚢ",
        'v' to "ᚹ",
        'w' to "ᚹ",
        'x' to "ᚲ",
        'y' to "ᛃ",
        'z' to "ᛉ"
    )

    private val youngerFutharkMap = mapOf(
        'a' to "ᛆ",
        'b' to "ᛒ",
        'c' to "ᚴ",
        'd' to "ᛞ",
        'e' to "ᛂ",
        'f' to "ᚠ",
        'g' to "ᚵ",
        'h' to "ᚼ",
        'i' to "ᛁ",
        'j' to "ᛃ",
        'k' to "ᚴ",
        'l' to "ᛚ",
        'm' to "ᛘ",
        'n' to "ᚾ",
        'o' to "ᚭ",
        'p' to "ᛈ",
        'q' to "ᚴ",
        'r' to "ᚱ",
        's' to "ᛋ",
        't' to "ᛏ",
        'u' to "ᚢ",
        'v' to "ᚥ",
        'w' to "ᚥ",
        'x' to "ᛋ",
        'y' to "ᛦ",
        'z' to "ᛉ"
    )

    private val elderReverseMap = elderFutharkMap.entries.associate { it.value to it.key }
    private val youngerReverseMap = youngerFutharkMap.entries.associate { it.value to it.key }

    private val runeInfoMap = mapOf(
        "ᚠ" to RuneInfo("ᚠ", "Fehu", "wealth, livestock, prosperity"),
        "ᚢ" to RuneInfo("ᚢ", "Uruz", "strength, vitality, wild ox"),
        "ᚦ" to RuneInfo("ᚦ", "Thurisaz", "threat, protection, chaos"),
        "ᚨ" to RuneInfo("ᚨ", "Ansuz", "wisdom, communication, breath"),
        "ᚱ" to RuneInfo("ᚱ", "Raido", "journey, travel, rhythm"),
        "ᚲ" to RuneInfo("ᚲ", "Kaunan", "wisdom, knowledge, awakening"),
        "ᚷ" to RuneInfo("ᚷ", "Gebo", "gift, generosity, partnership"),
        "ᚹ" to RuneInfo("ᚹ", "Wunjo", "joy, comfort, success"),
        "ᚺ" to RuneInfo("ᚺ", "Hagalaz", "hail, disruption, transformation"),
        "ᚾ" to RuneInfo("ᚾ", "Nauthiz", "need, hardship, perseverance"),
        "ᛁ" to RuneInfo("ᛁ", "Isa", "ice, stillness, pause"),
        "ᛃ" to RuneInfo("ᛃ", "Jera", "year, cycles, harvest"),
        "ᛇ" to RuneInfo("ᛇ", "Eihwaz", "protection, endurance, resilience"),
        "ᛈ" to RuneInfo("ᛈ", "Perthro", "mystery, fate, hidden things"),
        "ᛉ" to RuneInfo("ᛉ", "Algiz", "protection, defense, awakening"),
        "ᛊ" to RuneInfo("ᛊ", "Sowilo", "sun, success, vitality"),
        "ᛏ" to RuneInfo("ᛏ", "Tiwaz", "justice, courage, victory"),
        "ᛒ" to RuneInfo("ᛒ", "Berkana", "birth, growth, fertility"),
        "ᛖ" to RuneInfo("ᛖ", "Ehwaz", "movement, partnership, trust"),
        "ᛗ" to RuneInfo("ᛗ", "Mannaz", "humanity, self, intellect"),
        "ᛚ" to RuneInfo("ᛚ", "Laguz", "water, intuition, dreams"),
        "ᛜ" to RuneInfo("ᛜ", "Ingwaz", "fertility, potential, gestation"),
        "ᛟ" to RuneInfo("ᛟ", "Othala", "inheritance, homeland, legacy"),
        "ᛞ" to RuneInfo("ᛞ", "Dagaz", "dawn, clarity, breakthrough"),
        "ᛆ" to RuneInfo("ᛆ", "Ansuz/Young", "sound, speech, fate"),
        "ᛂ" to RuneInfo("ᛂ", "Ehwaz/Young", "movement, balance"),
        "ᚴ" to RuneInfo("ᚴ", "Kaunaz/Young", "knowledge, spark"),
        "ᚼ" to RuneInfo("ᚼ", "Haglaz/Young", "storm, force"),
        "ᚥ" to RuneInfo("ᚥ", "Wunjo/Young", "joy, success"),
        "ᛘ" to RuneInfo("ᛘ", "Mannaz/Young", "humanity"),
        "ᚭ" to RuneInfo("ᚭ", "Othala/Young", "home, inheritance"),
        "ᛦ" to RuneInfo("ᛦ", "Yr/Young", "yew, mystery"),
        "ᛋ" to RuneInfo("ᛋ", "Sowilo/Young", "sun, vitality"),
        "ᚱ" to RuneInfo("ᚱ", "Raido", "journey, travel")
    )

    fun toElderFuthark(input: String): String {
        if (input.isBlank()) return ""

        return input.map { char ->
            if (char.isWhitespace()) {
                " "
            } else {
                elderFutharkMap[char.lowercaseChar()] ?: char.toString()
            }
        }.joinToString("")
    }

    fun toYoungerFuthark(input: String): String {
        if (input.isBlank()) return ""

        return input.map { char ->
            if (char.isWhitespace()) {
                " "
            } else {
                youngerFutharkMap[char.lowercaseChar()] ?: char.toString()
            }
        }.joinToString("")
    }

    fun fromFuthark(input: String): String {
        if (input.isBlank()) return ""

        return input.map { char ->
            if (char.isWhitespace()) {
                ' '
            } else {
                val rune = char.toString()
                val translated = elderReverseMap[rune] ?: youngerReverseMap[rune]
                translated?.toString() ?: rune
            }
        }.joinToString("")
    }

    fun containsFuthark(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            val rune = char.toString()
            rune in elderReverseMap || rune in youngerReverseMap || rune in runeInfoMap
        }
    }

    fun getRuneDetails(rune: String): RuneInfo? {
        return runeInfoMap[rune.trim()]
    }

    fun getTranslationStats(originalText: String, translatedText: String): Map<String, Any> {
        val normalizedOriginal = originalText.trim()
        val wordCount = if (normalizedOriginal.isEmpty()) {
            0
        } else {
            normalizedOriginal.split(Regex("\\s+")).filter { it.isNotEmpty() }.size
        }

        val runeCount = translatedText.count { char ->
            val rune = char.toString()
            rune in elderReverseMap || rune in youngerReverseMap || rune in runeInfoMap
        }

        return mapOf(
            "originalLength" to normalizedOriginal.length,
            "translatedLength" to translatedText.length,
            "runeCount" to runeCount,
            "wordCount" to wordCount
        )
    }
}
