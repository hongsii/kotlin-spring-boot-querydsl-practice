package dev.hongsii.kotlinspringbootquerydsl.entity

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import dev.hongsii.kotlinspringbootquerydsl.dto.*
import dev.hongsii.kotlinspringbootquerydsl.entity.QMember.member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataJpaTest
class MemberTest {

    @PersistenceContext
    private lateinit var em: EntityManager

    private lateinit var jpaQueryFactory: JPAQueryFactory

    @BeforeEach
    fun setUp() {
        jpaQueryFactory = JPAQueryFactory(em)
    }

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
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = em.createQuery("SELECT m FROM Member m", Member::class.java).resultList

        // then
        assertThat(members).hasSize(4).allMatch { it.id > 0 }
    }

    @Test
    fun testWhereClause() {
        // Using comma is the same as using and()
        //
        //    .where(
        //       member.name.eq("member1"),
        //       member.age.eq(10)
        //    )
        jpaQueryFactory.selectFrom(member)
            .where(
                member.name.eq("member1").and(member.age.eq(10))
            )
            .fetch()
    }

    @Test
    fun testDtoProjection_setter() {
        // given
        val teamA = Team(name = "A")
        val teamB = Team(name = "B")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(name = "member1", age = 10, team = teamA)
        val member2 = Member(name = "member2", age = 20, team = teamA)
        val member3 = Member(name = "member3", age = 30, team = teamB)
        val member4 = Member(name = "member4", age = 40, team = teamB)
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = jpaQueryFactory
            .select(Projections.bean(MemberBeanDto::class.java, member.name, member.age))
            .from(member)
            .fetch()

        // then
        assertThat(members)
            .containsExactly(
                MemberBeanDto(name = member1.name, age = member1.age),
                MemberBeanDto(name = member2.name, age = member2.age),
                MemberBeanDto(name = member3.name, age = member3.age),
                MemberBeanDto(name = member4.name, age = member4.age)
            )
    }

    @Test
    fun testDtoProjection_field() {
        // given
        val teamA = Team(name = "A")
        val teamB = Team(name = "B")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(name = "member1", age = 10, team = teamA)
        val member2 = Member(name = "member2", age = 20, team = teamA)
        val member3 = Member(name = "member3", age = 30, team = teamB)
        val member4 = Member(name = "member4", age = 40, team = teamB)
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = jpaQueryFactory
            .select(Projections.fields(MemberFieldDto::class.java, member.name, member.age))
            .from(member)
            .fetch()

        // then
        assertThat(members)
            .containsExactly(
                MemberFieldDto(name = member1.name, age = member1.age),
                MemberFieldDto(name = member2.name, age = member2.age),
                MemberFieldDto(name = member3.name, age = member3.age),
                MemberFieldDto(name = member4.name, age = member4.age)
            )
    }

    @Test
    fun testDtoProjection_constructor() {
        // given
        val teamA = Team(name = "A")
        val teamB = Team(name = "B")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(name = "member1", age = 10, team = teamA)
        val member2 = Member(name = "member2", age = 20, team = teamA)
        val member3 = Member(name = "member3", age = 30, team = teamB)
        val member4 = Member(name = "member4", age = 40, team = teamB)
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = jpaQueryFactory
            .select(Projections.constructor(MemberConstructorDto::class.java, member.name, member.age))
            .from(member)
            .fetch()

        // then
        assertThat(members)
            .containsExactly(
                MemberConstructorDto(name = member1.name, age = member1.age),
                MemberConstructorDto(name = member2.name, age = member2.age),
                MemberConstructorDto(name = member3.name, age = member3.age),
                MemberConstructorDto(name = member4.name, age = member4.age)
            )
    }

    @Test
    fun testDtoProjection_queryProjection() {
        // given
        val teamA = Team(name = "A")
        val teamB = Team(name = "B")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member(name = "member1", age = 10, team = teamA)
        val member2 = Member(name = "member2", age = 20, team = teamA)
        val member3 = Member(name = "member3", age = 30, team = teamB)
        val member4 = Member(name = "member4", age = 40, team = teamB)
        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        // when
        val members = jpaQueryFactory
            .select(QMemberQueryProjectionDto(member.name,  member.age))
            .from(member)
            .fetch()

        // then
        assertThat(members)
            .containsExactly(
                MemberQueryProjectionDto(name = member1.name, age = member1.age),
                MemberQueryProjectionDto(name = member2.name, age = member2.age),
                MemberQueryProjectionDto(name = member3.name, age = member3.age),
                MemberQueryProjectionDto(name = member4.name, age = member4.age)
            )
    }
}
