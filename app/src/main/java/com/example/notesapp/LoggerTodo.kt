package com.example.notesapp

import android.util.Log

class LoggerTodo {
    companion object {

        fun logInfo(Info:String)
        {
            Log.i("Info of logger",Info )
        }
        fun logDebug(Debug:String)
        {
            Log.d("Debug of logger",Debug )
        }
        fun logVerbose(Verbose:String)
        {
            Log.v("Verbose of logger",Verbose )
        }
        fun logWarn(Warn:String)
        {
            Log.w("Warn of logger",Warn )
        }
        fun logError(Error:String)
        {
            Log.e("Error of logger",Error )
        }
        fun logAssert(Assert:String)
        {
            Log.wtf("Assert of logger",Assert )

        }

    }
}