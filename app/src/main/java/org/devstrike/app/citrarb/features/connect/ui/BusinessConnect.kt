/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentConnectOccupationLayoutBinding
import org.devstrike.app.citrarb.features.connect.data.ConnectApi
import org.devstrike.app.citrarb.features.connect.data.ConnectDao
import org.devstrike.app.citrarb.features.connect.data.models.response.Connect
import org.devstrike.app.citrarb.features.connect.repositories.ConnectRepoImpl
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 08/02/2023
 */
/**
 * Created by Richard Uzor  on 08/02/2023
 */

@AndroidEntryPoint
class BusinessConnect: BaseFragment<ConnectViewModel, FragmentConnectOccupationLayoutBinding, ConnectRepoImpl>() {

    @set:Inject
    var connectApi: ConnectApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    @set:Inject
    var connectDao: ConnectDao by Delegates.notNull()
    @set:Inject
    var db: CitrarbDatabase by Delegates.notNull()


    val connectViewModel: ConnectViewModel by activityViewModels()

    private lateinit var connectAdapter: ConnectAdapter

    private var shareIntent: Intent by Delegates.notNull()
    private var shareMessage: String by Delegates.notNull()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectAdapter = ConnectAdapter(requireContext())
        subscribeToAllConnectEvent()

        binding.ivSearchAllConnect.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchConnect(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchConnect(it)
                }
                return true
            }

        })

    }

    private fun subscribeToAllConnectEvent() {
        lifecycleScope.launch {
            val query = connectViewModel.searchQuery
            connectViewModel.savedConnectsList.collect { result ->
                binding.connectShimmerLayout.visible(false)
                val businessConnect = mutableListOf<Connect>()
                for (connect in result){
                    if (connect.category == "Business"){
                        businessConnect.add(connect)
                    }
                }
                if (businessConnect.isEmpty()){
                    requireContext().toast("No Connects")
                }else{
                    connectAdapter.connects = businessConnect.filter { connect ->
                        connect.name.contains(query, false) || connect.jobTitle.contains(
                            query,
                            false
                        ) || connect.description.contains(query)
                    }.toMutableList()

                    subscribeToAllEventsUi()
                }

            }
        }


    }

    private fun subscribeToAllEventsUi() {

        binding.rvAllConnect.visible(true)
        val allConnectListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvAllConnect.apply {
            adapter = connectAdapter
            layoutManager = allConnectListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), allConnectListLayoutManager.orientation
                )
            )
        }

        connectAdapter.createOnCallClickListener { connect ->
            callConnect(connect)

        }

        connectAdapter.createOnMessageClickListener { connect ->
            messageConnect(connect)

        }

        connectAdapter.createOnShareClickListener { connect ->
            shareConnect(connect)
        }

        connectAdapter.createOnItemClickListener { connect ->
            displayDetailsDialog(connect)

        }

    }

    @SuppressLint("MissingInflatedId")
    private fun displayDetailsDialog(connect: Connect) {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_occupation_dialog, null)


        val occupantName = view.findViewById<TextView>(R.id.occupation_dialog_name)
        val occupantJobTitle = view.findViewById<TextView>(R.id.occupation_dialog_occupation)
        val occupantLocation = view.findViewById<TextView>(R.id.occupation_dialog_location)
        val occupationDescription = view.findViewById<TextView>(R.id.occupation_dialog_description)
        val messageOccupant = view.findViewById<ImageView>(R.id.occupation_dialog_message_iv)
        val callOccupant = view.findViewById<ImageView>(R.id.occupation_dialog_call_iv)
        val shareOccupant = view.findViewById<ImageView>(R.id.occupation_dialog_share_iv)
        val buttonOkay = view.findViewById<MaterialButton>(R.id.occupation_dialog_okay_button)

        builder.setView(view)

        occupantName.text = connect.name
        occupantJobTitle.text = connect.jobTitle
        occupationDescription.text = connect.description
        occupantLocation.text = connect.category

        messageOccupant.setOnClickListener {
            messageConnect(connect)
        }

        callOccupant.setOnClickListener {
            callConnect(connect)
        }

        shareOccupant.setOnClickListener {
            shareConnect(connect)
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()

        buttonOkay.setOnClickListener {
            builder.dismiss()
        }

    }

    private fun callConnect(connect: Connect) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        //dialIntent.data = Uri.fromParts("tel",phoneNumber,null)
        dialIntent.data = Uri.fromParts("tel", connect.phone, null)
        startActivity(dialIntent)

    }

    private fun messageConnect(connect: Connect) {
        val pm = requireContext().packageManager
        val waIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://api.whatsapp.com/send?phone=" + connect.phone)
        )
        val info = pm.queryIntentActivities(waIntent, 0)
        if (!info.isEmpty()) {
            startActivity(waIntent)
        } else {
            requireContext().toast("WhatsApp not Installed")
        }
    }

    private fun shareConnect(connect: Connect) {
        shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, connect.jobTitle)
        shareMessage =
            "\nCheck out this ${connect.jobTitle} \n${connect.name} \n${connect.phone} \n${connect.description}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        requireContext().startActivity(Intent.createChooser(shareIntent, "Share via: "))

    }

    private fun searchConnect(query: String) = lifecycleScope.launch {
        connectViewModel.searchQuery = query
        connectAdapter.connects = connectViewModel.savedConnectsList.first().filter {
            it.name.contains(query, true) || it.jobTitle.contains(query, true) || it.description.contains(
                query,
                true
            )
        }.toMutableList()
    }

    override fun getFragmentRepo() = ConnectRepoImpl(connectApi, db, connectDao, sessionManager)

    override fun getViewModel() = ConnectViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentConnectOccupationLayoutBinding.inflate(inflater, container, false)
}