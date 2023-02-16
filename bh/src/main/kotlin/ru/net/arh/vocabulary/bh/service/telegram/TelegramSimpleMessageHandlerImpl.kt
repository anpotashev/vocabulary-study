package ru.net.arh.vocabulary.bh.service.telegram

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.SimpleMessageHandler
import java.io.Serializable

@Service
class TelegramSimpleMessageHandlerImpl(
        private val simpleMessageHandlers: Map<String, SimpleMessageHandler>,
        private val userProfileService: UserProfileService
): TelegramSimpleMessageHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val chatId = update.message.chatId
        val messageId = update.message.messageId
        val updateId = update.updateId
        val messageText = update.message.text
        val userProfile = userProfileService.getUserProfile(chatId)
        val handlerName = userProfile.simpleMessageData?.handlerName ?: return null
        return simpleMessageHandlers.get(handlerName)
                ?.onUpdate(userProfile.simpleMessageData!!.payload, chatId, messageId, updateId, messageText) as BotApiMethod<Serializable>?
    }
}