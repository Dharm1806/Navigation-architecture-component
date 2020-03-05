package com.example.navigationcomponentsample.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharam.githubissues.repository.model.IssuesModel
import com.example.navigationcomponentsample.R
import com.example.navigationcomponentsample.viewmodel.IssuesListViewModel
import com.example.navigationcomponentsample.viewmodel.uiData.Resource
import com.example.navigationcomponentsample.views.adapter.IssuesAdapter
import com.example.navigationcomponentsample.views.adapter.OnItemClickListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.issues_list_fragment.*


class IssuesListFragment : Fragment(), OnItemClickListener {


    private lateinit var mIssuesListViewModel: IssuesListViewModel
    private val subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issues_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //initialize view model
        mIssuesListViewModel = ViewModelProvider(this).get(IssuesListViewModel::class.java)

        mIssuesListViewModel.response().observe(activity!!, Observer {
            when(it){
                is Resource<IssuesModel> ->{
                    handleIssuesResponse(it)
                }
            }
        } )
        //try again
        error_message.setOnClickListener { mIssuesListViewModel.getIssuesList() }
    }



    /*
       on stop function of activity lifecycle
       */
    @Override
    override fun onStop() {
        super.onStop()
        //clear composit disposable
        subscriptions.clear()
    }


    /*
        handle item click listener of issue list
    */
    @Override
    override fun onItemClicked(issue: IssuesModel) {


        if (issue.comments == 0) {
            showNoCommentMessage()
        } else {

            val action =
                IssuesListFragmentDirections.issuesListToComment(issue.number.toString())
            view?.findNavController()?.navigate(action)
        }
    }



    private fun handleIssuesResponse(it: Resource<IssuesModel>) {
        when(it.status){
            Resource.Status.LOADING -> showProgressBar()
            Resource.Status.SUCCESS -> showIssuesList(it.data!!)
            Resource.Status.ERROR -> showError(it.exception!!)

        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        hideErrorView()
    }


    private fun showIssuesList(issuesData: List<IssuesModel>) {
        //sort issue list
        val issueList = issuesData.sortedWith(compareBy { it.updated_at }).reversed()
        //set adapter to issuelist recyclerview
        issueListView.adapter = IssuesAdapter(issueList, this)
        //set the layoutmanager to issuelist recyclerview
        issueListView.layoutManager = LinearLayoutManager(activity)
        //hide the errorView
        hideErrorView()
        //hide progressbar
        hideProgressbar()
    }

    //handle the error view visibility
    private fun showError(error: String) {

        if (error.isNotEmpty())
            error_message.text = error
        error_message.visibility = View.VISIBLE
        hideProgressbar()
    }

    //show the no comment message
    private fun showNoCommentMessage() {

        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity!!)


        //set message for alert dialog
        builder.setMessage(R.string.mssg_no_comment_found)
        builder.setIcon(android.R.drawable.ic_dialog_alert)


        //performing positive action
        builder.setPositiveButton(getString(R.string.ok_button)) { _, _ ->
            alertDialog?.dismiss()
        }
        // Create the AlertDialog

        alertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

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
