package com.example.navigationcomponentsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dharam.githubissues.repository.model.Comments
import com.example.navigationcomponentsample.repository.CommentsRepository
import com.example.navigationcomponentsample.viewmodel.uiData.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CommentsListViewModel (application: Application) : AndroidViewModel(Application()){

    private val subscriptions = CompositeDisposable()

    private var response: MutableLiveData<Resource<Comments>> = MutableLiveData()
    var mCommentsRepository: CommentsRepository = CommentsRepository()


    fun  getCommentList(number:String):MutableLiveData<Resource<Comments>>{
        subscribe(mCommentsRepository.getComments(number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                response.value = Resource.loading() }
            .subscribe({
                response.value  = (Resource.success(it))
            }, {
                response.value = Resource.error(it.localizedMessage!!)
            }))
        return response
    }

    override fun onCleared() {
        subscriptions.clear()
    }

    private fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }
}
