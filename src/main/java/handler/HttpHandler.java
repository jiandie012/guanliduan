package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.util.concurrent.TimeUnit;

public class HttpHandler extends ChannelInboundHandlerAdapter {


    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        long start = System.nanoTime();
        String returnMessage = "";
        if (msg instanceof HttpRequest) {
            //这里可以去取header之类的东西
            request = (FullHttpRequest) msg;
            System.out.println("Uri:" + request.getUri());
        }
        if (msg instanceof HttpContent) {
            //这里来做content的相关处理
            try {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();

            } catch (Exception e) {
                System.out.println("bad bad bad");
            }


            String res = "aaaaaaa";
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
        long end = System.nanoTime();

        long resultTime = end - start;
        //计算每次请求到返回的用时
        System.out.println(TimeUnit.NANOSECONDS.toMillis(resultTime));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
