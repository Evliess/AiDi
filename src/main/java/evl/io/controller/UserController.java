package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.service.SugarUserService;
import evl.io.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/v1")
@Tag(name = "用户管理", description = "用户管理操作接口")
public class UserController {
    private final SugarUserService sugarUserService;

    @Autowired
    public UserController(SugarUserService sugarUserService) {
        this.sugarUserService = sugarUserService;
    }

    @Operation(summary = "用户登录")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "name: name,<br>" +
                            "accessKey: accessKey<br>}")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String name = jsonNode.getString("name");
        String accessKey = jsonNode.getString("accessKey");
        return sugarUserService.login(name, accessKey);
    }

    @Operation(summary = "根据code返回openId")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "code: res.code<br>}")
    })
    @PostMapping("/uid")
    public ResponseEntity<String> getUserId(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String code = jsonNode.getString("code");
        String openId = RestUtils.getUid(code);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId", openId);
        return ResponseEntity.ok(jsonObject.toString());
    }

}
