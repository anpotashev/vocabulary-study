package ru.net.arh.vocabulary.bh.service
class MdV2Utils

fun String.escape(): String =
    replace("_", "\\_")
        .replace("*", "\\*")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("~", "\\~")
        .replace("`", "\\`")
        .replace(">", "\\>")
        .replace("#", "\\#")
        .replace("+", "\\+")
        .replace("-", "\\-")
        .replace("=", "\\=")
        .replace("|", "\\|")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace(".", "\\.")
        .replace("!", "\\!")

fun String.bold(): String ="*$this*"

fun String.hide(): String ="||$this||"

fun String.underline() = "__${this}__"

fun String.hyperlink(text: String) = "[text]($this)"