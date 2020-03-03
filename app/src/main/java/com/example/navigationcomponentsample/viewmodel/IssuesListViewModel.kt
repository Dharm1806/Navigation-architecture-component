package com.example.navigationcomponentsample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dharam.githubissues.repository.model.IssuesModel
import com.example.navigationcomponentsample.repository.IssueListRepository
import com.example.navigationcomponentsample.viewmodel.uiData.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class IssuesListViewModel(application: Application) : AndroidViewModel(Application()) {

    private val subscriptions = CompositeDisposable()

    private var response: MutableLiveData<Resource<IssuesModel>> = MutableLiveData<Resource<IssuesModel>>()
    var mIssuesListRepository:IssueListRepository = IssueListRepository()

    init{
        getIssuesList()
    }


    fun  getIssuesList(){
       subscribe(mIssuesListRepository.getIssues()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .doOnSubscribe {
               response.value = Resource.loading() }
           .subscribe({
               response.value  = (Resource.success(it))
           }, {
              response.value = Resource.error(it.localizedMessage!!)
           }))
    }

    override fun onCleared() {
        subscriptions.clear()
    }

    fun response(): MutableLiveData<Resource<IssuesModel>> {
        return response
    }

    private fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }
}
