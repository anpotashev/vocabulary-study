package ru.net.arh.vocabulary.bh.service.common

import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.data.UserProfile
import ru.net.arh.vocabulary.bh.service.common.repository.UserProfileRepository

@Service
class UserProfileServiceImpl(
    private val userProfileRepository: UserProfileRepository
) : UserProfileService {
    override fun getUserProfile(chatId: Long): UserProfile {
        return userProfileRepository.findByChatId(chatId)
            ?: userProfileRepository.save(UserProfile(chatId = chatId))
    }

    override fun save(userProfile: UserProfile) {
        userProfileRepository.save(userProfile)
    }

    override fun clearSimpleMessageData(chatId: Long) {
        val userProfile = getUserProfile(chatId)
        userProfile.simpleMessageData = null
        save(userProfile)
    }
}