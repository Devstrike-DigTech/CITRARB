/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.ui


import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentConnectLandingBinding
import org.devstrike.app.citrarb.features.connect.data.ConnectApi
import org.devstrike.app.citrarb.features.connect.data.ConnectDao
import org.devstrike.app.citrarb.features.connect.data.models.requests.CreateOccupationRequest
import org.devstrike.app.citrarb.features.connect.repositories.ConnectRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.*
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class ConnectLanding :
    BaseFragment<ConnectViewModel, FragmentConnectLandingBinding, ConnectRepoImpl>() {

    @set:Inject
    var connectApi: ConnectApi by Delegates.notNull()

    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()

    @set:Inject
    var connectDao: ConnectDao by Delegates.notNull()

    @set:Inject
    var db: CitrarbDatabase by Delegates.notNull()

    private val connectViewModel: ConnectViewModel by activityViewModels()

    private val occupationList = mutableListOf<String>()

    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val connectIcons = intArrayOf(
            R.drawable.ic_briefcase_plain,
            R.drawable.ic_tech,
            R.drawable.ic_artisan,
            R.drawable.ic_medical,
            R.drawable.ic_money_bag,
            R.drawable.ic_education
        )

            for (i in connectIcons.indices) {
                connectTabTitle.getTabAt(i)!!.setIcon(connectIcons[i])
            }
//            //set the title to be displayed on each tab
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("All"))
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("Tech"))
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("Artisan"))
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("Medical"))
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("Business"))
//            connectTabTitle.addTab(connectTabTitle.newTab().setText("Education"))

            connectTabTitle.tabGravity = TabLayout.GRAVITY_FILL

//            customToolbar = landingScreen.toolBar() as Toolbar
//            customToolbar.title = "News"

            //val connectIcons =

            val adapter = ConnectLandingPagerAdapter(
                activity,
                childFragmentManager,
                connectTabTitle.tabCount
            )
            connectLandingViewPager.adapter = adapter
            connectLandingViewPager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    connectTabTitle
                )
            )

            //define the functionality of the tab layout
            connectTabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    connectLandingViewPager.currentItem = tab.position
                    connectTabTitle.setSelectedTabIndicatorColor(resources.getColor(R.color.custom_secondary))
                    connectTabTitle.setTabTextColors(
                        Color.BLACK,
                        resources.getColor(R.color.custom_secondary)
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    connectTabTitle.setTabTextColors(Color.WHITE, Color.BLACK)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            fabAddOccupation.setOnClickListener {
                addOccupationDialog()
            }

            subscribeToConnectsListEvent()

        }


    }

    private fun subscribeToConnectsListEvent() {
        connectViewModel.getAllConnect.observe(viewLifecycleOwner){ result ->
            lifecycleScope.launch{
                when(result){
                    is Resource.Success ->{
                        val userId = sessionManager.getCurrentUserId()
                        Common.userId = userId!!
                        hideProgressBar()
                        if (result.value!!.isEmpty()) {
                            requireContext().toast("No events!")
                        }
                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){subscribeToConnectsListEvent()}
                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }

            }

        }
    }

    private fun addOccupationDialog() {
        val dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                //dialog.window?= R.style.CustomAlertDialog
            //.Builder(requireContext(), R.style.CustomAlertDialog)

           // .create()
        val view = layoutInflater.inflate(R.layout.custom_occupation_create_dialog, null)

        val tilOccupationName =
            view.findViewById<TextInputLayout>(R.id.text_input_layout_create_occupation_name)
        val tilOccupationJobTitle =
            view.findViewById<TextInputLayout>(R.id.text_input_layout_create_occupation_job_title)
        val tilOccupationCategory =
            view.findViewById<TextInputLayout>(R.id.text_input_layout_create_occupation_category)
        val tilOccupationDescription =
            view.findViewById<TextInputLayout>(R.id.text_input_layout_create_occupation_description)
        val etOccupationName = view.findViewById<TextInputEditText>(R.id.et_create_occupation_name)
        val etOccupationJobTitle =
            view.findViewById<AutoCompleteTextView>(R.id.et_create_occupation_job_title)
        val etOccupationCategory =
            view.findViewById<AutoCompleteTextView>(R.id.create_occupation_category_drop_down)
        val etOccupationDescription =
            view.findViewById<TextInputEditText>(R.id.et_create_occupation_description)
        val etOccupationPhoneNumber =
            view.findViewById<TextInputEditText>(R.id.et_create_occupation_phone_number)
        val buttonConfirm = view.findViewById<Button>(R.id.btn_create_occupation_confirm)
        val buttonCancel = view.findViewById<Button>(R.id.btn_create_occupation_cancel)

        var occupantName = ""
        var occupationJobTitle = ""
        var occupationCategory = ""
        var occupationDescription = ""
        var occupationPhoneNumber = ""

        dialog.setContentView(view)
        getOccupationList()

        buttonConfirm.enable(false)

        val occupationCategoryArray = resources.getStringArray(R.array.occupation_categories)
        val occupationCategoryArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, occupationCategoryArray)
        etOccupationCategory.setAdapter(occupationCategoryArrayAdapter)


        val occupationJobTitleArray = occupationList
        val occupationJobTitleArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, occupationJobTitleArray)
        etOccupationJobTitle.setAdapter(occupationJobTitleArrayAdapter)


        etOccupationDescription.addTextChangedListener {
            val name = etOccupationName.text.toString().trim()
            val category = etOccupationCategory.text.toString().trim()
            val description = it.toString().trim()
            val isAboveMinimum = description.length > 25
            val phone = etOccupationPhoneNumber.text.toString().trim()
            val isPhoneComplete = phone.length == 11


            buttonConfirm.enable(
                name.isNotEmpty() && category.isNotEmpty()
                        && description.isNotEmpty() && isAboveMinimum
                        && phone.isNotEmpty() && isPhoneComplete
            )
        }

        buttonConfirm.setOnClickListener {
            occupantName = etOccupationName.text.toString().trim()
            occupationJobTitle = etOccupationJobTitle.text.toString().trim()
            occupationCategory = etOccupationCategory.text.toString().trim()
            occupationDescription = etOccupationDescription.text.toString().trim()
            occupationPhoneNumber = etOccupationPhoneNumber.text.toString().trim()

            showProgressBar()
            val createOccupationRequest = CreateOccupationRequest(
                category = if (occupationCategory == "Artisan (Service)")
                    "Service"
                else
                    occupationCategory,
                description = occupationDescription,
                jobTitle = occupationJobTitle,
                name = occupantName,
                phone = occupationPhoneNumber
            )
            addOccupation(createOccupationRequest, dialog)
            //  builder.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun getOccupationList() {
        connectViewModel.getOccupationList()
        lifecycleScope.launch {
            connectViewModel.getOccupationListState.collect{result ->
                when(result){
                    is Resource.Success ->{
                        hideProgressBar()
                        for (job in result.value!!.data.occupations)
                            occupationList.add(job)
                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){getOccupationList()}
                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }
            }
        }
    }

    private fun addOccupation(
        createOccupationRequest: CreateOccupationRequest,
        dialogBuilder: Dialog
    ) {

        connectViewModel.createOccupation(createOccupationRequest)
        lifecycleScope.launch {
            connectViewModel.createOccupationState.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        hideProgressBar()
                        requireContext().toast("Occupation Added!")
                        dialogBuilder.dismiss()
                    }
                    is Resource.Failure -> {
                        hideProgressBar()
                        handleApiError(result.error) {
                            addOccupation(
                                createOccupationRequest,
                                dialogBuilder
                            )
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }

    }


    private fun showProgressBar() {
        hideProgressBar()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgressBar() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }


    override fun getFragmentRepo() = ConnectRepoImpl(connectApi, db, connectDao, sessionManager)

    override fun getViewModel() = ConnectViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentConnectLandingBinding.inflate(inflater, container, false)

}