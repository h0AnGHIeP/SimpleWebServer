package handler.error

import handler.HttpHandler
import handler.Request
import handler.Response

class DefaultErrorHandler : HttpHandler(){
    override fun onGet(request: Request, response: Response) {
        response.body="Đéo có đâu! Mở cái đầu buồi"
    }

    override fun onPost(request: Request, response: Response) {
        response.body="Cố cái lồn"
    }
}