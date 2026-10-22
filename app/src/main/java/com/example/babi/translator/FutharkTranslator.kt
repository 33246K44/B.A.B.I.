package com.example.babi.translator

import kotlin.text.lowercase

data class RuneInfo(
    val rune: String,
    val name: String,
    val meaning: String
)

object FutharkTranslator {

    private fun convertToScript(input: String, map: Map<Char, String>): String {
        if (input.isBlank()) return ""

        return input.map { char ->
            if (char.isWhitespace()) {
                " "
            } else {
                map[char.lowercaseChar()] ?: char.toString()
            }
        }.joinToString("")
    }

    private fun convertFromScript(input: String, reverseMap: Map<String, Char>): String {
        if (input.isBlank()) return ""

        return input.map { char ->
            if (char.isWhitespace()) {
                ' '
            } else {
                val translated = reverseMap[char.toString()]
                translated?.toString() ?: char.toString()
            }
        }.joinToString("")
    }

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

    private val hebrewMap = mapOf(
        'a' to "א",
        'b' to "ב",
        'c' to "כ",
        'd' to "ד",
        'e' to "ע",
        'f' to "פ",
        'g' to "ג",
        'h' to "ח",
        'i' to "י",
        'j' to "ג",
        'k' to "ק",
        'l' to "ל",
        'm' to "מ",
        'n' to "נ",
        'o' to "ו",
        'p' to "פ",
        'q' to "ק",
        'r' to "ר",
        's' to "ש",
        't' to "ת",
        'u' to "ו",
        'v' to "ב",
        'w' to "ו",
        'x' to "ש",
        'y' to "י",
        'z' to "ז"
    )

    private val nkoMap = mapOf(
        'a' to "ߊ",
        'b' to "ߓ",
        'c' to "ߗ",
        'd' to "ߘ",
        'e' to "ߍ",
        'f' to "ߝ",
        'g' to "ߜ",
        'h' to "ߛ",
        'i' to "ߌ",
        'j' to "ߖ",
        'k' to "ߞ",
        'l' to "ߟ",
        'm' to "ߡ",
        'n' to "ߢ",
        'o' to "ߍ",
        'p' to "ߔ",
        'q' to "߳",
        'r' to "ߙ",
        's' to "ߛ",
        't' to "ߕ",
        'u' to "ߎ",
        'v' to "ߦ",
        'w' to "ߥ",
        'x' to "ߛ",
        'y' to "ߌ",
        'z' to "ߒ"
    )

    private val hausaAjamiMap = mapOf(
        'a' to "ا",
        'b' to "ب",
        'c' to "چ",
        'd' to "د",
        'e' to "ي",
        'f' to "ف",
        'g' to "گ",
        'h' to "ح",
        'i' to "ي",
        'j' to "ج",
        'k' to "ك",
        'l' to "ل",
        'm' to "م",
        'n' to "ن",
        'o' to "و",
        'p' to "پ",
        'q' to "ق",
        'r' to "ر",
        's' to "س",
        't' to "ت",
        'u' to "و",
        'v' to "ڢ",
        'w' to "و",
        'x' to "ش",
        'y' to "ي",
        'z' to "ز"
    )

    private val geezMap = mapOf(
        'a' to "አ",
        'b' to "በ",
        'c' to "ቸ",
        'd' to "ደ",
        'e' to "ኤ",
        'f' to "ፍ",
        'g' to "ገ",
        'h' to "ሀ",
        'i' to "ኢ",
        'j' to "ጃ",
        'k' to "ከ",
        'l' to "ለ",
        'm' to "ም",
        'n' to "ነ",
        'o' to "ኦ",
        'p' to "ፐ",
        'q' to "ቀ",
        'r' to "ረ",
        's' to "ሰ",
        't' to "ተ",
        'u' to "ኡ",
        'v' to "ቨ",
        'w' to "ወ",
        'x' to "ሽ",
        'y' to "የ",
        'z' to "ዘ"
    )

    private val elderReverseMap = elderFutharkMap.entries.associate { it.value to it.key }
    private val youngerReverseMap = youngerFutharkMap.entries.associate { it.value to it.key }
    private val hebrewReverseMap = hebrewMap.entries.associate { it.value to it.key }
    private val nkoReverseMap = nkoMap.entries.associate { it.value to it.key }
    private val hausaReverseMap = hausaAjamiMap.entries.associate { it.value to it.key }
    private val geezReverseMap = geezMap.entries.associate { it.value to it.key }

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
        "ᛋ" to RuneInfo("ᛋ", "Sowilo/Young", "sun, vitality")
    )

    fun toElderFuthark(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, elderFutharkMap)
    }

    fun toYoungerFuthark(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, youngerFutharkMap)
    }

    fun toHebrew(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, hebrewMap)
    }

    fun toNko(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, nkoMap)
    }

    fun toHausaAjami(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, hausaAjamiMap)
    }

    fun toGeEz(input: String): String {
        if (input.isBlank()) return ""
        return convertToScript(input, geezMap)
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

    fun fromHebrew(input: String): String {
        if (input.isBlank()) return ""
        return convertFromScript(input, hebrewReverseMap)
    }

    fun fromNko(input: String): String {
        if (input.isBlank()) return ""
        return convertFromScript(input, nkoReverseMap)
    }

    fun fromHausaAjami(input: String): String {
        if (input.isBlank()) return ""
        return convertFromScript(input, hausaReverseMap)
    }

    fun fromGeEz(input: String): String {
        if (input.isBlank()) return ""
        return convertFromScript(input, geezReverseMap)
    }

    fun containsFuthark(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            val rune = char.toString()
            rune in elderReverseMap || rune in youngerReverseMap || rune in runeInfoMap
        }
    }

    fun containsHebrew(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            val value = char.toString()
            value in hebrewReverseMap || value in "אבגדהוזחטיכלמנסעפץצקרשת"
        }
    }

    fun containsNko(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            char.toString() in nkoReverseMap || char.toString() in "ߊߋߌߍߎߏߐߑߒߓߔߕߖߗߘߙߚߛߜߝߞߟߠߡߢߣߤߥߦߧߨߩߪ߫߬߭߮߯߰"
        }
    }

    fun containsHausaAjami(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            val value = char.toString()
            value in hausaReverseMap || value in "ابجدحخسشصضطظعغفقكلمنهويي"
        }
    }

    fun containsGeEz(input: String): Boolean {
        if (input.isBlank()) return false
        return input.any { char ->
            val value = char.toString()
            value in geezReverseMap || value in "ሀሁሂሃሄህሆለሉሊላሌልሎሏሐሑሒሓሔሕሖሗመሙሚማሜምሞሟሠሡሢሣሤሥሦሧረሩሪራሬርሮሯሰሱሲሳሴስሶሷሸሹሺሻሼሽሾሿቀቁቂቃቄቅቆቇቈ቉ቊቋቌቍቐቑቒቓቔቕቖቘ቙ቚቛቜቝበቡቢባቤብቦቧቨቩቪቫቬቭቮቯተቱቲታቴትቶቷቸቹቺቻቼችቾቿኀኁኂኃኄኅኆኇኈ኉ኊኋኌኍ኎኏ነኑኒናኔንኖኗኘኙኚኛኜኝኞኟአኡኢኣኤእኦኧከኩኪካኬክኮኯኰ኱ኲኳኴኵ኶኷ኸኹኺኻኼኽኾዀ዁ዂዃዄዅ዆዇ወዉዊዋዌውዎዏዐዑዒዓዔዕዖ዗ዘዙዚዛዜዝዞዟዠዡዢዣዤዥዦዧየዩዪያዬይዮዯደዱዲዳዴድዶዷጀጁጂጃጄጅጆጇገጉጊጋጌግጎጏጐ጑ጒጓጔጕጘጙጚጛጜጝጞጟጠጡጢጣጤጥጦጧጨጩጪጫጬጭጮጯጰጱጲጳጴጵጶጷጸጹጺጻጼጽጾጿፀፁፂፃፄፅፆፇፈፉፊፋፌፍፎፏፐፑፒፓፔፕፖፗፘፙፚ"
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
            rune in elderReverseMap || rune in youngerReverseMap || rune in runeInfoMap || rune in hebrewReverseMap || rune in nkoReverseMap || rune in hausaReverseMap || rune in geezReverseMap
        }

        return mapOf(
            "originalLength" to normalizedOriginal.length,
            "translatedLength" to translatedText.length,
            "runeCount" to runeCount,
            "wordCount" to wordCount
        )
    }
}
