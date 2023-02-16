package ru.net.arh.vocabulary.bh.service.businesslogic

import ru.net.arh.vocabulary.bh.data.Dictionary

/**
 * Dictionary management services
 */
interface DictionaryManagementService {

    /**
     * Get all dictionaries by telegram chat identifier
     * @param chatId - telegram chat identifier
     * @return collection of Dictionary
     * @see ru.net.arh.vocabulary.bh.data.Dictionary
     */
    fun getAll(chatId: Long): List<Dictionary>

    /**
     * Saves (creates or updates) dictionary
     * @param dictionary - dictionary
     * @return updated dictionary
     * @see ru.net.arh.vocabulary.bh.data.Dictionary
     */
    fun save(dictionary: Dictionary): Dictionary

    /**
     * retrieves dictionary by its id
     * @param dictId - dictionary identifier
     * @return found dictionary
     * @see ru.net.arh.vocabulary.bh.data.Dictionary
     */
    fun get(dictId: Long): Dictionary

    /**
     * Deletes dictionary
     * @param dictId - dictionary identifier
     */
    fun delete(dictId: Long)

    /**
     * Check if dictionary contains words
     * @param dictId - dictionary identifier
     * @return true if dictionary doesn't contain any word, false - otherwise
     */
    fun isEmpty(dictId: Long): Boolean
}