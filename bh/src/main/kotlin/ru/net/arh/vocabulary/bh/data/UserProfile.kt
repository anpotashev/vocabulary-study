package ru.net.arh.vocabulary.bh.data

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_profile")
/**
 * User's profile
 */
class UserProfile(
    /**
     * User's telegram chat identifier
     */
    @Column(name = "chat_id", nullable = false)
    @Id
    var chatId: Long = 0,

    /**
     * User's locale
     */
    @Column(name = "locale", nullable = false)
    var locale: Locale = Locale.ENGLISH
)
