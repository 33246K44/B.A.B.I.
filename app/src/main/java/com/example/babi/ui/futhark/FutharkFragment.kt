package com.example.babi.ui.futhark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.babi.R
import com.example.babi.translator.FutharkTranslator
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

/**
 * FutharkFragment - Interactive script translator UI
 * Allows users to convert text to and from runic and non-Latin scripts.
 */
class FutharkFragment : Fragment() {

    private lateinit var inputText: TextInputEditText
    private lateinit var outputText: MaterialTextView
    private lateinit var runeInfoText: MaterialTextView
    private lateinit var btnToElder: MaterialButton
    private lateinit var btnToYounger: MaterialButton
    private lateinit var btnToHebrew: MaterialButton
    private lateinit var btnToNko: MaterialButton
    private lateinit var btnToHausa: MaterialButton
    private lateinit var btnToGeEz: MaterialButton
    private lateinit var btnFromScript: MaterialButton
    private lateinit var btnClear: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_futhark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputText = view.findViewById(R.id.input_text)
        outputText = view.findViewById(R.id.output_text)
        runeInfoText = view.findViewById(R.id.rune_info_text)
        btnToElder = view.findViewById(R.id.btn_to_elder)
        btnToYounger = view.findViewById(R.id.btn_to_younger)
        btnToHebrew = view.findViewById(R.id.btn_to_hebrew)
        btnToNko = view.findViewById(R.id.btn_to_nko)
        btnToHausa = view.findViewById(R.id.btn_to_hausa)
        btnToGeEz = view.findViewById(R.id.btn_to_geez)
        btnFromScript = view.findViewById(R.id.btn_from_script)
        btnClear = view.findViewById(R.id.btn_clear)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        btnToElder.setOnClickListener { translateToElderFuthark() }
        btnToYounger.setOnClickListener { translateToYoungerFuthark() }
        btnToHebrew.setOnClickListener { translateToHebrew() }
        btnToNko.setOnClickListener { translateToNko() }
        btnToHausa.setOnClickListener { translateToHausaAjami() }
        btnToGeEz.setOnClickListener { translateToGeEz() }
        btnFromScript.setOnClickListener { translateFromScript() }
        btnClear.setOnClickListener { clearAll() }
    }

    private fun translateToElderFuthark() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toElderFuthark(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateRuneInfo(result, "Elder Futhark")
        updateStats(stats)
    }

    private fun translateToYoungerFuthark() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toYoungerFuthark(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateRuneInfo(result, "Younger Futhark")
        updateStats(stats)
    }

    private fun translateToHebrew() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toHebrew(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateScriptInfo("Hebrew", result)
        updateStats(stats)
    }

    private fun translateToNko() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toNko(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateScriptInfo("N'Ko", result)
        updateStats(stats)
    }

    private fun translateToHausaAjami() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toHausaAjami(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateScriptInfo("Hausa Ajami", result)
        updateStats(stats)
    }

    private fun translateToGeEz() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter text to translate"
            runeInfoText.text = ""
            return
        }

        val result = FutharkTranslator.toGeEz(input)
        val stats = FutharkTranslator.getTranslationStats(input, result)

        outputText.text = result
        updateScriptInfo("Ge'ez", result)
        updateStats(stats)
    }

    private fun translateFromScript() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter script text to translate"
            runeInfoText.text = ""
            return
        }

        val result = when {
            FutharkTranslator.containsFuthark(input) -> FutharkTranslator.fromFuthark(input)
            FutharkTranslator.containsHebrew(input) -> FutharkTranslator.fromHebrew(input)
            FutharkTranslator.containsNko(input) -> FutharkTranslator.fromNko(input)
            FutharkTranslator.containsHausaAjami(input) -> FutharkTranslator.fromHausaAjami(input)
            FutharkTranslator.containsGeEz(input) -> FutharkTranslator.fromGeEz(input)
            else -> {
                outputText.text = "No supported script characters found in input"
                runeInfoText.text = "Please enter text containing Futhark, Hebrew, N'Ko, Hausa Ajami, or Ge'ez characters"
                return
            }
        }

        outputText.text = result
        runeInfoText.text = "Successfully converted from script to Latin text"
    }

    private fun updateRuneInfo(runes: String, futharkType: String) {
        val uniqueRunes = runes.filter { it != ' ' }.distinct()
        val infoBuilder = StringBuilder()

        infoBuilder.append("$futharkType Translation\n")
        infoBuilder.append("Runes: ${uniqueRunes.length}\n")
        infoBuilder.append("Words: ${runes.split(" ").size}\n\n")

        infoBuilder.append("Rune Meanings:\n")
        uniqueRunes.forEach { rune ->
            val runeInfo = FutharkTranslator.getRuneDetails(rune.toString())
            if (runeInfo != null) {
                infoBuilder.append("${runeInfo.rune} ${runeInfo.name}: ${runeInfo.meaning}\n")
            }
        }

        runeInfoText.text = infoBuilder.toString()
    }

    private fun updateScriptInfo(scriptName: String, text: String) {
        val wordCount = text.split(Regex("\\s+")).filter { it.isNotEmpty() }.size
        val infoBuilder = StringBuilder()

        infoBuilder.append("$scriptName Translation\n")
        infoBuilder.append("Characters: ${text.length}\n")
        infoBuilder.append("Words: $wordCount\n\n")
        infoBuilder.append("This output is a transliteration into $scriptName script.\n")
        infoBuilder.append("It is optimized for practical script conversion and readability.")

        runeInfoText.text = infoBuilder.toString()
    }

    private fun updateStats(stats: Map<String, Any>) {
        val infoBuilder = StringBuilder(runeInfoText.text)
        infoBuilder.append("\n\nStatistics:\n")
        infoBuilder.append("Original length: ${stats["originalLength"]}\n")
        infoBuilder.append("Translated length: ${stats["translatedLength"]}\n")
        infoBuilder.append("Rune count: ${stats["runeCount"]}\n")
        infoBuilder.append("Word count: ${stats["wordCount"]}\n")

        runeInfoText.text = infoBuilder.toString()
    }

    private fun clearAll() {
        inputText.text?.clear()
        outputText.text = "(Translation will appear here)"
        runeInfoText.text = ""
    }
}
