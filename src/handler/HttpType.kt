package handler

enum class HttpType {
    GET, POST
}

enum class HttpCode(val value: Int) {
    OK(200), SERVER_ERROR(500)
}