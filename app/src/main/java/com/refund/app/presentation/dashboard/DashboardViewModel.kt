package com.refund.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refund.app.domain.usecases.GetStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {

    val statistics = getStatisticsUseCase().asLiveData()
}
