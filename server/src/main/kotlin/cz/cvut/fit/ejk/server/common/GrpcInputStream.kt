package cz.cvut.fit.ejk.server.common

import com.google.protobuf.ByteString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.nio.ByteBuffer

class GrpcInputStream(private val byteStringFlow: Flow<ByteString>): InputStream() {
    private val channel = Channel<ByteString>(capacity = Channel.Factory.BUFFERED)
    private var currentBuffer = ByteBuffer.allocate(0)
    private var closed = false

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                byteStringFlow.collect { chunk ->
                    channel.send(chunk)
                }
            } catch (e: Throwable) {
                channel.close(e)
            } finally {
                channel.close()
            }
        }
    }

    override fun read(): Int {
        if (closed) return -1
        if (!currentBuffer.hasRemaining()) {
            val next = runBlocking { channel.receiveCatching().getOrNull() } ?: return -1
            currentBuffer = ByteBuffer.wrap(next.toByteArray())
        }
        return currentBuffer.get().toInt() and 0xFF
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (closed) return -1
        var bytesRead = 0
        while (bytesRead < len) {
            if (!currentBuffer.hasRemaining()) {
                val next = runBlocking { channel.receiveCatching().getOrNull() } ?: break
                currentBuffer = ByteBuffer.wrap(next.toByteArray())
            }
            val toRead = minOf(currentBuffer.remaining(), len - bytesRead)
            currentBuffer.get(b, off + bytesRead, toRead)
            bytesRead += toRead
        }
        return if (bytesRead == 0) -1 else bytesRead
    }

    override fun close() {
        closed = true
        channel.cancel()
    }
}