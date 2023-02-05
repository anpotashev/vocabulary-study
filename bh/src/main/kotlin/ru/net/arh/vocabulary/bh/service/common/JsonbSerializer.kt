package ru.net.arh.vocabulary.bh.service.common

import org.postgresql.util.PGobject

/**
 * Сервис преобразования объекта в объект Postgres jsonb.
 */
interface JsonbSerializer {

    fun jsonb(value: Any?): PGobject

}
