package actuator

class ServerState {
    private val commandMap = mutableMapOf<String, () -> Unit>()
    fun addCommand(command: String, func: () -> Unit) {
        commandMap[command] = func
    }

    fun addCommand(map: Map<String, () -> Unit>) {
        commandMap.putAll(map)
    }

    fun actuate() {
        val buffer = System.`in`.bufferedReader()
        while (true) {
            val command = buffer.readLine()
            if (!commandMap.containsKey(command)) {
                println("Command not found!!!")
            } else {
                commandMap.getValue(command)()
            }
        }
    }
}