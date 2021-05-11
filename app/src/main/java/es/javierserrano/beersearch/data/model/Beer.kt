package es.javierserrano.beersearch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    @SerializedName("name") val Name : String,
    @SerializedName("description") val Description: String?,
    @SerializedName("image_url") val ImageURL: String?,
    @SerializedName("abv") val Degrees : Float?
) : Parcelable