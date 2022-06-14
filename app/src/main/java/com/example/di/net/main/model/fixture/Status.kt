package com.example.di.net.main.model.fixture

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.livescoredemo.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(

    @SerializedName("elapsed")
    val elapsedTime: Int?,

    @SerializedName("short")
    val type: StatusType
) : Parcelable

@Parcelize
enum class StatusType(@StringRes val message: Int) : Parcelable {
    TBD(R.string.time_to_be_defined),
    NS(R.string.not_started),
    @SerializedName("1H")
    H1(R.string.first_half_kick_off),
    HT(R.string.halftime),
    @SerializedName("2H")
    H2(R.string.second_half_2nd_half_started),
    ET(R.string.extra_time),
    P(R.string.penalty_in_progress),
    FT(R.string.match_finished),
    AET(R.string.match_finished_after_extra_time),
    PEN(R.string.match_finished_after_penalty),
    BT(R.string.break_time_in_extra_time),
    SUSP(R.string.match_suspended),
    INT(R.string.match_interrupted),
    PST(R.string.match_postponed),
    CANC(R.string.match_cancelled),
    ABD(R.string.match_abandoned),
    AWD(R.string.technical_loss),
    WO(R.string.walkover),
    LIVE(R.string.in_progress)
}