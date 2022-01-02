package com.example.android.quiz

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedAnswerPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)

        mQuestionList = Constants.getQuestions()
        setQuestion()

        answerOne.setOnClickListener(this)
        answerTwo.setOnClickListener(this)
        answerThree.setOnClickListener(this)
        answerFour.setOnClickListener(this)
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

        setAllAnswerButtonsToDefaultColor()

        questionTitle.text = question!!.question
        progressBar.progress = mCurrentPosition
        progressBarText.text = "$mCurrentPosition" + "/" + progressBar.max
        questionImage.setImageResource(question.image)
        answerOne.text = question.answerOne
        answerTwo.text = question.answerTwo
        answerThree.text = question.answerThree
        answerFour.text = question.answerFour
    }

    private fun setAllAnswerButtonsToDefaultColor() {
        val options = ArrayList<TextView>()
        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)

        options.add(0, answerOne)
        options.add(1, answerTwo)
        options.add(2, answerThree)
        options.add(3, answerFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("@android:color/black"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }


    }

    override fun onClick(p0: View?) {

    }
}