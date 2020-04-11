package handler


data class Response(val code: HttpCode, var body: String = "Hello") {
    fun parseRaw() = "HTTP/1.1 ${code.value} $code" + "\n\n" + body
}