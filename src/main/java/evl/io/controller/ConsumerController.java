package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.service.ConsumerService;
import evl.io.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/v1")
@Tag(name = "会员管理", description = "会员管理的操作接口")
public class ConsumerController {
    public static final String ILLEGAL_ARGS_MSG = "参数错误！";
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Operation(summary = "新增一个会员")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "name: 小飞,<br>" +
                            "money: 998,<br>" +
                            "type: day,<br>" +
                            "leftCount: 10,<br>" +
                            "expiredAt: 2021/01/11,<br>" +
                            "phone: 15611112222<br>}")
    })
    @PostMapping("/consumers")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        String name = jsonNode.getString("name");
        String money = jsonNode.getString("money");
        if (ValidationUtils.isBlank(money)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        String type = jsonNode.getString("type");
        String leftCount = jsonNode.getString("leftCount");
        String expiredAt = jsonNode.getString("expiredAt");
        return consumerService.create(phone, name, money, type, leftCount, expiredAt);
    }

    @Operation(summary = "根据手机号查找会员")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
    })
    @GetMapping("/consumer/{phone}")
    public ResponseEntity<String> findByPhone(@PathVariable("phone") String phone) {
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return consumerService.findByPhone(phone);
    }

    @Operation(summary = "根据手机号修改会员名字")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
            @Parameter(name = "name", description = "新名字", example = "小飞飞"),
    })
    @PutMapping("/consumer/{phone}/{name}")
    public ResponseEntity<String> updateNameByPhone(@PathVariable("phone") String phone, @PathVariable("name") String name) {
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return consumerService.updateNameByPhone(phone, name);
    }

    @Operation(summary = "根据手机号修改会员剩余次数")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
            @Parameter(name = "leftCount", description = "剩余次数", example = "9"),
    })
    @PutMapping("/consumer/left-count/{phone}/{leftCount}")
    public ResponseEntity<String> updateLeftCountByPhone(@PathVariable("phone") String phone, @PathVariable("leftCount") String leftCount) {
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return consumerService.updateLeftCountByPhone(phone, leftCount);
    }

    @Operation(summary = "根据手机号修改会员信息")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
            @Parameter(name = "body",
                    description = "{<br>" +
                            "type: day,<br>" +
                            "leftCount: 10,<br>" +
                            "expiredAt: 2000/01/19<br>}")
    })
    @PutMapping("/consumer/{phone}/")
    public ResponseEntity<String> updateByPhone(@PathVariable("phone") String phone, @RequestBody String body) {
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        JSONObject jsonNode = JSON.parseObject(body);
        String type = jsonNode.getString("type");
        String leftCount = jsonNode.getString("leftCount");
        String expiredAt = jsonNode.getString("expiredAt");
        return consumerService.updateByPhone(phone, leftCount, type, expiredAt);
    }

    @Operation(summary = "根据手机号查看会员基本信息，充值记录，消费记录")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
    })
    @GetMapping("/consumer/view-info/{phone}")
    public ResponseEntity<String> viewVip(@PathVariable("phone") String phone) {
        if (ValidationUtils.isNotValidPhone(phone)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return consumerService.viewVip(phone);
    }
}
