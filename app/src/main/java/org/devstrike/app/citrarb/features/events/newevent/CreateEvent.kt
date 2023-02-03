/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.newevent

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentCreateEventBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.EventsDao
import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsLandingDirections
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.convertISODateToMillis
import org.devstrike.app.citrarb.utils.toast
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class CreateEvent : BaseFragment<EventsViewModel, FragmentCreateEventBinding, EventsRepoImpl>() {

    @set:Inject
    var eventsApi: EventsApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    @set:Inject
    var friendsDao: FriendsDao by Delegates.notNull()
    @set:Inject
    var eventsDao: EventsDao by Delegates.notNull()
    @set:Inject
    var db: CitrarbDatabase by Delegates.notNull()


    val eventsViewModel: EventsViewModel by activityViewModels()

    lateinit var eventName: String
    lateinit var eventDescription: String
    lateinit var eventLocation: String
    var eventDate: Long = 0

    private lateinit var date_time: String
    var mYear = 0
    var mMonth = 0
    var mDay = 0

    var mHour = 0
    var mMinute = 0

    private lateinit var mFusedLocationClient: FusedLocationProviderClient


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Request permissions
        Dexter.withActivity(requireActivity()) //Dexter makes runtime permission easier to implement
            .withPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    requireContext().toast("Accept Permission")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }
            }).check()
        //Request permissions
        Dexter.withActivity(requireActivity()) //Dexter makes runtime permission easier to implement
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    requireContext().toast("Accept Permission")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }
            }).check()




        with(binding){

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            etCreateEventTime.setOnClickListener {
                pickEventDate()
            }
            iconLocation.setOnClickListener {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result


                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val list: List<Address> =
                        geocoder.getFromLocation(location!!.latitude, location.longitude, 1)

                    //mUsageLocality = "Locality\n${list[0].locality}"
                    val currentAddress = list[0].getAddressLine(0)
                    etCreateEventLocation.setText(currentAddress)
                }
            }
            createEventCancelBtn.setOnClickListener {
                val navBackToEvents = EventsLandingDirections.actionEventsLandingToAppMenu()
                findNavController().navigate(navBackToEvents)
            }
            createEventNextBtn.setOnClickListener {
            eventName = etCreateEventName.text.toString().trim()
            eventDescription = etCreateEventDescription.text.toString().trim()
            eventLocation = etCreateEventLocation.text.toString().trim()
            //eventDate = etCreateEventTime.text.toString().trim()

                performValidation()
            }
        }

    }

    private fun pickEventDate() {
        // Get Current Date
        // Get Current Date
        val c: Calendar = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                date_time = "$year-${(monthOfYear + 1)}-$dayOfMonth"
                //*************Call Time Picker Here ********************
                tiemPicker()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun tiemPicker() {
        // Get Current Time
        // Get Current Time
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]

        // Launch Time Picker Dialog

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(requireContext(),
            OnTimeSetListener { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                binding.etCreateEventTime.setText("$date_time by $hourOfDay:$minute")
                 val iSODate= "${date_time}T$hourOfDay:$minute:00.000Z"
                eventDate = convertISODateToMillis(iSODate)
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }

    private fun performValidation() {

        with(binding){
            if (eventName.isEmpty()){
                textInputLayoutCreateEventName.error = "Required"
                etCreateEventName.requestFocus()
            }
            if (eventDescription.isEmpty()){
                textInputLayoutCreateEventDescription.error = "Required"
                etCreateEventDescription.requestFocus()
            }
            if (eventLocation.isEmpty()){
                textInputLayoutCreateEventLocation.error = "Required"
                etCreateEventLocation.requestFocus()
            }
            if (etCreateEventTime.text.isNullOrEmpty()){
                textInputLayoutCreateEventTime.error = "Required"
                etCreateEventTime.requestFocus()
            }
            else{
                val navToEventHosts = CreateEventDirections.actionCreateEventToCreateEventHosts(eventName, eventDescription, eventLocation, eventDate)
                findNavController().navigate(navToEventHosts)
            }
        }

    }

    override fun getFragmentRepo() = EventsRepoImpl(eventsApi, db, friendsDao, eventsDao, sessionManager)

    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateEventBinding.inflate(inflater, container, false)

}