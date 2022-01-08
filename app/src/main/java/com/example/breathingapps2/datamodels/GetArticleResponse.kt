package com.example.breathingapps2.datamodels

data class GetArticleResponse (
    val posts : ArrayList<ArticleItemResponse>
        )

data class ArticleItemResponse(
    val fid : String,
    val title : String,
    val body : String,
    val image : String
)