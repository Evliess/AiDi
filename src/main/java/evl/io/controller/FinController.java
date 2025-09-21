package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.service.FinService;
import evl.io.utils.ValidationUtils;
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

import static evl.io.constant.ServiceConstants.ILLEGAL_ARGS_MSG;

@RestController
@RequestMapping("/private/v1")
@Tag(name = "财务管理", description = "财务管理操作接口")
public class FinController {

    private final FinService finService;

    @Autowired
    public FinController(FinService finService) {
        this.finService = finService;
    }

    @Operation(summary = "根据日期查看财务状况，开始日期和结束日期相同表示查询当天")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "start: 2025/09/21,<br>" +
                            "end: 2025/09/21<br>}")
    })
    @PostMapping("/fin/search")
    public ResponseEntity<String> findByPhone(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String start = jsonNode.getString("start");
        String end = jsonNode.getString("end");
        if (ValidationUtils.isNotValidDate(start) || ValidationUtils.isNotValidDate(end))
            throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return finService.findByDateGap(start, end);
    }


}
