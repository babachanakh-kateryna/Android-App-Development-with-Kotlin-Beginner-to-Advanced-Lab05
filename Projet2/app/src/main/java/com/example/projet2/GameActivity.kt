package com.example.projet2

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random


class GameActivity : AppCompatActivity() {
    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTime: TextView

    lateinit var textQuestion: TextView
    lateinit var editText: EditText

    lateinit var buttonSubmit: Button
    lateinit var buttonNext: Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer: CountDownTimer
    private val startTimeinMili: Long = 20000
    var timeLeft :Long = startTimeinMili

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textScore = findViewById(R.id.Score)
        textLife = findViewById(R.id.life)
        textTime = findViewById(R.id.time)

        textQuestion = findViewById(R.id.textViewQuestion)
        editText = findViewById(R.id.editTextAnswer)
        buttonNext = findViewById(R.id.btnNext)
        buttonSubmit = findViewById(R.id.btnOk)

        gameContinue()
        buttonSubmit.setOnClickListener{
            val input = editText.text.toString()
            if (input == ""){
                Toast.makeText(applicationContext, "Please write answer or click the next button", Toast.LENGTH_LONG).show()
            }else{
                pauseTimer()
                val answerUser = input.toInt()
                if (answerUser == correctAnswer) {
                    userScore += 10
                    textQuestion.text = "Congratulation! Your answer is correct"
                    textScore.text = userScore.toString()
                }else{
                    userLife--
                    textQuestion.text = "Sorry, your answer is wrong"
                    textLife.text = userLife.toString()

                }
            }
        }

        buttonNext.setOnClickListener{
            pauseTimer()
            resetTimer()
            gameContinue()
            editText.setText("")

            if (userLife == 0) {
                Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score", userScore)
                startActivity(intent)
                finish()
            }

        }

    }

    fun gameContinue(){
        val number1 = Random.nextInt(0, 100);
        val number2 = Random.nextInt(0, 100);

        textQuestion.text = "$number1 + $number2"
        correctAnswer = number1 + number2
        startTime()
    }

    fun startTime (){
        timer= object : CountDownTimer (timeLeft, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Time's up!"
            }
        }.start()
    }

    fun pauseTimer(){
        timer.cancel()
    }
    fun resetTimer(){
        timeLeft = startTimeinMili
        updateText()
    }
    fun updateText(){
        val remainingTime:Int  = (timeLeft / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }
}