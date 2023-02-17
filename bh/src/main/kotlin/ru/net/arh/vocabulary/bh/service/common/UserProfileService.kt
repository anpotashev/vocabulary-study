package ru.net.arh.vocabulary.bh.service.common

import ru.net.arh.vocabulary.bh.data.SimpleMessageData
import ru.net.arh.vocabulary.bh.data.UserProfile

/**
 * Working with user profile
 */
interface UserProfileService {

    /**
     * Returns user profile. If not exists creates default one.
     * @param chatId - user telegram chat identifier
     * @return UserProfile - user profile
     */
    fun getUserProfile(chatId: Long): UserProfile

    /**
     * Saves user profile.
     * @param userProfile - user profile
     */
    fun save(userProfile: UserProfile)

    /**
     * Updates user profile
     * @param chatId - telegram chat identifier
     * @param fun0 - function changes user profile
     * @return updated user profile
     */
    fun update(chatId: Long, fun0: (userProfile: UserProfile) -> UserProfile): UserProfile

    /**
     * Clears field simpleMessageData in user profile
     * @param chatId - user telegram chat identifier
     */
    fun clearSimpleMessageData(chatId: Long)
}