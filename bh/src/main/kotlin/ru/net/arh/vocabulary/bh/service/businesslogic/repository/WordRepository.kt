package ru.net.arh.vocabulary.bh.service.businesslogic.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.net.arh.vocabulary.bh.data.Word

/**
 * Word repository
 */
interface WordRepository: JpaRepository<Word, Long> {

    /**
     * Return if Dictionary contains any word
     * @param dictId - dictionary identifier
     * @return true - if any word in the dictionary exists, false - otherwise
     */
    fun existsByDictionaryId(dictId: Long): Boolean
}