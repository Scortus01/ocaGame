package com.example.theocagame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.theocagame.databinding.ActivityMainBinding

class BoardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var NUM_DIVISIONS = 7
    val paint = Paint()
    private var currentCirclePosition: Pair<Int, Int> = Pair(0, 0)
    private var lastedCirclePosition: Pair<Int, Int> = Pair(0, 0)

    fun setSize(size: Int, binding: ActivityMainBinding) {
        this.NUM_DIVISIONS = size
        invalidate()
        resetGame(binding)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val divisionWidth = width / NUM_DIVISIONS
        val divisionHeight = height / NUM_DIVISIONS

        // Dibuja las líneas verticales
        for (i in 0..NUM_DIVISIONS) {
            val x = i * divisionWidth
            canvas?.drawLine(x, 0f, x, height, paint)
        }

        // Dibuja las líneas horizontales
        for (i in 0..NUM_DIVISIONS) {
            val y = i * divisionHeight
            canvas?.drawLine(0f, y, width, y, paint)
        }

        // Dibuja el punto rojo en la posición actual
        paint.color = Color.RED
        val circleX = (currentCirclePosition.first + 0.5f) * divisionWidth
        val circleY = (currentCirclePosition.second + 0.5f) * divisionHeight
        canvas?.drawCircle(circleX, circleY, 20f, paint)
    }

    private fun resetGame(binding: ActivityMainBinding) {
        currentCirclePosition = Pair(0, 0)
        invalidate()
        binding.btnDice.isEnabled = true
    }

    fun moveCircleToRandomPosition(
        binding: ActivityMainBinding,
        listData: MutableList<QuestionModel>
    ) {
        val randomNum = (1..6).random()
        binding.tvDiceNumber.text = "Número: $randomNum"

        // Check if the circle is at the last row
        lastedCirclePosition = currentCirclePosition
        if (currentCirclePosition.second == NUM_DIVISIONS - 1) {
            val remainingSteps = NUM_DIVISIONS - currentCirclePosition.first - 1
            if (randomNum > remainingSteps) {
                Toast.makeText(
                    context,
                    "Debe sacar un número menor o igual a $remainingSteps",
                    Toast.LENGTH_SHORT
                ).show()
                return
            } else if (randomNum == remainingSteps) {
                Utils().defaultWinMessage(context = context, listener = object : IAlertDialogListener(){
                    override fun btnPositive() {
                        binding.btnDice.isEnabled = false
                        resetGame(binding)
                        return
                    }

                    override fun btnNeutral() {}

                    override fun btnNegative() {}

                })
            }
        }

        var desiredCol = currentCirclePosition.first + randomNum
        var desiredRow = currentCirclePosition.second
        while (desiredCol >= NUM_DIVISIONS) {
            desiredCol -= NUM_DIVISIONS
            desiredRow++
            if (desiredRow >= NUM_DIVISIONS) {
                desiredRow = 0
            }
        }
        currentCirclePosition = Pair(desiredCol, desiredRow)
        binding.tvCurrentPosition.text = "Posición: $currentCirclePosition"
        invalidate()
        if (listData.isNotEmpty()) {
            val randomPosition = (1 until listData.size).random()
            showQuestion(listData[randomPosition], binding)
        }
    }

    private fun showQuestion(question: QuestionModel, binding: ActivityMainBinding) {
        Utils().defaultQuestionMessage(
            context = context,
            listener = object : IAlertDialogListener() {
                override fun btnPositive() {
                    if (question.answerOne == question.correctAnswer){
                        showToast("Excelente trabajo")
                    }else{
                        showToast("Incorrecto")
                        returnPosition(binding = binding)
                    }
                }

                override fun btnNeutral() {
                    if (question.answerTwo == question.correctAnswer){
                        showToast("Excelente trabajo")
                    }else{
                        showToast("Incorrecto")
                        returnPosition(binding = binding)
                    }
                }

                override fun btnNegative() {
                    if (question.answerThree == question.correctAnswer){
                        showToast("Excelente trabajo")
                    }else{
                        showToast("Incorrecto")
                        returnPosition(binding = binding)
                    }
                }

            },
            question = question
        )
    }

    private fun returnPosition(binding: ActivityMainBinding){
        currentCirclePosition = lastedCirclePosition
        binding.tvCurrentPosition.text = "Posición: $currentCirclePosition"
        invalidate()
    }
    private fun showToast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}