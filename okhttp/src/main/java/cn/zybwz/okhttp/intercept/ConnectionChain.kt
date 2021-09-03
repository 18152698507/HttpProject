package cn.zybwz.okhttp.intercept

import cn.zybwz.okhttp.base.Request
import cn.zybwz.okhttp.base.Response

class ConnectionChain(private var response: Response,
                      private var request: Request
) :Intercept.Chain {

    override fun request(): Request {
        return request
    }

    override fun proceed(request: Request): Response {
        return response
    }
}