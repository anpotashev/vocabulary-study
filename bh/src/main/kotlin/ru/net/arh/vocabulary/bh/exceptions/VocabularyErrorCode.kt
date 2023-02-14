package ru.net.arh.vocabulary.bh.exceptions

enum class VocabularyErrorCode(val messageCode: String) {
    WORDS_NOT_PRESENT("error.words_not_present"),
    ACTIVE_DICTIONARY_NOT_SET("error.active_dictionary_not_set"),
    UNKNOWN_ERROR("error.unknown");

    override fun toString(): String {
        return messageCode
    }
}
