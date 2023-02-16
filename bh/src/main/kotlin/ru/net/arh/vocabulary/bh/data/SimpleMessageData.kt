package ru.net.arh.vocabulary.bh.data

import ru.net.arh.vocabulary.bh.service.common.JsonbMapConverter
import javax.persistence.*

/**
 * Data used by SimpleMessageHandlers
 * @see ru.net.arh.vocabulary.bh.service.telegram.TelegramSimpleMessageHandler
 * @see ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.SimpleMessageHandler
 *
 * When program needs to retrieve some text information from user, all information needed to process user answer
 * stores in object SimpleMessageData. This object set as property UserProfile.simpleMessageData and saves in DB.
 * @see UserProfile.simpleMessageData
 * When user send a text message this information retrieves from DB.
 */
@Entity
@Table(name = "simple_message_data")
data class SimpleMessageData(
        /**
         * Record identifier
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        /**
         * Bean name, used to process telegram simple message update
         * Should be a bean name of implementation SimpleMessageHandler
         * @see ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.SimpleMessageHandler
         */
        @Column(name="handler_name")
        var handlerName: String = "",

        /**
         * Any data needed to process
         */
        @Column
        @Convert(converter = JsonbMapConverter::class)
        @Suppress("JpaAttributeTypeInspection")
        var payload: Map<String, Any?> = emptyMap()
)
