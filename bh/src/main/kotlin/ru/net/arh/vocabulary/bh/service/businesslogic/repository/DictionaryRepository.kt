package ru.net.arh.vocabulary.bh.service.businesslogic.repository

import org.springframework.data.repository.CrudRepository
import ru.net.arh.vocabulary.bh.data.Dictionary

/**
 * Dictionary repository
 */
interface DictionaryRepository: CrudRepository<Dictionary, Long> {

    /**
     * Retrieves user dictionaries
     * @param chatId - telegram chat identifier
     * @return collection of dictionaries
     * @see ru.net.arh.vocabulary.bh.data.Dictionary
     */
    fun findAllByUserProfileChatId(chatId: Long): List<Dictionary>

}