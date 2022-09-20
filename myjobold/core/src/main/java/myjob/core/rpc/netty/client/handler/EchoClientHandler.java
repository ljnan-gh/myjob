package myjob.core.rpc.netty.client.handler;

import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("读取客户端通道消息......");
		ByteBuf buf = msg.readBytes(msg.readableBytes());
		System.out.println("客户端收到的服务端消息：" + ByteBufUtil.hexDump(buf)+"数据包为：" +buf.toString(Charset.forName("utf-8")));
	}
	
	/**
	 * 向服务端发送数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("客户端与服务端通道开启");
		String sendInfo = "Hello,这里是客户端";
		System.out.println("客户端准备发送数据包......");
		ctx.writeAndFlush(Unpooled.copiedBuffer(sendInfo,CharsetUtil.UTF_8));
	}
	
	/**
	 * 当客户端主动断开服务端的连接后，这个通道就是不活跃，也就是或客户端与服务端的关闭了通信通道并且不可以传输数据了
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		System.out.println("客户端与服务端通道关闭");
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		ctx.close();
		System.out.println("异常信息\r\n" + cause.getMessage());
	}
}
