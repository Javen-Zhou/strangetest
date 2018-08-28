package com.techsure.strange.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/26 20:56
 */
public class NettyTest {
	private static final Logger logger = LoggerFactory.getLogger(NettyTest.class);
	private static final Integer PORT = 7188;

	@Test
	public void server() throws InterruptedException {
		new DiscardServer(7188).run();
	}

	@Test
	public void client(){

	}


}

class DiscardServer{

	private int port;

	public DiscardServer(int port) {
		this.port = port;
	}

	public void run() throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new DiscardServerHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG,128)
					.childOption(ChannelOption.SO_KEEPALIVE,true);

			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
			System.out.println("TEST");
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("TESTFINALLY");
		}
	}
}


class DiscardServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf)msg;
		try{
			while(in.isReadable()){
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		}finally {
			ReferenceCountUtil.release(msg);
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
