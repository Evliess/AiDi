package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.service.ConsumerPlayService;
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
@Tag(name = "会员划卡", description = "会员划卡的操作接口")
public class ConsumerPlayController {

    private final ConsumerPlayService consumerPlayService;

    @Autowired
    public ConsumerPlayController(ConsumerPlayService consumerPlayService) {
        this.consumerPlayService = consumerPlayService;
    }

    @Operation(summary = "新增一条划卡记录")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "item: 寻宝,<br>" +
                            "phone: 15611112222<br>}")
    })
    @PostMapping("/consumers-play")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String item = jsonNode.getString("item");
        if (ValidationUtils.isNotValidPhone(phone) || ValidationUtils.isBlank(item)) {
            throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        }
        return consumerPlayService.create(body);
    }

    @Operation(summary = "根据手机号查找会员划卡记录")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
    })
    @GetMapping("/consumer-play/{phone}")
    public ResponseEntity<String> findByPhone(@PathVariable("phone") String phone) {
        if (ValidationUtils.isNotValidPhone(phone))
            throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        return consumerPlayService.findByPhone(phone);
    }

}
