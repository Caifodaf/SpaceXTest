package ru.android.spacextest.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Response

data class LaunchesModelResponse(
    @Json(name = "docs") val docs: List<LaunchesModel>,
    @Json(name = "totalPages") val totalPages: Int
)

data class LaunchesModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "links") val links: LaunchesModelLinks,
    @Json(name = "cores") val core: List<LaunchesModelCores>,
    @Json(name = "success") val successStatus: Boolean?,
    @Json(name = "date_utc") val date: String,
    @Json(name = "details") val details: String?,
    @Json(name = "crew") val crews: List<String>,
)

data class LaunchesModelLinks(
    @Json(name = "patch") val patch:LaunchesModelPatch,
)

data class LaunchesModelPatch(
    @Json(name = "small") val smallLinkImage:String?,
    @Json(name = "large") val largeLinkImage:String?,
)

data class LaunchesModelCores(
    @Json(name = "flight") val flight:String? = "0",
)

// Body request

data class BodyLaunchersModel(
    @Json(name = "query") val query: BodyQueryLaunchersModel = BodyQueryLaunchersModel(BodyDateUTCLaunchersModel()),
    @Json(name = "options") val options: BodyOptionsLaunchersModel = BodyOptionsLaunchersModel(1)
)
//Options

data class BodyOptionsLaunchersModel(
    @Json(name = "page") val page: Int,
    @Json(name = "sort") val sort: BodySortLaunchersModel = BodySortLaunchersModel()
)
data class BodySortLaunchersModel(
    @Json(name = "date_utc") val date_utc: String = "desc"
)
//Query

data class BodyQueryLaunchersModel(
    @Json(name = "date_utc") val date_utc: BodyDateUTCLaunchersModel = BodyDateUTCLaunchersModel(),
)
data class BodyDateUTCLaunchersModel(
    @Json(name = "\$gte") val gte: String = "2021-01-01T00:00:00.000Z",
    @Json(name = "\$lte") val lte: String = "2030-01-01T00:00:00.000Z"
)


