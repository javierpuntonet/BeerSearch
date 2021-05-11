package es.javierserrano.beersearch.app.viewmodel.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainViewModelStatus(val status: STATUS) : Parcelable {
    enum class STATUS  { IDLE, SEARCHING, OK, ERROR }
}