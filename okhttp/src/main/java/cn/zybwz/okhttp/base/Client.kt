package cn.zybwz.okhttp.base

import android.util.Log
import java.net.Socket

class Client {
    private lateinit var socket: Socket

    fun open(request:Request.Builder):Response{
        socket= Socket(request.host,request.port)
        val outputStream = socket.getOutputStream()
        val inputStream = socket.getInputStream()
        println(request.toString())
        outputStream.write(request.toString().toByteArray())
        val response = Response()
        response.dealInput(inputStream)
        socket.close()
        return response
    }

}