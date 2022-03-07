package com.example.notesapp.dataclasses

import android.content.Context

data class DataAll(
    val titleofnote:String?=null,
    val discription:String?=null,
    val idForNote:String?=null,
    val doneNot:String?=null,
    val noteType: String?=null,
    val noteidp:String?=null,
    val context: Context?=null

)