package com.example.salamport.models

data class ListItemModel (

    val id: String = "",

    var question: String,

    var answerOne: String,
    var answerTwo:String,
    var answerThree: String,
    var answerFore: String

)
{
    override fun equals(other: Any?): Boolean {
        return (other as ListItemModel).id == id
    }
}