package com.konasl.httpserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Lenovo on 2/24/2015.
 */
public class MessageExchange {

    private final SocketChannel channel;
    private ByteBuffer requestMessageBuffer;

    public MessageExchange(SocketChannel channel) {
        this.channel = channel;
    }

    private void write(ByteBuffer buffer) throws IOException {
        channel.write(buffer);
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public ByteBuffer getRequestMessageBuffer() {
        return requestMessageBuffer;
    }

    public void setRequestMessageBuffer(ByteBuffer requestMessageBuffer) {
        this.requestMessageBuffer = requestMessageBuffer;
    }

    public void close() {
        try {
            channel.close();
        } catch (IOException ex) {
        }
    }
}
