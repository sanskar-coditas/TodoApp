package com.example.notesapp

import android.util.Log

class LoggerTodo {
    companion object {

        fun LogInfo(Info:String)
        {
            Log.i("Info of logger",Info )
        }
        fun LogDebug(Debug:String)
        {
            Log.d("Debug of logger",Debug )
        }
        fun LogVerbose(Verbose:String)
        {
            Log.v("Verbose of logger",Verbose )
        }
        fun LogWarn(Warn:String)
        {
            Log.w("Warn of logger",Warn )
        }
        fun LogError(Error:String)
        {
            Log.e("Error of logger",Error )
        }
        fun LogAssert(Assert:String)
        {
            Log.wtf("Assert of logger",Assert )
            
        }

    }
}