package com.example.navigationcomponentsample.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharam.githubissues.repository.model.Comments
import com.dharam.githubissues.repository.model.IssuesModel
import com.example.navigationcomponentsample.R
import com.example.navigationcomponentsample.viewmodel.CommentsListViewModel
import com.example.navigationcomponentsample.viewmodel.uiData.CommentList
import com.example.navigationcomponentsample.viewmodel.uiData.Resource
import com.example.navigationcomponentsample.views.adapter.CommentsAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.issue_detail_fragment.*
import kotlinx.android.synthetic.main.issue_detail_fragment.error_message
import kotlinx.android.synthetic.main.issue_detail_fragment.progressBar
import kotlinx.android.synthetic.main.issues_list_fragment.*
import java.net.ConnectException
import java.net.UnknownHostException


class IssueDetailFragment : Fragment() {

    private lateinit var mCommentsListViewModel: CommentsListViewModel
    private val subscriptions = CompositeDisposable()
    private lateinit var number:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issue_detail_fragment, container, false)
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            //initialize view model
            mCommentsListViewModel = ViewModelProviders.of(this)
                .get(CommentsListViewModel::class.java)
            number = IssueDetailFragmentArgs.fromBundle(arguments!!).issueNumber
            Log.e("receivedBundle", IssueDetailFragmentArgs.fromBundle(arguments!!).issueNumber)

            mCommentsListViewModel.getCommentList(number).observe(activity!!, Observer {
                when(it){
                    is Resource<Comments> ->{
                        handelCommentListResponse(it)
                    }
                }
            } )
            //retry to get comment list on click over the error message
            error_message.setOnClickListener { mCommentsListViewModel.getCommentList(number) }

    }



    //onstop function of activity lifecycle
    @Override
    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }


    private fun handelCommentListResponse(it: Resource<Comments>) {
        when(it.status){
            Resource.Status.LOADING -> showProgressBar()
            Resource.Status.SUCCESS -> showComments(it.data!!)
            Resource.Status.ERROR -> showError(it.exception!!)

        }

    }

    private fun showProgressBar() {

        progressBar.visibility = View.VISIBLE
        hideErrorView()
    }

    private fun showComments(commentData: List<Comments>) {

        //sort the comment list
        val commentList = commentData.sortedWith(compareBy { it.updated_at })
        //set the the adapter to comment list
        commentsList.adapter = CommentsAdapter(commentList)
        //set the layout manager to comment list view
        commentsList.layoutManager = LinearLayoutManager(activity)
        //hide the error view
        hideErrorView()
        hideProgressbar()

    }

    private fun showError(error: String) {
        if (error.isNotEmpty())
            error_message.text = error
        error_message.visibility = View.VISIBLE
        hideProgressbar()

    }

    //hide the error view
    private fun hideErrorView() {
        error_message.visibility = View.GONE
    }

    //hide the progressbar
    private fun hideProgressbar() {

        progressBar.visibility = View.GONE
    }

}
