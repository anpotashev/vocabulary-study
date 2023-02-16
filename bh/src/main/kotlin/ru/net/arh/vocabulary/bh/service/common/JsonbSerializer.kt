package ru.net.arh.vocabulary.bh.service.common

import org.postgresql.util.PGobject

/**
 * Service converting java-object to Postgres jsonb.
 */
interface JsonbSerializer {

    /**
     * Convert POJO to PGobject
     * @see org.postgresql.util.PGobject
     */
    fun jsonb(value: Any?): PGobject

}
