package com.example.btechquiz.navigator

import android.widget.ImageButton

interface ItemQuestionsClicked {
    fun onClicked(imgABtn:ImageButton,imgBBtn:ImageButton,imgCBtn:ImageButton,imgDBtn:ImageButton,optPos:String,pos:Int)
}