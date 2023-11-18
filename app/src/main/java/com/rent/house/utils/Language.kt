package com.rent.house.utils

import java.util.Locale


enum class Language(private val language: String) {
    DEFAULT("Default"), HINDI("Hindi");

    fun value(): String {
        return language
    }

    companion object {
        fun getLanguageCode(language: Language?): String {
            return when (language) {
                HINDI -> "hi"
                DEFAULT -> "df"
                else -> Locale.getDefault().language
            }
        }

        fun getLanguageFromCode(code: String?): Language {
            return when (code) {
                "df" -> DEFAULT
                "hi" -> HINDI
                else -> DEFAULT
            }
        }
    }
}
