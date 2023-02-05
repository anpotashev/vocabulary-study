package ru.net.arh.vocabulary.bh.exceptions

class VocabularyBhException(
    val errorCode: VocabularyErrorCode,
    message: String = ""
) : RuntimeException(message)