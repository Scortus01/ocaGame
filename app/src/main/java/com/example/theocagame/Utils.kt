package com.example.theocagame

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

class Utils {

    fun defaultQuestionMessage(
        context: Context?,
        listener: IAlertDialogListener?,
        question: QuestionModel
    ){
        val dialog = AlertDialog.Builder(context).create()
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.question_dialog, null).apply {
            val btnPositive = this.findViewById<Button>(R.id.btn_accept)
            val btnNeutral = this.findViewById<Button>(R.id.btn_neutral)
            val btnNegative = this.findViewById<Button>(R.id.btn_cancel)
            val questionTitle = this.findViewById<TextView>(R.id.question)
            val answerOne = this.findViewById<TextView>(R.id.answerOne)
            val answerTwo = this.findViewById<TextView>(R.id.answerTwo)
            val answerThree = this.findViewById<TextView>(R.id.answerThree)
            questionTitle.text = question.question
            answerOne.text = "a: ${question.answerOne}"
            answerTwo.text = "b: ${question.answerTwo}"
            answerThree.text = "c: ${question.answerThree}"
            btnPositive.setOnClickListener { dialog.dismiss(); listener?.btnPositive() }
            btnNeutral.setOnClickListener { dialog.dismiss(); listener?.btnNeutral() }
            btnNegative.setOnClickListener { dialog.dismiss(); listener?.btnNegative() }
        }
        dialog.setView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun defaultWinMessage(
        context: Context?,
        listener: IAlertDialogListener?
    ){
        val dialog = AlertDialog.Builder(context).create()
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.question_dialog, null).apply {
            val btnPositive = this.findViewById<Button>(R.id.btn_accept)
            val btnNeutral = this.findViewById<Button>(R.id.btn_neutral)
            val btnNegative = this.findViewById<Button>(R.id.btn_cancel)
            val questionTitle = this.findViewById<TextView>(R.id.question)
            val answerOne = this.findViewById<TextView>(R.id.answerOne)
            val answerTwo = this.findViewById<TextView>(R.id.answerTwo)
            val answerThree = this.findViewById<TextView>(R.id.answerThree)
            questionTitle.text = "Felicitaciones has ganado"
            answerOne.apply {
                text = "¿Deseas iniciar una nueva partida?"
                gravity = Gravity.CENTER
            }
            answerTwo.visibility = View.GONE
            answerThree.visibility = View.GONE
            btnNegative.visibility = View.GONE
            btnNeutral.visibility = View.GONE
            btnPositive.apply {
                text = "Aceptar"
                setOnClickListener { dialog.dismiss(); listener?.btnPositive() }
            }
        }
        dialog.setView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }
}

abstract class IAlertDialogListener {
    abstract fun btnPositive()
    abstract fun btnNeutral()
    abstract fun btnNegative()
}