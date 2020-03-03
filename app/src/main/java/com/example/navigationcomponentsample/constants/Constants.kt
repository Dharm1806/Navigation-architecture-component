package com.example.navigationcomponentsample.constants

import java.text.SimpleDateFormat



object Constants {
     //base url for apis
     const val BASE_URL = "https://api.github.com/"
     //endpoints for apis
     const val ISSUELIST_END_POINTS ="repos/firebase/firebase-ios-sdk/issues"
     const val COMMENT_LIST_END_POINTS ="repos/firebase/firebase-ios-sdk/issues/{number}/comments"

     const val EXTRA_NUMBER = "number"
     const val ERROR_MESSAGE = "An error occurred"

     const val CACHE_TIME_IN_HOURS:Int = 24
     private const val SERVER_DATE_FORMAT  ="yyyy-MM-dd'T'HH:mm:ss'Z'"
     private const val APP_DATE_FORMAT = "dd-MM-yyyy HH:mm"


     //parse server date to app format

     fun  parseDate(date:String):String{
          val parser = SimpleDateFormat(SERVER_DATE_FORMAT)
          val formatter = SimpleDateFormat(APP_DATE_FORMAT)
          return  formatter.format(parser.parse(date))
     }

     /* modify issue body with maximum 140 chars and add ... */
     fun getModifiedIssueBody( issueBody:String):String{
          if (issueBody.length>=140) return issueBody.chunked(140)[0]+"..."
          return issueBody
     }

}