package com.example.android.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val questionList = Constants.getQuestions()

        val currentQuestion = 1
        val question: Question? = questionList[currentQuestion - 1]

        val questionTitle = findViewById<TextView>(R.id.question_title)
        val progressBar = findViewById<ProgressBar>(R.id.question_progressBar)
        val progressBarText = findViewById<TextView>(R.id.question_progress)
        val questionImage = findViewById<ImageView>(R.id.question_img)

        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)

        questionTitle.text = question!!.question
        progressBar.progress = currentQuestion
        progressBarText.text = "$currentQuestion" + "/" + progressBar.max
        questionImage.setImageResource(question.image)
        answerOne.text = question.answerOne
        answerTwo.text = question.answerTwo
        answerThree.text = question.answerThree
        answerFour.text = question.answerFour


    }
}