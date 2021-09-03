package cn.zybwz.okhttp

import cn.zybwz.okhttp.base.Client
import cn.zybwz.okhttp.base.FormBody
import cn.zybwz.okhttp.base.Request
import cn.zybwz.okhttp.utils.GZipUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSocket(){
        val request = Request()
        val formBody = FormBody()
        formBody.addParam("userName","0001")
        formBody.addParam("password","123456")
        request.post(formBody)
        request.addHeader("Content-Type","application/json")
        request.setMethod("POST")
        val build=request.url("http://59.110.212.105/PharmacyServer/signin.action").build()?:return
//        val build = request.url("http://59.110.212.105/PharmacyServer/0/selectMedicine.action").build()?:return
//        val build = request.url("http://api.caiyunapp.com/v2.5/EMl8cKjtYUQKj-KC" +
//                "/121.6544,25.1552/weather.json").build()?:return

        val open = Client().open(build)
        println("body: "+open.string())
    }


    @Test
    fun testGZip(){
        val compress = GZipUtil.compress("123".toByteArray())
        println(compress)
        val uncompress = GZipUtil.uncompress(compress)
        println(String(uncompress))
    }
}