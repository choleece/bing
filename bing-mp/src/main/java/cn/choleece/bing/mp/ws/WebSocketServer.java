package cn.choleece.bing.mp.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * web socket
 * @author choleece
 * @date 2019/6/9
 */
@ServerEndpoint("/order/{userId}")
@Component
public class WebSocketServer {

    private static final Logger logger = LogManager.getLogger("optInfo");

    private static ConcurrentHashMap<String, Session> sessionList = new ConcurrentHashMap<>();

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        try {
            logger.info("建立链接成功...userId: {}", userId);
            sessionList.put(userId, session);
            addOnLineCount();
        } catch (Exception e) {
            logger.error("建立链接失败...userId: {}", userId);
        }
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        try {
            logger.info("关闭链接成功...userId: {}", userId);
            sessionList.remove(userId);
            subOnlineCount();
        } catch (Exception e) {
            logger.error("关闭链接失败...userId: {}", userId);
        }
    }

    @OnError
    public void onError(@PathParam("userId") String userId, Throwable e) {
        logger.error("错误信息: {}, userId: {}", e.getMessage(), userId);
    }

    public void sendMessage(String userId, String message) {
        logger.info("发消息了， 用户ID：{}, 内容: {}",userId, message);
        Session session = sessionList.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMediaMessage(String userId, byte[] bytes) {
        logger.info("发送多媒体消息了, 用户ID：{}", userId);

        Session session = sessionList.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在线用户+1
     */
    public void addOnLineCount() {
        onlineCount.getAndIncrement();
        logger.info("当前已链接用户数: {}", onlineCount.get());
    }

    /**
     * 在线用户-1
     */
    public void subOnlineCount() {
        onlineCount.getAndDecrement();
        logger.info("当前已链接用户数: {}", onlineCount.get());
    }
}
