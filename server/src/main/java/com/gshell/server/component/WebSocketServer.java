package com.gshell.server.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/")
public class WebSocketServer {
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 接收sid
    private String sid = "";

    /* 连接建立成功调用的方法 */

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this); // 加入set中
        addOnlineCount(); // 在线数加1
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        this.sid = sid;
//        try {
//            sendMessage("连接成功");
//
//            //连接ws即推送消息
//
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    // 收到客户端消息后调用的方法
    // @param message 客户端发送过来的消息
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }

    // 实现服务器主动推送

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 群发自定义消息
    public static int sendInfo(String message, @PathParam("sid") String sid) {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        int sended = 0;

        for (WebSocketServer item : webSocketSet) {
            try {
                // 这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null || StringUtils.isBlank(sid.trim())) {
                    item.sendMessage(message);
                    sended++;
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                    sended++;
                }
            } catch (IOException e) {
                continue;
            }
        }
        return sended;
    }

    public static int getOnlineCount() {
        return onlineCount.get();
    }

    protected static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    protected static void subOnlineCount() {
        onlineCount.decrementAndGet();
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }
}

