package br.com.nicolas.buscanome.ui.home

import android.provider.ContactsContract.Data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nicolas.buscanome.data.repository.NameRepository
import br.com.nicolas.buscanome.utils.DataEvent
import br.com.nicolas.buscanome.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NameRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    init {
        getPopularName()
    }

    fun onEvent(event: DataEvent) {
        when (event) {
            is DataEvent.Search -> {
                getName(event.name)
            }
            DataEvent.Popular -> getPopularName()
        }
    }

    private fun getName(name: String) = viewModelScope.launch {
        _state.value = DataState(loading = true)
        repository.getName(name)
            .catch {
                _state.value = DataState(
                    error =
                    "Houve um erro ao buscar os dados"
                )
            }
            .collect {
                _state.value = DataState(successNames = it, showNames = true, showPopular = false)
            }
    }

    fun getPopularName() = viewModelScope.launch {
        _state.value = DataState(loading = true)
        repository.getAllNames()
            .catch {
                _state.value = DataState(
                    error =
                    "Houve um erro ao buscar os dados"
                )
            }
            .collect {
                _state.value = DataState(successPopular = it, showPopular = true, showNames = false)
            }
    }
}