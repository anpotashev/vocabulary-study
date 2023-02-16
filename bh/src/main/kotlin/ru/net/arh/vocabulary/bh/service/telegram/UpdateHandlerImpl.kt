package ru.net.arh.vocabulary.bh.service.telegram

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.net.arh.vocabulary.bh.exceptions.VocabularyBhException
import ru.net.arh.vocabulary.bh.exceptions.VocabularyErrorCode
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import java.io.Serializable
import java.text.MessageFormat

@Service
class UpdateHandlerImpl(
        private val messageTemplateProvider: MessageTemplateProvider,
        private val telegramCommandHandler: TelegramCommandHandler,
        private val telegramCallbackQueryHandler: TelegramCallbackQueryHandler,
        private val telegramSimpleMessageHandler: TelegramSimpleMessageHandlerImpl,
) : UpdateHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val chatId = getChatId(update) ?: return null
        val messageId = getMessageId(update)
        try {
            return when {
                update.message?.isCommand ?: false -> telegramCommandHandler.onUpdate(update)
                update.hasCallbackQuery() -> telegramCallbackQueryHandler.onUpdate(update)
                update.message?.hasText() ?: false -> telegramSimpleMessageHandler.onUpdate(update)
                else -> null
            }
        } catch (e: Throwable) {
            val errorCode = (e as? VocabularyBhException)?.errorCode ?: VocabularyErrorCode.UNKNOWN_ERROR
            val errorMessage = MessageFormat(messageTemplateProvider.getMessage(chatId, errorCode))
                .format(arrayOf(e.message?.escape()?.underline()))
            return errorMessage(chatId, errorMessage, messageId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun errorMessage(
        chatId: Long,
        errorMessage: String,
        messageId: Int?
    ) = SendMessage.builder()
        .chatId(chatId.toString())
        .text(errorMessage)
        .parseMode(ParseMode.MARKDOWN)
        .let { if (messageId != null) it.replyToMessageId(messageId) else it }
        .build() as BotApiMethod<Serializable>

    private fun getMessageId(update: Update): Int? {
        return update.message?.messageId ?: update.callbackQuery?.message?.messageId
    }

    private fun getChatId(update: Update): Long? {
        return update.message?.chatId ?: update.callbackQuery?.message?.chatId
    }

}