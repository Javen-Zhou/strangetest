package com.techsure.strange.netty;

import com.techsure.strange.hazelcast.assist.RollupVo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	public void client() throws InterruptedException {
		DataClient dataClient = new DataClient("127.0.0.1");
		for(int i=0;i<10;i++){
			TransportVo transportVo = new TransportVo();
			List<RollupVo> rollupVoList = new ArrayList<>();
			for(int j=0;j<10;j++){
				RollupVo rollupVo = new RollupVo();
				rollupVo.setMetricId(j+"");
				rollupVo.setTable("test");
				rollupVo.setTenant("test");
				rollupVo.setDataType("double");
				rollupVo.setTtl(0);
				rollupVo.setSum(new Double(10));
				rollupVo.setAverage(new Double(10));
				rollupVo.setMax(new Double(10));
				rollupVo.setMin(new Double(10));
				rollupVo.setNum(1);
				rollupVo.setP95(new Double(j));
				rollupVo.setP5(new Double(j));
				rollupVo.setTime(0L);
				rollupVoList.add(rollupVo);
			}
			transportVo.setRollupVoList(rollupVoList);
			dataClient.writeAndFlush(transportVo);
		}

		while(true){
			TimeUnit.SECONDS.sleep(2L);
		}
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
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ch.pipeline()
									.addLast(
											new ObjectEncoder(),
											new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
											new DataServerHandlerAdapter());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.SO_RCVBUF, 1024 * 1024 * 2);
			b.bind(7188).sync().channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("TESTFINALLY");
		}
	}
}


class DataServerHandlerAdapter extends SimpleChannelInboundHandler<TransportVo> {
	private static final Logger logger = LoggerFactory.getLogger(DataServerHandlerAdapter.class);


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TransportVo transportVo) {


		logger.debug("accept {} data ", transportVo.getRollupVoList().size());
		TransportVo newTransportVo = new TransportVo(transportVo.getType(),transportVo.getRollupVoList());
		ChronicleWriterUtil.write(transportVo);
	}


}