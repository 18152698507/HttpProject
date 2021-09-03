package cn.zybwz.okhttp.base

import java.lang.StringBuilder
import java.net.URL

class Request {
    private lateinit var url:String
    private var method:String="GET"
    private var protocol:String="HTTP/1.1"
    private lateinit var host:String
    private var port:Int = 80
    private lateinit var api:String
    private lateinit var head:String
    private var headerMap:MutableMap<String,Any> = mutableMapOf()
    private lateinit var headerString:String
    private  var body: FormBody?=null


    //https://zhuanlan.zhihu.com/p/77272642
    fun url(url:String):Request{
        this.url=url
        val urlReal = URL(url)
        host=urlReal.host
        port=urlReal.port
        if (port==-1)
            port=urlReal.defaultPort
        api=urlReal.path
        return this
    }

    fun post(body:FormBody){
        this.body=body
    }

    fun host(host:String):Request{
        this.host=host
        return this
    }

    fun port(port:Int):Request{
        this.port=port
        return this
    }

    fun api(api:String):Request{
        this.api=api
        return this
    }

    fun addHeader(key:String,value:Any):Request{
        headerMap[key]=value
        return this
    }

    fun setMethod(method:String):Request{
        this.method=method
        return this
    }

    private fun buildDefaultHeader(){
        addHeader("Host", "$host:$port")
        //addHeader("Content-Type","application/x-www-form-urlencoded")
        addHeader("User-Agent","PostmanRuntime/7.15.0")
        addHeader("Accept","*/*")
        addHeader("Cache-Control","no-cache")
        addHeader("Accept-Encoding","br, deflate,gzip")
        addHeader("Connection","keep-alive")
    }

    private fun buildDefaultHead(){
        head = "$method $api $protocol"
    }

    private fun buildGetHead(body:String?){
        head = if (body!=null||body=="null")
            "$method $api?$body $protocol"
        else
            "$method $api $protocol"
    }

    private fun header2String(){
        val stringBuilder =StringBuilder()
        for (it in headerMap){
            stringBuilder.append(it.key)
            stringBuilder.append(": ")
            stringBuilder.append(it.value)
            stringBuilder.append("\n")
        }
        if (method=="POST"){
            stringBuilder.append("Content-Length")
            stringBuilder.append(": ")
            stringBuilder.append(body.toString().length)
            stringBuilder.append("\n")
        }
        headerString=stringBuilder.toString()
    }

    fun build():Builder?{
        buildDefaultHeader()
        val type = headerMap["Content-Type"]
        println(type)
        val body = if (type=="application/x-www-form-urlencoded")
            this.body?.toString()
        else this.body?.toJson()
        println(body)
        header2String()

        when(method){
            "GET"->{
                buildGetHead(body)
                return Builder(host,port,head,headerString,"")
            }
            "POST"->{
                buildDefaultHead()
                return Builder(host,port,head,headerString,body?:"")
            }
        }
        return null
    }

    inner class Builder(val host: String,val port: Int,val head:String,val header:String,val body:String){

        override fun toString(): String {
            val stringBuilder =StringBuilder()
            stringBuilder.append(head)
            stringBuilder.append("\n")
            stringBuilder.append(header)
            stringBuilder.append("\n")
            stringBuilder.append(body)
            stringBuilder.append("\n")
            return stringBuilder.toString()
        }
    }

}