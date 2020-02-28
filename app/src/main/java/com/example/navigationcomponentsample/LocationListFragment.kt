package com.example.navigationcomponentsample

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import kotlinx.android.synthetic.main.location_list_fragment.*


class LocationListFragment : Fragment() {

    companion object {
        fun newInstance() = LocationListFragment()
    }

    private lateinit var viewModel: LocationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationListViewModel::class.java)

        list_item.setOnClickListener {

            val action = LocationListFragmentDirections.locationlisttodetail("my app default argument")
            view?.findNavController()?.navigate(action)
        }
        // TODO: Use the ViewModel
    }

}
