package myjob.core.rpc.netty.service;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import myjob.core.rpc.netty.service.handler.EchoServiceHandler;

public class EchoService {
	private int port = 8989;
	public EchoService(int port) {
		this.port = port;
	}
	public void start() throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.option(ChannelOption.SO_BACKLOG, 1024);
		sb.group(group,bossGroup).//绑定线程池
		channel(NioServerSocketChannel.class).//指定使用的channal
		localAddress(port).//绑定监听端口
		childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				System.out.println("报告");
				System.out.println("信息：有一个客户端连接到本地本服务端");
				System.out.println("IP:"+ ch.localAddress().getHostName());
				System.out.println("IP:"+ ch.localAddress().getPort());
				System.out.println("报告完毕！！！");
				ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8"))).//设置编码
				addLast(new EchoServiceHandler()).//客户端触发操作
				addLast(new ByteArrayEncoder());
			}			
		});
		ChannelFuture cf;
		try {
			cf = sb.bind().sync();//服务器异步创建绑定
			System.out.println(EchoService.class+"正在监听" + cf.channel().localAddress());
			cf.channel().closeFuture().sync();//关闭服务通道
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully().sync();//释放线程池资源
			bossGroup.shutdownGracefully().sync();
		}
	}
//	public static void main(String[] args) throws InterruptedException {
//		new EchoService(8888).start();
//	}
}
