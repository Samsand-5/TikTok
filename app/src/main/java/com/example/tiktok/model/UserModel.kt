package com.example.tiktok.model

data class UserModel(
    var id:String="",
    var email:String="",
    var username:String="",
    var profilepic:String="",
    var followerList : MutableList<String> = mutableListOf(),
    var followingList : MutableList<String> = mutableListOf()
)
