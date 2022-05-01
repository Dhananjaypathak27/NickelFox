package com.inxparticle.nickelfox.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inxparticle.nickelfox.data.model.JsonPlaceHolderItemResponse
import com.inxparticle.nickelfox.utils.DispatcherProvider
import com.inxparticle.nickelfox.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class JsonPlaceHolderEvent {
        class Success(val result: JsonPlaceHolderItemResponse) : JsonPlaceHolderEvent()
        class Failure(val errorText: String) : JsonPlaceHolderEvent()
        object Loading : JsonPlaceHolderEvent()
        object Empty : JsonPlaceHolderEvent()
    }

    private val _placeHolder = MutableStateFlow<JsonPlaceHolderEvent>(JsonPlaceHolderEvent.Empty)
    val placeHolder: StateFlow<JsonPlaceHolderEvent> = _placeHolder
    val bodyText = MutableStateFlow("")
    val titleText = MutableStateFlow("")
    val visibilityOfProgress = MutableStateFlow(true)

    fun fetchJsonPlaceHolderData(
    ) {

        viewModelScope.launch(dispatchers.io) {
            _placeHolder.value = JsonPlaceHolderEvent.Loading
            when (val placeHolderResponse = repository.getData()) {
                is Resource.Error -> {
                    visibilityOfProgress.value = false
                    _placeHolder.value =
                        JsonPlaceHolderEvent.Failure(
                            placeHolderResponse.message ?: "Error Occurred"
                        )
                }
                is Resource.Success -> {
                    visibilityOfProgress.value = false
                    if (placeHolderResponse.data != null) {
                        _placeHolder.value = JsonPlaceHolderEvent.Success(placeHolderResponse.data)
                        bodyText.value = placeHolderResponse.data.body ?: "No data available"
                        titleText.value = placeHolderResponse.data.title ?: "No data available"
                    } else {
                        _placeHolder.value = JsonPlaceHolderEvent.Failure("UnExpected Error")
                    }
                }
            }
        }
    }


}