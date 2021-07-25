package com.codingwithjks.agetominutesapp.utils

import android.content.Context
import android.widget.Toast


fun Context.showMsg(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}