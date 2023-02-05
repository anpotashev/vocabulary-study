package ru.net.arh.vocabulary.bh.service

import ru.net.arh.vocabulary.bh.data.UserProfile

/**
 * Working with user profile
 */
interface UserProfileService {

    /**
     * returns user's profile. If not exists creates default one.
     * @param chatId - user's telegram chat identifier
     * @return UserProfile - user profile
     */
    fun getUserProfile(chatId: Long): UserProfile
}