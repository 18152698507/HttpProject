package cn.zybwz.okhttp.base


import cn.zybwz.okhttp.utils.GZipUtil
import java.io.InputStream
import java.lang.StringBuilder


class Response() {
    private val TAG="Response"
    private var code:Int=0
    private var contentLength=0
    private val headerMap:LinkedHashMap<String,String> = linkedMapOf()
    private var data:String?=null
    private var headFinish=false
    lateinit var inputStream: InputStream

    fun getHeaders():LinkedHashMap<String,String>{
        return headerMap
    }

    private fun readLine(inputStream: InputStream):String{
        val byteArray=ArrayList<Byte>()
        while (inputStream.read().let {
                if (it!=10&&it!=13){
                    byteArray.add(it.toByte())
                }
                it
            }!=10){

        }
        return String(byteArray.toByteArray())
    }

    fun dealInput(inputStream: InputStream){
        val lines= mutableListOf<String>()
        while (!headFinish){
            val line=readLine(inputStream)
            if (line.isEmpty()){
                headFinish=true
                break
            }
            println(line)

            if (lines.isEmpty()){
                code=line.split(" ")[1].toInt()
            }else{
                val headMap = line.split(":")
                headerMap[headMap[0]]=headMap[1].replaceFirst(" ","")

            }

            lines.add(line)

        }


        if (headerMap.containsKey("Transfer-Encoding")){
            chuckData(inputStream)
        } else if(headerMap.containsKey("Content-Length")){
            contentLengthData(inputStream)
        }
    }

    private fun contentLengthData(inputStream: InputStream){
        contentLength = headerMap["Content-Length"]?.toInt()?:0
        println(contentLength)
        if (contentLength>0){
            val by=ByteArray(contentLength)
            inputStream.read(by)
            val uncompress = GZipUtil.uncompress(by)
            this.data= String(uncompress)
        }
    }
    private fun chuckData(inputStream: InputStream){
        var lastChuck=false
        val dataBuilder=StringBuilder()
        while (!lastChuck){
            val line=readLine(inputStream)
            if (line.isEmpty())
                continue
            val chuckSize = Integer.parseInt(line, 16)
            contentLength+=chuckSize
            if (chuckSize>0){
                val chuckDataByteArray=ByteArray(chuckSize)
                inputStream.read(chuckDataByteArray)
                dataBuilder.append(String(chuckDataByteArray))
            }else lastChuck=true
        }
        this.data= dataBuilder.toString()
    }

    fun string():String?{
        return data
    }
}