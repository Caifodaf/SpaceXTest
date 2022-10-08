package ru.android.spacextest.models

import com.squareup.moshi.Json

data class CrewsModel (
    @Json(name = "id") val id:String,
    @Json(name = "name") val name:String,
    @Json(name = "agency") val agency:String,
    @Json(name = "image") val linkImage:String?,
    @Json(name = "wikipedia") val wikipediaLink:String?,
    @Json(name = "launches") val launches:List<String>,
    @Json(name = "status") val status:String,
)