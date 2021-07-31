package com.kevin1008.basecore.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> loadJson(data: String?): T? {
    return Gson().fromJson(data, object : TypeToken<T>() {}.type)
}