package com.dharam.githubissues.repository.model


/*
issue list api response model
*/
data class IssuesModel(
        val id: Long,
        val title: String,
        val number: Long,
        val body: String,
        val comments: Int,
        val updated_at: String)


/*
 comment list of issue with issue number api response model
*/
data class Comments(
        val id: Long,
        val body: String,
        val updated_at: String,
        val user: User)

/*
author of comment  response model
*/
data class User(
        val id: Long,
        val avatar_url: String,
        val login: String
)
