package com.example.notesapp.dataclasses



data class TaskOfNote(
    val titleOfNote:String?=null,
    val description:String?=null,
    val idForNote:String?=null,
    val doneNot:String?=null,
    val noteType: String?=null,
    val noteIdp:String?=null,
    val priorityOfTask : String? = null

    )