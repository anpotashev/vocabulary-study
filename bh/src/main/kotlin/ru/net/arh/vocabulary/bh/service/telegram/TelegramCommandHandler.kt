package ru.net.arh.vocabulary.bh.service.telegram

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

/**
 * Main class to handle telegram command callbacks.
 * Prepares the necessary data, selects a custom command-handler and delegates processing to it
 */
interface TelegramCommandHandler {

    fun onUpdate(update: Update): BotApiMethod<Serializable>?
}