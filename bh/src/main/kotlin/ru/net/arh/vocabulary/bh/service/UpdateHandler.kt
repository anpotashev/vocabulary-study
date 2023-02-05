package ru.net.arh.vocabulary.bh.service

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

interface UpdateHandler {
    fun onUpdate(update: Update): BotApiMethod<Serializable>?

}
