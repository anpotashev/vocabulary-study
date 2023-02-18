package ru.net.arh.vocabulary.bh

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = [VocabularyApplicationTests.Initializer::class])
class VocabularyApplicationTests {

    companion object {

        const val DB_NAME = "testdb"
        const val DB_USER = "testdb-user"
        const val DB_PASS = "testdb-password"

        @Container
        val pgContainer = PostgreSQLContainer<Nothing>(
                DockerImageName.parse("postgres:14")
        )
                .apply {
                    withDatabaseName(DB_NAME)
                    withUsername(DB_USER)
                    withPassword(DB_PASS)
                }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:tc:postgresql:14:///vocabulary",
                    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @Test
    fun contextLoads() {
    }

}
