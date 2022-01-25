package com.example.btechquiz.model


data class QuestionModel(
    var queID:String,
    var question: String,
    var optionA: String,
    var optionB: String,
    var optionC: String,
    var optionD: String,
    var answer: String,
    var selectedAns:String,
    var status:Int
)
