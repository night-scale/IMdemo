package com.imdemo;

import com.imdemo.codec.CodecAutoRegistrar;
import com.imdemo.codec.ProtocolDecoder;
import com.imdemo.codec.ProtocolEncoder;
import com.imdemo.handler.*;

import com.imdemo.handler.GroupChatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.*;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import com.imdemo.model.Group;
public class EntailServer {
    @Getter
    @Setter
    private static  ConcurrentMap<String, ChannelHandlerContext> clients = new ConcurrentHashMap<>();
    @Setter
    @Getter
    private static  ConcurrentMap<Long, Group> groups=new ConcurrentHashMap<>();
    @Getter
    @Setter
    private static  ConcurrentMap<Integer, Integer> privateChats = new ConcurrentHashMap<>();
    public static void main(String[] args) throws Exception {
        CodecAutoRegistrar.registerAllCodecs("com.imdemo.codec.decoder", "com.imdemo.codec.encoder");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtocolEncoder());
                            pipeline.addLast(new ProtocolDecoder());

                            //以下阻滞操作如何拆解
                            pipeline.addLast(new SignupHandler());
                            pipeline.addLast(new LoginHandler());
                            pipeline.addLast(new PrivateChatHandler());
                            pipeline.addLast(new GroupChatHandler());
                            pipeline.addLast(new SendMessageHandler());
                        }
                    });

            ChannelFuture f = b.bind(8899).sync();
            System.out.println("Chat Server started on port 8899.");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

