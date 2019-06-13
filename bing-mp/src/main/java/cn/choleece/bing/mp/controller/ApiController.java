package cn.choleece.bing.mp.controller;

import cn.choleece.bing.common.util.R;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by choleece on 2019/6/11.
 */
@RestController
@RequestMapping("/mp/api")
public class ApiController {

    @PostMapping("/auth")
    public String authLogin(@RequestBody JSONObject json) {
        String code = json.getString("code");
        System.out.println(code);
        return R.ok();
    }
}
