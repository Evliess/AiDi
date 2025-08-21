package evliess.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.service.ConsumerScoreService;
import evliess.io.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static evliess.io.constant.ServiceConstants.ILLEGAL_ARGS_MSG;

@RestController
@RequestMapping("/public/v1")
@Tag(name = "会员积分管理", description = "会员积分的操作接口")
public class ConsumerScoreController {
    private final ConsumerScoreService consumerScoreService;

    public ConsumerScoreController(ConsumerScoreService consumerScoreService) {
        this.consumerScoreService = consumerScoreService;
    }

    @Operation(summary = "新增或则修改会员积分")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "score: 3,<br>" +
                            "phone: 15611112222<br>}")
    })
    @PostMapping("/consumers-score")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String score = jsonNode.getString("score");
        if (ValidationUtils.isNotValidPhone(phone) || ValidationUtils.isBlank(score))
            throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return consumerScoreService.createOrUpdate(phone, score);
    }


    @Operation(summary = "根据手机号查找会员积分")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", example = "15611112222"),
    })
    @GetMapping("/consumer-score/{phone}")
    public ResponseEntity<String> findByPhone(@PathVariable("phone") String phone) {
        if (ValidationUtils.isNotValidPhone(phone))
            throw new IllegalArgumentException(ServiceConstants.ILLEGAL_ARGS_MSG);
        return consumerScoreService.findByPhone(phone);
    }
}
