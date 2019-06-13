package cn.choleece.bing.mp.util.baidu;

import cn.choleece.bing.common.constant.PropertyName;
import cn.choleece.bing.common.util.PropertiesFileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by choleece on 2019/6/9.
 */
public class Text2AudioUtils {

    private static String appKey;

    private static String appSecret;

    private static String tokenUrl;

    static {
        appKey = PropertiesFileUtil.getInstance(PropertyName.BAI_DU).get("baidu.app.key");

        appSecret = PropertiesFileUtil.getInstance(PropertyName.BAI_DU).get("baidu.app.secret");

        tokenUrl = PropertiesFileUtil.getInstance(PropertyName.BAI_DU).get("baidu.app.token.url");
    }

    /**
     * 获取百度api的token
     * @return
     */
    public static String getToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        map.put("client_id", appKey);
        map.put("client_secret", appSecret);

        String response = HttpUtil.get(tokenUrl, map, 5000);
        JSONObject json = JSONObject.parseObject(response);
        return json.getString("access_token");
    }

    public static byte[] text2Audio(String content, String token) throws UnsupportedEncodingException {
        Map json = new HashMap();
        json.put("tex", URLEncoder.encode(content, "utf-8"));
        json.put("tok", token);
        json.put("cuid", "1");
        json.put("ctp", "1");
        json.put("lan", "zh");

        HttpResponse response = HttpRequest.post("https://tsn.baidu.com/text2audio").form(json).execute();
        return response.bodyBytes();
    }

    public static void main(String[] args) {
        getToken();
    }

}
