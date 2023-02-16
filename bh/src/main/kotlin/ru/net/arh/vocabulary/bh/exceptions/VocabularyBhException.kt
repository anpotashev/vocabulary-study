package ru.net.arh.vocabulary.bh.exceptions

/**
 * Exception class for business-logic errors
 */
class VocabularyBhException(
    val errorCode: VocabularyErrorCode,
    message: String = ""
) : RuntimeException(message)