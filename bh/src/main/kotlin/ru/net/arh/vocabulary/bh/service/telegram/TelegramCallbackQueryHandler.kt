package ru.net.arh.vocabulary.bh.service.telegram

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

interface TelegramCallbackQueryHandler {
    fun onUpdate(update: Update): BotApiMethod<Serializable>?
}