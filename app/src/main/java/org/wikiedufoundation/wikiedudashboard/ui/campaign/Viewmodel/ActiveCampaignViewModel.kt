package org.wikiedufoundation.wikiedudashboard.ui.campaign.Viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.wikiedufoundation.wikiedudashboard.ui.campaign.Repository.ActiveCampaignRepository

/**
 * Class extends AndroidViewModel and requires application as a parameter.
 */

class ActiveCampaignViewModel(private val activeCampaignRepository: ActiveCampaignRepository) : ViewModel() {

    /**
     *   LiveData gives us updated campaign when they change.
     */

    private  val _showMsg : MutableLiveData<String> = MutableLiveData()
    val showMsg : MutableLiveData<String> get() = _showMsg

    private val _progressbar = MutableLiveData<Boolean>()
    val progressbar: LiveData<Boolean> get() = _progressbar

    val data = activeCampaignRepository.allCampaignList


    init {
        _showMsg.value = null
        _progressbar.value = true

    }


   /**  The implementation of insert() is completely hidden from the UI.
     *  We don't want insert to block the main thread, so we're launching a new
     *  coroutine. ViewModels have a coroutine scope based on their lifecycle called
     *  viewModelScope which we can use here.
    **/


        fun fetchCampaignList(cookies: String){
        viewModelScope.launch {
            try{
                _progressbar.value = true
                activeCampaignRepository.getMutableLiveData(cookies)
                _progressbar.value = false

            }catch (e: Exception){
                _showMsg.postValue("Unable to connect to server")

            }
        }

    }

    override fun onCleared() {
        activeCampaignRepository.completableJob.cancel()
        super.onCleared()
    }



}