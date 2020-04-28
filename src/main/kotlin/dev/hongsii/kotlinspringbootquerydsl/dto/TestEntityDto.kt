package dev.hongsii.kotlinspringbootquerydsl.dto

import com.querydsl.core.annotations.QueryProjection

data class MemberBeanDto(var name: String = "", var age: Int = 0)

data class MemberFieldDto(val name: String = "", val age: Int = 0)

data class MemberConstructorDto(val name: String, val age: Int)

data class MemberQueryProjectionDto @QueryProjection constructor(val name: String, val age: Int)
