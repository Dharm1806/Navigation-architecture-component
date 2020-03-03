package com.example.navigationcomponentsample.viewmodel.uiData

import com.dharam.githubissues.repository.model.Comments

data class CommentList(val commentList: List<Comments>, val message: String, val error:Throwable?= null)