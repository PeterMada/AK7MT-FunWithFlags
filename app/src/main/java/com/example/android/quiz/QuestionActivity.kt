package com.example.android.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

const val BASE_URL = "https://restcountries.com/v2/"
const val NUMBER_OF_QUESTIONS = 5

class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedAnswerPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val questionListBody = intent.getStringExtra("allQuestions")
        val gson = Gson()
        val collectionType: Type = object : TypeToken<ArrayList<CountriesItem?>?>() {}.type
        val deserialzieIt = gson.fromJson<ArrayList<CountriesItem>>(questionListBody, collectionType)

        mQuestionList = makeDataUsable(deserialzieIt)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)
        val buttonSubmit = findViewById<Button>(R.id.question_submit)

        setQuestion()

        answerOne.setOnClickListener(this)
        answerTwo.setOnClickListener(this)
        answerThree.setOnClickListener(this)
        answerFour.setOnClickListener(this)
        buttonSubmit.setOnClickListener(this)
    }


    override fun onBackPressed(){
        val questionList = intent.getSerializableExtra("allQuestions")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Androidly Alert")
        builder.setMessage("We have a message")
        builder.setPositiveButton(android.R.string.yes) { _,_ ->
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.USER_NAME, mUserName)
            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
            intent.putExtra("allQuestions", questionList)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton(android.R.string.no) { _,_ ->

        }
        builder.show()

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val question = mQuestionList!![mCurrentPosition - 1]

        val questionTitle = findViewById<TextView>(R.id.question_title)
        val progressBar = findViewById<ProgressBar>(R.id.question_progressBar)
        val progressBarText = findViewById<TextView>(R.id.question_progress)
        val questionImage = findViewById<ImageView>(R.id.question_img)

        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)
        val buttonSubmit = findViewById<Button>(R.id.question_submit)

        setAllAnswerButtonsToDefaultColor()

        if(mCurrentPosition == mQuestionList!!.size) {
            buttonSubmit.text = "FINISH"
        } else {
            buttonSubmit.text = "SUBMIT"
        }

        questionTitle.text = question!!.question
        progressBar.progress = mCurrentPosition
        progressBarText.text = "$mCurrentPosition" + "/" + progressBar.max
        //questionImage.setImageResource(question.image)
        Picasso.get().load(question.image).into(questionImage)

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
            option.setTextColor(Color.parseColor("#000000"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }


    }

    override fun onClick(p0: View?) {
        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)
        val buttonSubmit = findViewById<Button>(R.id.question_submit)

        when(p0?.id) {
            R.id.question_answer_one -> {
                setSelectedAnswer(answerOne, 1)
            }
            R.id.question_answer_two -> {
                setSelectedAnswer(answerTwo, 2)
            }
            R.id.question_answer_three -> {
                setSelectedAnswer(answerThree, 3)
            }
            R.id.question_answer_four -> {
                setSelectedAnswer(answerFour, 4)
            }
            R.id.question_submit -> {
                if(mSelectedAnswerPosition == 0) {
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        } else -> {
                            val questionList = intent.getSerializableExtra("allQuestions")
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            intent.putExtra("allQuestions", questionList)
                            startActivity(intent)
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)

                    if(question!!.correctAnswer != mSelectedAnswerPosition) {
                        answerView(mSelectedAnswerPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size) {
                        buttonSubmit.text = "FINISH"
                    } else {
                        buttonSubmit.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedAnswerPosition = 0
                }
            }
        }
    }

    private fun setSelectedAnswer (tv: TextView, selectAnswerNum: Int) {
        setAllAnswerButtonsToDefaultColor()
        mSelectedAnswerPosition = selectAnswerNum

        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        val answerOne = findViewById<TextView>(R.id.question_answer_one)
        val answerTwo = findViewById<TextView>(R.id.question_answer_two)
        val answerThree = findViewById<TextView>(R.id.question_answer_three)
        val answerFour = findViewById<TextView>(R.id.question_answer_four)


        when(answer) {
            1 -> {
                answerOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                answerTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                answerThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                answerFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }


    private fun makeDataUsable(responseBody: List<CountriesItem>): ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val alreadyUsedQuestion = ArrayList<Int?>()

        for (i in 1..NUMBER_OF_QUESTIONS) {
            val correctAnswerPosition = generateRandom(1, 4, ArrayList<Int?>())
            val correctAnswer = generateRandom(0, responseBody.size - 2, alreadyUsedQuestion)
            alreadyUsedQuestion.add(correctAnswer)

            val allAnswersInQuestion = ArrayList<Int>()

            val alreadyUsedAnswersInOneQuestion = ArrayList<Int?>()
            alreadyUsedAnswersInOneQuestion.add(correctAnswerPosition)

            for (k in 1..4) {
                print(k)
                val randomAnswer = generateRandom(0, responseBody.size - 2, alreadyUsedAnswersInOneQuestion)
                if (k == correctAnswerPosition) {
                    allAnswersInQuestion.add(correctAnswer)
                } else {
                    allAnswersInQuestion.add(randomAnswer)
                    alreadyUsedAnswersInOneQuestion.add(randomAnswer)
                }
            }


            val question = Question(
                i,
                "Country ?",
                responseBody[correctAnswer].flags.png,
                responseBody[allAnswersInQuestion[0]].name,
                responseBody[allAnswersInQuestion[1]].name,
                responseBody[allAnswersInQuestion[2]].name,
                responseBody[allAnswersInQuestion[3]].name,
                correctAnswerPosition
            )
            questionList.add(question)
        }

        return questionList
    }

    fun generateRandom(start: Int, end: Int, excludeRows: ArrayList<Int?>): Int {
        val rand = Random()
        val range = end - start + 1
        var random: Int = rand.nextInt(range) + 1
        while (excludeRows.contains(random)) {
            random = rand.nextInt(range) + 1
        }
        return random
    }
}