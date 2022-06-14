package com.example.di.net.main.model.event


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EventResponse(

    @SerializedName("assist")
    val assist: Assist,

    @SerializedName("comments")
    val comments: String?,

    @SerializedName("detail")
    val detail: String,

    @SerializedName("player")
    val player: Player?,

    @SerializedName("team")
    val team: Team,

    @SerializedName("time")
    val time: Time,

    @SerializedName("type")
    val type: EventType
)

@Parcelize
enum class EventType:Parcelable {

    @SerializedName("Goal")
    GOAL,

    @SerializedName("Card")
    CARD,

    @SerializedName("Subst")
    SUBST,

    @SerializedName("Var")
    VAR
}