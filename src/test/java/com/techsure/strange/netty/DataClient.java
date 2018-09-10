package com.techsure.strange.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DataClient {
	private static final Logger logger = LoggerFactory.getLogger(DataClient.class);

	private String host;
	private Channel channel;
	private EventLoopGroup workerGroup;

	public DataClient(String host) {
		this.host = host;
		createChannel();
	}

	private void createChannel() {
		workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_SNDBUF, 1024 * 150)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(
											new ObjectEncoder(),
											new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
											new ChannelOutboundHandlerAdapter());
						}
					});
			channel = b.connect(host, 7188).sync().channel();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void closeChannel() {
		try {
			channel.closeFuture();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public void writeAndFlush(TransportVo msg) {
		ChannelFuture future = channel.writeAndFlush(msg);
		future.addListener((ChannelFutureListener) channelFuture -> {
			if (!channelFuture.isSuccess()) {
				logger.error(channelFuture.cause().getMessage(), future.cause());
				TimeUnit.SECONDS.sleep(3);
				closeChannel();
				createChannel();
			}
		});
	}

	public String getHost() {
		return host;
	}
}