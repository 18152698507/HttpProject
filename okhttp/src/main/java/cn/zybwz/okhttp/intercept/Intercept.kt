package cn.zybwz.okhttp.intercept

import cn.zybwz.okhttp.base.Request
import cn.zybwz.okhttp.base.Response

abstract class Intercept {
    var next:Intercept?=null

    fun doChain(chain: Chain){
        onIntercept(chain)
        next?.doChain(chain)
    }

    abstract fun onIntercept(chain: Chain):Response

    interface Chain{
        fun request():Request
        fun proceed(request: Request):Response
    }
}