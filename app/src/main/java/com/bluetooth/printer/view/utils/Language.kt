package com.bluetooth.printer.view.utils

import java.util.Locale


enum class Language(private val language: String) {
    DEFAULT("Default"),
    BANGLA("বাংলা"),
    GUJARATI("ગુજરાતી"),
    HINDI("हिंदी"),
    KANNADA("ಕನ್ನಡ"),
    MARATHI("मराठी"),
    MALAYALAM("മലയാളം"),
    ODIA("ଓଡ଼ିଆ"),
    PUNJABI("ਪੰਜਾਬੀ"),
    TAMIL("தமிழ்"),
    TELUGU("తెలుగు"),
    URDU("اردو");

    fun value(): String {
        return language
    }

    companion object {
        fun getLanguageCode(language: Language?): String {
            return when (language) {
                BANGLA -> "bn"
                GUJARATI -> "gu"
                HINDI -> "hi"
                KANNADA -> "kn"
                MALAYALAM -> "ml"
                MARATHI -> "mr"
                ODIA -> "or"
                PUNJABI -> "pa"
                TAMIL -> "ta"
                TELUGU -> "te"
                URDU -> "ur"
                DEFAULT -> "df"
                else -> Locale.getDefault().language
            }
        }

        fun getLanguageFromCode(code: String?): Language {
            return when (code) {
                "bn" -> BANGLA
                "gu" -> GUJARATI
                "hi" -> HINDI
                "kn" -> KANNADA
                "ml" -> MALAYALAM
                "mr" -> MARATHI
                "or" -> ODIA
                "pa" -> PUNJABI
                "ta" -> TAMIL
                "te" -> TELUGU
                "ur" -> URDU
                "df" -> DEFAULT
                else -> DEFAULT
            }
        }
    }

    override fun toString(): String {
        return language
    }
}
