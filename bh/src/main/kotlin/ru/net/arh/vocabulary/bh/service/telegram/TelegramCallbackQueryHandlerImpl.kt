package ru.net.arh.vocabulary.bh.service.telegram

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.CallbackHandler
import java.io.Serializable

@Service
class TelegramCallbackQueryHandlerImpl(
    private val callbackHandlers: Map<String, CallbackHandler>,
    private val callbackUtils: CallbackUtils
) : TelegramCallbackQueryHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val messageId = update.callbackQuery.message.messageId
        val chatId = update.callbackQuery.message.chatId
        val updateId = update.updateId
        val callbackData = update.callbackQuery.data
        val (callbackName, callbackParams) = callbackUtils.getCallbackDataFromString(callbackData)
        return callbackHandlers.get(callbackName)
            ?.onUpdate(callbackParams, chatId, messageId, updateId) as BotApiMethod<Serializable>?
    }
}