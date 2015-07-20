package com.konasl.httpserver;

import com.konasl.httpserver.exception.EventLoopIOException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Mizan on 2/23/2015.
 */
public abstract class NioServer implements Runnable {

    private final int port;
    private final ByteBuffer buffer = ByteBuffer.allocate(2048);

    protected NioServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Selector selector = null;
        ServerSocketChannel server = null;
        try {
            selector = Selector.open();
            server = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(this.port);
            server.socket().bind(address);
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            started();
            while (true) {
                selector.select();
                for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
                    SelectionKey key = i.next();
                    i.remove();
                    if (key.isConnectable()) {
                        ((SocketChannel) key.channel()).finishConnect();
                    }
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.socket().setTcpNoDelay(true);
                        client.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        MessageExchange exchange = (MessageExchange) key.attachment();
                        if (exchange == null) {
                            exchange = new MessageExchange(channel);
                            key.attach(exchange);
                        }
                        channel.read(buffer);
                        exchange.setRequestMessageBuffer(buffer);
                        messageReceived(exchange);
                        exchange.close();
                    }
                }
            }
        } catch (IOException e) {
            throw new EventLoopIOException("io failure in EventLoop " + e.getMessage());
        } finally {
            try {
                selector.close();
                server.socket().close();
                server.close();
                stopped();
            } catch (IOException e) {
            }
        }
    }

    protected abstract void messageReceived(MessageExchange exchange) throws IOException;

    protected abstract void started();

    protected abstract void stopped();
}
