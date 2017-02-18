package ru.but4er007.kotlinsockets

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import org.json.JSONObject

fun main(args: Array<String>) {
    Thread({
        val request = Request.Builder()
                .url("ws://localhost:4000/socket/websocket")
                .build();

        val logging = HttpLoggingInterceptor(LoggerForInterceptor())

        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        httpClient.newWebSocket(request, Listener())
    }).start()
}

class Listener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        super.onOpen(webSocket, response)
        println("onOpen")
        webSocket?.send("{\"topic\":\"room:lobby\", \"event\":\"phx_join\", \"payload\": {}, \"ref\":\"joining\"}")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        super.onFailure(webSocket, t, response)
        //print(response.toString() + "\n")
        println(t)
        println("onFailure")
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        super.onClosing(webSocket, code, reason)
        println("onClosing " + reason)
    }

    override fun onMessage(webSocket: WebSocket?, text: String?) {
        super.onMessage(webSocket, text)
        println(text)
        val ref = JSONObject(text).getString("event")
        when(ref){
            "phx_reply" -> webSocket?.send("{\"topic\":\"room:lobby\", \"event\":\"new_msg\", \"payload\": {\"body\": \"HI!!!\"}, \"ref\":\"send_message\"}")
            "new_msg" -> println("new message" + JSONObject(text).getJSONObject("payload").getString("body"));
        }
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        super.onMessage(webSocket, bytes)
        println("onMessage " + bytes)
    }

    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        super.onClosed(webSocket, code, reason)
        println("onMessage " + code)
    }
}

class LoggerForInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(p0: String?) {
        println(p0)
    }

}

