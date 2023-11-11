package com.book.gaadi.utils

import java.util.Locale


enum class Language(private val language: String) {
    DEFAULT("Default"), ENGLISH("English"), ODIA("Odia"), TAMIL("Tamil"),
    KANNADA("Kannada"), TELUGU("Telugu"), HINDI("Hindi");

    fun value(): String {
        return language
    }

    companion object {
        fun getLanguageCode(language: Language?): String {
            return when (language) {
                ENGLISH -> "en"
                ODIA -> "or"
                TAMIL -> "ta"
                KANNADA -> "kn"
                HINDI -> "hi"
                TELUGU -> "te"
                DEFAULT -> "df"
                else -> Locale.getDefault().language
            }
        }

        fun getLanguageFromCode(code: String?): Language {
            return when (code) {
                "df" -> DEFAULT
                "or" -> ODIA
                "en" -> ENGLISH
                "ta" -> TAMIL
                "kn" -> KANNADA
                "hi" -> HINDI
                "te" -> TELUGU
                else -> DEFAULT
            }
        }
    }
}
