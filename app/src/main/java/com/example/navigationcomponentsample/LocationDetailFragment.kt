package com.example.navigationcomponentsample

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class LocationDetailFragment : Fragment() {

    companion object {
        fun newInstance() = LocationDetailFragment()
    }

    private lateinit var viewModel: LocationDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_detail_fragment, container, false)
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationDetailViewModel::class.java)

        Log.e("receivedBundle", "${LocationDetailFragmentArgs.fromBundle(arguments!!).primitiveArgument}")
        // TODO: Use the ViewModel
    }

}
