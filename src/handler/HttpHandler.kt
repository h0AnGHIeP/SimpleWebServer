package handler

abstract class HttpHandler {
    fun handle(request: Request, response: Response) {
        when (request.type) {
            HttpType.GET -> onGet(request, response)
            HttpType.POST -> onPost(request, response)
        }
    }

    abstract fun onGet(request: Request, response: Response)
    abstract fun onPost(request: Request, response: Response)
}