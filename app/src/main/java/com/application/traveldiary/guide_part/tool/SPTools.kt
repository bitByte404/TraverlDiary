package com.application.traveldiary.guide_part.tool

import android.content.Context
import android.content.SharedPreferences
import java.lang.ref.WeakReference

//sharedPreference
class SPTools private constructor(){
    private lateinit var weakContext:WeakReference<Context>
    private val sp:SharedPreferences by lazy {
        weakContext.get()!!.getSharedPreferences(Constants.SharedFileName,Context.MODE_PRIVATE)
    }
    companion object{
        private var instance: SPTools? = null
        fun defaultTools(context:Context): SPTools {
            return instance ?: SPTools().also {
                instance = it
                it.weakContext = WeakReference(context)
            }
        }
    }
    var isFirst:Boolean = true
        set(value){
            field = value
            sp.edit().also {
                it.putBoolean(Constants.isFirstKey,value)
                it.apply()
            }
        }
        get(){
            return sp.getBoolean(Constants.isFirstKey,true)
        }
}