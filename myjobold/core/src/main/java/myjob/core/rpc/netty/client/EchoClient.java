package myjob.core.rpc.netty.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import myjob.core.rpc.netty.client.handler.EchoClientHandler;
public class EchoClient {
	private String host;
	private int port;
	public EchoClient(String host,int port) {
		this.host = host;
		this.port = port;
	}
	public void start() throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).//注册线程池
		channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(this.host,this.port)).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				System.out.println("正在连接......");
				ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
				ch.pipeline().addLast(new EchoClientHandler());
				ch.pipeline().addLast(new ByteArrayEncoder());
				ch.pipeline().addLast(new ChunkedWriteHandler());
			}
		});
		ChannelFuture cf;
		try {
			cf = b.connect().sync();//异步连接服务器
			System.out.println("服务端连接成功......");
			cf.channel().closeFuture().sync();//异步等待关闭连接
			System.out.println("连接已关闭......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully().sync();//释放线程池资源
		}		
	}
//	public static void main(String[] args) throws InterruptedException {
//		new EchoClient("127.0.0.1",8888).start();
//	}
}
