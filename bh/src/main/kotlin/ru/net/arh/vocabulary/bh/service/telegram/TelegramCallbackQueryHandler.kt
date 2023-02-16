package ru.net.arh.vocabulary.bh.service.telegram

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

/**
 * Main class to handle telegram callback updates.
 * Prepares the necessary data, selects a custom callback-handler and delegates processing to it
 */
interface TelegramCallbackQueryHandler {

    fun onUpdate(update: Update): BotApiMethod<Serializable>?
}