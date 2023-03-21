package com.example.theocagame

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    val question: String = "",
    val answerOne: String = "",
    val answerTwo: String = "",
    val answerThree: String = "",
    val correctAnswer: String = ""
) : Parcelable
