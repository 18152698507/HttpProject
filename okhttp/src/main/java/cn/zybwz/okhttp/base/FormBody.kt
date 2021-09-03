package cn.zybwz.okhttp.base

import java.lang.StringBuilder

class FormBody {
    private val bodyMap:LinkedHashMap<String,String> = linkedMapOf()

    fun addParam(key:String,value:String){
        bodyMap[key]=value
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        if (bodyMap.isEmpty())
            return ""
        for (it in bodyMap){
            stringBuilder.append(it.key)
            stringBuilder.append("=")
            stringBuilder.append(it.value)
            stringBuilder.append("&")
        }
        val toString = stringBuilder.toString()
        return toString.substring(0,toString.length-1)
    }

    fun toJson():String{
        val stringBuilder = StringBuilder()
        if (bodyMap.isEmpty())
            return ""
        stringBuilder.append("{")
        for (it in bodyMap){
            stringBuilder.append("\"")
            stringBuilder.append(it.key)
            stringBuilder.append("\"")
            stringBuilder.append(":")
            stringBuilder.append("\"")
            stringBuilder.append(it.value)
            stringBuilder.append("\"")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}