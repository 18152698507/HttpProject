package cn.zybwz.okhttp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import cn.zybwz.okhttp.base.Client
import cn.zybwz.okhttp.base.Request

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cn.zybwz.okhttp.test", appContext.packageName)
    }

    @Test
    fun testSocket(){
        val request = Request()
//        val formBody = FormBody()
//        formBody.addParam("username","0001")

//        request.post(formBody)
//        request.setMethod("POST")
        //http://59.110.212.105/PharmacyServer/0/selectMedicine.action
        //https://api.caiyunapp.com/v2.5/EMl8cKjtYUQKj-KC/121.6544,25.1552/weather.json
        val build = request.url("http://api.caiyunapp.com/v2.5/EMl8cKjtYUQKj-KC/121.6544,25.1552/weather.json").build()?:return

        val open = Client().open(build)
        println("body: "+open.string())

    }
}