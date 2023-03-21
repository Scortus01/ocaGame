package com.example.theocagame

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.theocagame.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var boardView: BoardView
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
    }

    private fun getData() {
        db.collection("questions").document("level").collection("easy").get()
            .addOnSuccessListener { result ->
                val listData = mutableListOf<QuestionModel>()
                result?.forEach { currentService ->
                    val document = currentService.toObject(QuestionModel::class.java)
                    listData.add(document)
                }
                drawTable()
                moveDice(listData)
            }.addOnFailureListener {
                Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun moveDice(listData: MutableList<QuestionModel>) {
        binding.btnDice.setOnClickListener {
            boardView.moveCircleToRandomPosition(binding, listData)
        }
    }

    private fun drawTable() {
        boardView = binding.boardView
        boardView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val paint = boardView.paint
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
    }
}