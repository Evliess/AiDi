package evliess.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.service.ConsumerChargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/v1")
public class ConsumerChargeController {

    private final ConsumerChargeService consumerChargeService;

    @Autowired
    public ConsumerChargeController(ConsumerChargeService consumerChargeService) {
        this.consumerChargeService = consumerChargeService;
    }

    @Operation(summary = "新增一条充值记录")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "money: 198,<br>" +
                            "phone: 15611112222<br>}")
    })
    @PostMapping("/consumers-charge")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String money = jsonNode.getString("money");
        if (phone == null || phone.isEmpty() || money == null || money.isEmpty()) {
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
        if (phone == null || phone.isEmpty()) throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        return consumerChargeService.findByPhone(phone);
    }

}
