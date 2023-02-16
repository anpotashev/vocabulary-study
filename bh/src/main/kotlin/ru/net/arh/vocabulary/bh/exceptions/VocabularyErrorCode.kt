package ru.net.arh.vocabulary.bh.exceptions

/**
 * Error codes for VocabularyBhException
 * @see VocabularyBhException
 */
enum class VocabularyErrorCode(val messageCode: String) {
    WORDS_NOT_PRESENT("error.words_not_present"),
    ACTIVE_DICTIONARY_NOT_SET("error.active_dictionary_not_set"),
    DICTIONARY_NOT_FOUND("error.dictionary_not_found"),
    UNKNOWN_COMMAND("error.unknown_command"),
    EMPTY_INPUT("error.empty_input"),
    UNKNOWN_ERROR("error.unknown");

    override fun toString(): String {
        return messageCode
    }
}
