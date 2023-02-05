package ru.net.arh.vocabulary.bh.service.repository

import org.springframework.data.repository.CrudRepository
import ru.net.arh.vocabulary.bh.data.UserProfile

interface UserProfileRepository: CrudRepository<UserProfile, Long> {

    fun findByChatId(chatId: Long): UserProfile?
}