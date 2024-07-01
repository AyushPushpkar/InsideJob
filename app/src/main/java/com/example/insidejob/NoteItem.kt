package com.example.insidejob

import android.util.Log

data class NoteItem(val title : String, val des : String, var noteId : String ){

    constructor(): this("","" , "" )

}
