import actuator.ServerState
import handler.*
import handler.error.DefaultErrorHandler
import handlerImp.HomeHandler
import java.io.*
import java.net.ServerSocket
import java.util.*
import kotlin.system.exitProcess

/*
1 server - 1 mapper
 */
class UrlMapper {
    private val routeTable = hashMapOf<String, HttpHandler>()
    private val errorHandler = DefaultErrorHandler()
    private fun parseText(raw: String): Request {
        val scan = Scanner(raw)
        return try {
            val result = Request(HttpType.valueOf(scan.next()), scan.next())
            result
        } catch (e: Exception) {
            Request(HttpType.GET, "/error")
        }
    }

    fun addRoute(map: Map<String, HttpHandler>) {
        routeTable.putAll(map)
    }

    fun response(rawRequest: String): String {
        val request = parseText(rawRequest)
        val handler = routeTable[request.path]
        val templateResponse = Response(HttpCode.OK)
        if (handler?.handle(request, templateResponse) == null) {
            errorHandler.handle(request, templateResponse)
        }
        return templateResponse.parseRaw()
    }

}

class SimpleJvmServer(map: HashMap<String, HttpHandler>, private val port: Int = 8080) {
    private val mapper = UrlMapper()
    private val server = ServerSocket(port)

    init {
        mapper.addRoute(map)
    }

    private fun startActuator(commandMap: (HashMap<String, () -> Unit>)?) {
        Thread {
            ServerState().apply {
                addCommand("exit") {
                    print("Exiting process")
                    exitProcess(-1)
                }
                if (commandMap != null) addCommand(commandMap)
            }.actuate()
        }.start()
    }

    fun activate() {
        startActuator(null)
        while (true) {
            val userRequest = server.accept()
            Thread {
                println(userRequest.inetAddress.hostAddress)
                val buffer = BufferedReader(InputStreamReader(userRequest.getInputStream()))
                val textResponse = mapper.response(buffer.readLine())
                userRequest.getOutputStream().apply {
                    write(textResponse.toByteArray())
                    close()
                }
                buffer.close()
            }.start()
        }
    }


}

fun main() {
    SimpleJvmServer(
        hashMapOf(
            "/" to HomeHandler()
        ), 80
    ).activate()



}
