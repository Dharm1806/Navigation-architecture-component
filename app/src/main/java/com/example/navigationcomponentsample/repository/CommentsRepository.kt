package com.example.navigationcomponentsample.repository
import com.dharam.githubissues.repository.model.Comments
import com.example.navigationcomponentsample.App
import io.reactivex.Observable

class CommentsRepository() {

    /*
       get comment data from comment list api or from cache by using issue number
    */
    fun getComments(number: String): Observable<List<Comments>> = App.issuesApi.getComments(number)

}
