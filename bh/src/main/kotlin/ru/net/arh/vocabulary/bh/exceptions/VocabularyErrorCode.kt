package ru.net.arh.vocabulary.bh.exceptions

enum class VocabularyErrorCode(val messageCode: String) {
    WORDS_NOT_PRESENT("error.words_not.present"),
    UNKNOWN_ERROR("error.unknown");

    override fun toString(): String {
        return messageCode
    }
}
