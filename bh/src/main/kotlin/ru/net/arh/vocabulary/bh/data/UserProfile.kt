package ru.net.arh.vocabulary.bh.data

import java.util.*
import javax.persistence.*

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
    var chatId: Long? = null,

    /**
     * User's locale
     */
    @Column(name = "locale", nullable = false)
    var locale: Locale = Locale.ENGLISH,

    /**
     * Active dictionary
     */
    @OneToOne
    @JoinColumn(name = "active_dict_id", referencedColumnName = "id")
    var activeDictionary: Dictionary? = null,

    /**
     * information needed to process a simple message update
     * @see SimpleMessageData
     */
    @OneToOne
    @JoinColumn(name = "simple_message_data_id", referencedColumnName = "id")
    var simpleMessageData: SimpleMessageData? = null
)
