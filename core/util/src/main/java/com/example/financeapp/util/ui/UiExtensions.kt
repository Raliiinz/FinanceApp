package com.example.financeapp.util.ui

/**
 * Проверяет, является ли строка одиночным emoji-символом.
 */
fun String.isSingleEmoji(): Boolean = when {
    length != 2 -> false
    codePointAt(0) in 0x1F600..0x1F64F -> true
    codePointAt(0) in 0x1F300..0x1F5FF -> true
    codePointAt(0) in 0x1F680..0x1F6FF -> true
    codePointAt(0) in 0x2600..0x26FF -> true
    codePointAt(0) in 0x2700..0x27BF -> true
    codePointAt(0) in 0xFE00..0xFE0F -> true
    codePointAt(0) in 0x1F900..0x1F9FF -> true
    else -> false
}
