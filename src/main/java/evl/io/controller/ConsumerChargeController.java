package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.service.ConsumerChargeService;
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
@Tag(name = "会员充值", description = "会员充值的操作接口")
public class ConsumerChargeController {

    private final ConsumerChargeService consumerChargeService;
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerChargeController(ConsumerChargeService consumerChargeService,
                                    ConsumerService consumerService) {
        this.consumerChargeService = consumerChargeService;
        this.consumerService = consumerService;
    }

    @Operation(summary = "新增一条充值记录, 充值金额负值(-198)表示取消充值")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "money: 998,<br>" +
                            "type: day,<br>" +
                            "leftCount: 10,<br>" +
                            "expiredAt: 2021/01/11,<br>" +
                            "phone: 15611112222<br>}")
    })
    @PostMapping("/consumers-charge")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String money = jsonNode.getString("money");
        if (ValidationUtils.isNotValidPhone(phone) || ValidationUtils.isBlank(money)) {
            throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        }
        return consumerChargeService.create(body);
    }

    @Operation(summary = "根据手机号查找会员充值记录")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
    })
    @GetMapping("/consumer-charge/{phone}")
    public ResponseEntity<String> findByPhone(@PathVariable("phone") String phone) {
        if (ValidationUtils.isNotValidPhone(phone))
            throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        return consumerChargeService.findByPhone(phone);
    }

}
