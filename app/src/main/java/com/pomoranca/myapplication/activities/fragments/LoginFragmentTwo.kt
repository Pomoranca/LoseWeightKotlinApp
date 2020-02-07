package com.pomoranca.myapplication.activities.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pomoranca.myapplication.R
import com.pomoranca.myapplication.SharedPref
import com.pomoranca.myapplication.adapters.LoginQuestionRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_login_two.view.*


/**
 * Login fragment that contains questions about user
 */
class LoginFragmentTwo : Fragment() {
    private lateinit var sharedPref: SharedPref
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_two, container, false)
        sharedPref = SharedPref(activity!!)

        val bundle = this.arguments
        if (bundle != null) {
            name = bundle.getString("name", "")
        }
        sharedPref.saveUserName(name)


        //init expanding recyclerview
        val recyclerView = rootView.login_two_recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.setHasFixedSize(true)
        val loginQuestionRecyclerViewAdapter = LoginQuestionRecyclerViewAdapter()
        loginQuestionRecyclerViewAdapter.populateQuestionList()
        recyclerView.adapter = loginQuestionRecyclerViewAdapter


        return rootView

    }


}



