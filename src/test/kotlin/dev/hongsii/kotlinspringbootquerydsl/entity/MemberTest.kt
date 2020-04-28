package dev.hongsii.kotlinspringbootquerydsl.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataJpaTest
class MemberTest {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Test
    fun testMember() {
        // given
        val teamA = Team(name = "A")
        val teamB = Team(name = "B")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(name = "member1", age = 10, team = teamA)
        val member2 = Member(name = "member2", age = 20, team = teamA)
        val member3 = Member(name = "member3", age = 30, team = teamB)
        val member4 = Member(name = "member4", age = 40, team = teamB)
        member1.changeTeam(teamA)
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = em.createQuery("SELECT m FROM Member m", Member::class.java).resultList

        // then
        assertThat(members).hasSize(4).allMatch { it.id > 0 }
    }
}