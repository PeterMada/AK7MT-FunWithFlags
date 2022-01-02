package com.example.android.quiz

object Constants {

    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        val question1 = Question(
            1,
            "Country ?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Austria",
            "Australia",
            "Russia",
            1
            )
        questionList.add(question1)


        val question2 = Question(
            2,
            "Country ?",
            R.drawable.ic_flag_of_australia,
            "Argentina",
            "Austria",
            "Australia",
            "Russia",
            3
        )
        questionList.add(question2)


        val question3 = Question(
            3,
            "Country ?",
            R.drawable.ic_flag_of_germany,
            "Argentina",
            "Austria",
            "Germany",
            "Russia",
            3
        )
        questionList.add(question3)



        val question4 = Question(
            4,
            "Country ?",
            R.drawable.ic_flag_of_brazil,
            "Argentina",
            "Brazil",
            "Australia",
            "Russia",
            2
        )
        questionList.add(question4)


        val question5 = Question(
            5,
            "Country ?",
            R.drawable.ic_flag_of_denmark,
            "Argentina",
            "Fiji",
            "Russia",
            "Denmark",
            4
        )
        questionList.add(question5)

        return questionList
    }
}