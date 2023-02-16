package ru.net.arh.vocabulary.bh.service.telegram.repository

import org.springframework.data.repository.CrudRepository
import ru.net.arh.vocabulary.bh.data.SimpleMessageData

/**
 * repository to work with simple message data
 */
interface SimpleMessageDataRepository : CrudRepository<SimpleMessageData, Long>