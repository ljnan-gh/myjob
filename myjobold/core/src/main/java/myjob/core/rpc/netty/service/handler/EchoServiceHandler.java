package myjob.core.rpc.netty.service.handler;

import java.io.UnsupportedEncodingException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class EchoServiceHandler extends ChannelInboundHandlerAdapter{
	
	/**
	 * 客户端主动连接服务端的连接后，这个通道就是激活了，也就是客户端与服务端建立了通讯通道并且可以传输数据了
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println(ctx.channel().localAddress().toString()+ "通道已经激活");
	}
	/**
	 * 当客户端主动断开服务端的连接后，这个通道就是不活跃，也就是或客户端与服务端的关闭了通信通道并且不可以传输数据了
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		System.out.println(ctx.channel().localAddress().toString()+ "通道不激活");
	}
	/*
	 * 处理收到的数据中含有中文的时候，出现乱码问题
	 */
	private String getMessage(ByteBuf buf) {
		byte[] con = new byte[buf.readableBytes()];
		buf.readBytes(con);		
		try {
			return new String(con,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws InterruptedException {
		System.out.println("服务端接收数据完毕......");
		//第一种方法：写一个空的buf，并刷新写出区域。完成后关闭socket channel连接
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		ctx.flush();
		//第二种方法：在client端关闭channel连接，这样的话会触发两次channelReadComplete方法
		ctx.flush().close().sync();//第三种：改成这种写法也可以，但是这种写法，没有第一种写法好
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		ctx.close();
		System.out.println("异常信息\r\n" + cause.getMessage());
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) {
		ByteBuf buf = (ByteBuf)msg;
		String rev = getMessage(buf);
		System.out.println("客户端收到服务器数据：" + rev);
	}
}
