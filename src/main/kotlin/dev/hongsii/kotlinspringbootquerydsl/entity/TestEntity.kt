package dev.hongsii.kotlinspringbootquerydsl.entity

import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val age: Int,

    team: Team
) {

    /**
     * If you don't want to see "Cannot resolve column 'team_id'" in IntelliJ,
     * assign data source
     * or disable inspection
     * or use @Suppress("JpaDataSourceORMInspection")
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team = team
        private set

    fun changeTeam(team: Team) {
        this.team = team
        team.addMember(this)
    }

    override fun toString(): String {
        return "Member(id=$id, username='$name', age=$age)"
    }
}

@Entity
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @OneToMany(mappedBy = "team")
    private val members: MutableList<Member> = mutableListOf()
) {

    fun addMember(member: Member) {
        members.add(member)
    }

    override fun toString(): String {
        return "Team(id=$id, name='$name')"
    }
}
