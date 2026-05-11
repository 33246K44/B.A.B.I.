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
 * FutharkFragment - Interactive Futhark translator UI
 * Allows users to convert text to/from Elder and Younger Futhark runes
 */
class FutharkFragment : Fragment() {

    private lateinit var inputText: TextInputEditText
    private lateinit var outputText: MaterialTextView
    private lateinit var runeInfoText: MaterialTextView
    private lateinit var btnToElder: MaterialButton
    private lateinit var btnToYounger: MaterialButton
    private lateinit var btnFromFuthark: MaterialButton
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

        // Initialize views
        inputText = view.findViewById(R.id.input_text)
        outputText = view.findViewById(R.id.output_text)
        runeInfoText = view.findViewById(R.id.rune_info_text)
        btnToElder = view.findViewById(R.id.btn_to_elder)
        btnToYounger = view.findViewById(R.id.btn_to_younger)
        btnFromFuthark = view.findViewById(R.id.btn_from_futhark)
        btnClear = view.findViewById(R.id.btn_clear)

        // Set up click listeners
        setupClickListeners()
    }

    private fun setupClickListeners() {
        btnToElder.setOnClickListener { translateToElderFuthark() }
        btnToYounger.setOnClickListener { translateToYoungerFuthark() }
        btnFromFuthark.setOnClickListener { translateFromFuthark() }
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

    private fun translateFromFuthark() {
        val input = inputText.text.toString().trim()
        if (input.isEmpty()) {
            outputText.text = "Please enter runes to translate"
            runeInfoText.text = ""
            return
        }

        if (!FutharkTranslator.containsFuthark(input)) {
            outputText.text = "No Futhark runes found in input"
            runeInfoText.text = "Please enter text containing Futhark runes (ᚠ ᚢ ᚦ etc.)"
            return
        }

        val result = FutharkTranslator.fromFuthark(input)
        outputText.text = result
        runeInfoText.text = "Successfully converted from Futhark runes to Latin text"
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
