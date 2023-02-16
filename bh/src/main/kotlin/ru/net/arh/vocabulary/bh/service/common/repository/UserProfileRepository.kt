package ru.net.arh.vocabulary.bh.service.common.repository

import org.springframework.data.repository.CrudRepository
import ru.net.arh.vocabulary.bh.data.UserProfile

/**
 * User profile repository
 */
interface UserProfileRepository : CrudRepository<UserProfile, Long> {

    /**
     * Retrieves userProfile by telegram chat identifier
     * @param chatId - telegram chat identifier
     * @return user profile if it exists, null - if not exists
     * @see ru.net.arh.vocabulary.bh.data.UserProfile
     */
    fun findByChatId(chatId: Long): UserProfile?
}