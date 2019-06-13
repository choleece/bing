package cn.choleece.bing.mp.util.tencent;

import cn.choleece.bing.common.constant.PropertyName;
import cn.choleece.bing.common.util.PropertiesFileUtil;
import cn.choleece.bing.mp.vo.wx.Template;
import cn.choleece.bing.mp.vo.wx.TemplateParam;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by choleece on 2019/6/11.
 */
public class TencentUtils {

    private static final String appId;

    private static final String appSecret;

    private static final String tokenUrl;

    private static final String jsCodeUrl;

    private static final String notifyTemplateUrl;

    private static final String orderDoneTemplateId;

    static {
        appId = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.appKey");
        appSecret = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.appSecret");
        tokenUrl = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.accessToken.url");
        jsCodeUrl = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.jscode.url");
        notifyTemplateUrl = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.template.url");
        orderDoneTemplateId = PropertiesFileUtil.getInstance(PropertyName.TENCENT).get("tencent.mp.template.orderDone.id");
    }

    public static JSONObject getAccessToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "client_credential");
        map.put("appid", appId);
        map.put("secret", appSecret);

        String response = HttpUtil.get(tokenUrl, map, 5000);
        JSONObject json = JSONObject.parseObject(response);
        System.out.println(response);
        return json;
    }

    public static JSONObject mpLogin(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);

        String response = HttpUtil.get(jsCodeUrl, map, 5000);
        JSONObject json = JSONObject.parseObject(response);
        System.out.println(response);
        return json;
    }

    public static void sendOrderDoneTemplate(String accessToken, String toUser) {
        Template tem = new Template();
        tem.setTemplateId(orderDoneTemplateId);
        // 颜色
        tem.setPage("pages/home/index");
        // 接收方ID
        tem.setToUser(toUser);
        // 设置超链接（点击模板可跳转相应链接中）
        tem.setUrl("www.baidu.com");
        tem.setFormId("2fa75d765105497f9e80774922aaabca");

        List<TemplateParam> paras = new ArrayList<TemplateParam>();//消息主体
        paras.add(new TemplateParam("keyword1", "测试","#333")); //标题
        paras.add(new TemplateParam("keyword2", "333","#333"));//审核类型
        paras.add(new TemplateParam("keyword3", "444","#333"));//时间
        paras.add(new TemplateParam("keyword4", "555","#333"));
        paras.add(new TemplateParam("keyword5","点击此消息查看详情","#333"));
        paras.add(new TemplateParam("keyword6","点击此消息查看详情","#333"));
        paras.add(new TemplateParam("keyword7","点击此消息查看详情","#333"));
        tem.setTemplateParamList(paras);

        HttpUtil.post(notifyTemplateUrl + accessToken, tem.toJSON());
    }

    public static void sendUnifyTemplateMsg(String accessToken, Template template) {

        String response = HttpUtil.post(notifyTemplateUrl + accessToken, template.toJSON());

        System.out.println(response.toString());
    }

    public static void main(String[] args) {
        // openId: oHH4F0ZkOcb4i1NHgfvLV1U24r1Y
//        mpLogin("071HrPkm0EDf4s1iEtlm0zkdlm0HrPkO");
        // token 22_308GI0VLU80SCZdlvG_JGP2s-NwnKc1U_U5pgH4UtGvbwRLg2wYEv21MpRj7jaEm5UonYrRh0k-9ls0xJmp262EveRBJvEin-Hj-JxnrmZncLa3Bdax9rgoYba4TEJeABAJUZ
        JSONObject json = getAccessToken();

        sendOrderDoneTemplate(json.getString("access_token"), "oHH4F0ZkOcb4i1NHgfvLV1U24r1Y");

    }

}
