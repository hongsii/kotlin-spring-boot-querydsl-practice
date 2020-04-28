package dev.hongsii.kotlinspringbootquerydsl.entity

import com.querydsl.jpa.impl.JPAQueryFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataJpaTest
class HelloTest {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Test
    fun testQClass() {
        // given
        val hello = Hello()
        entityManager.persist(hello)

        // when
        val jpaQueryFactory = JPAQueryFactory(entityManager)
        val actual = jpaQueryFactory.selectFrom(QHello.hello)
            .fetchOne()

        // then
        assertThat(actual).isEqualTo(hello)
        assertThat(actual?.id).isNotNull()
    }
}