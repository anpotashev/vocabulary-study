package ru.net.arh.vocabulary.bh.service.common

import org.springframework.stereotype.Service
import ru.net.arh.vocabulary.bh.data.SimpleMessageData
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

    override fun update(chatId: Long, fun0: (userProfile: UserProfile) -> UserProfile): UserProfile {
        val userProfile = getUserProfile(chatId)
            .let { fun0.invoke(it) }
        save(userProfile)
        return userProfile
    }

    override fun clearSimpleMessageData(chatId: Long) {
        update(chatId, { it.apply { it.simpleMessageData = null }})
    }
}