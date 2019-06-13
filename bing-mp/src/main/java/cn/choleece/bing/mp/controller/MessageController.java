package cn.choleece.bing.mp.controller;

import cn.choleece.bing.common.util.PropertiesFileUtil;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.choleece.bing.mp.util.baidu.Text2AudioUtils;
import cn.choleece.bing.mp.ws.WebSocketServer;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author choleece
 * @date 2019/6/9
 */
@RestController
public class MessageController {
    @Autowired
    private WebSocketServer webSocketServer;

    @GetMapping("/user/{userId}/message")
    public void sendMessage(@PathVariable("userId") String userId) {
        System.out.printf("我要开始发消息了..");
        webSocketServer.sendMessage(userId, "我是一个测试的消息");
    }

    @GetMapping("/user/{userId}/audio")
    public void sendAudioMessage(@PathVariable("userId") String userId, String no) throws UnsupportedEncodingException {
        System.out.println("我要开始发送语音消息了...");
        String token = "24.23a8dd655aac9b33b300c3be00775fd8.2592000.1562770379.282335-10180830";
        byte[] bytes = Text2AudioUtils.text2Audio("客观您好，您的" + no + "号餐食准备好了，请及时取餐～", token);
        String id = SnowFlakeUtil.getStrId();
        String filePath = PropertiesFileUtil.getInstance().get("sys.mp3.file-path") + id + ".mp3";
        FileUtil.writeBytes(bytes, new File(filePath));
        webSocketServer.sendMessage(userId, id);
    }

    @GetMapping("/message/audio/{id}")
    public void audio(@PathVariable("id") String id, HttpServletResponse response) {
        byte[] bytes = FileUtil.readBytes(PropertiesFileUtil.getInstance().get("sys.mp3.file-path") + id + ".mp3");
        response.setContentType("Content-Type: audio/mp3;charset=utf-8");

        try {
            OutputStream ops = response.getOutputStream();
            ops.write(bytes);
            ops.flush();
            ops.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
