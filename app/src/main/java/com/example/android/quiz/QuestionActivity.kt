package com.example.android.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {
    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedAnswerPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        mQuestionList = Constants.getQuestions()
        setQuestion()

    }

    private fun setQuestion() {
        mCurrentPosition = 1
        val question = mQuestionList!![mCurrentPosition - 1]

        val questionTitle = findViewById<TextView>(R.id.question_title)
        val progressBar = findViewById<ProgressBar>(R.id.question_progressBar)
        val progressBarText = findViewById<TextView>(R.id.question_progress)
        val questionImage = findViewById<ImageView>(R.id.question_img)

        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)

        questionTitle.text = question!!.question
        progressBar.progress = mCurrentPosition
        progressBarText.text = "$mCurrentPosition" + "/" + progressBar.max
        questionImage.setImageResource(question.image)
        answerOne.text = question.answerOne
        answerTwo.text = question.answerTwo
        answerThree.text = question.answerThree
        answerFour.text = question.answerFour
    }
}