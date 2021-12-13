package com.example.tixid_get_github_profile.exception

class ApiResponseException(val errorCode: String?, val errorMessage: String?) : Exception()