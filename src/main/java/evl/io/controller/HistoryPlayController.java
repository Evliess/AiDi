package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.service.HistoryPlayService;
import evl.io.utils.DateTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static evl.io.constant.ServiceConstants.ILLEGAL_ARGS_MSG;

@RestController
@RequestMapping("/public/v1")
@Tag(name = "非会员管理", description = "非会员管理的操作接口")
public class HistoryPlayController {

    private final HistoryPlayService historyPlayService;

    @Autowired
    public HistoryPlayController(HistoryPlayService historyPlayService) {
        this.historyPlayService = historyPlayService;
    }

    @Operation(summary = "新增一条非会员消费记录,(-198)表示撤销消费")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "itemName: 捞鱼,<br>" +
                            "money: 29.9<br>}")
    })
    @PostMapping("/non-consumers")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String itemName = jsonNode.getString("itemName");
        String money = jsonNode.getString("money");
        return historyPlayService.create(money, itemName);
    }

    @Operation(summary = "根据日期查找非会员消费记录")
    @Parameters({
            @Parameter(name = "date", description = "日期", example = "2025/01/27"),
    })
    @GetMapping("/non-consumer/search")
    public ResponseEntity<String> findByPhone(@RequestParam String date) {
        if (date == null || date.isEmpty() || !date.matches(ServiceConstants.DATE_PATTERN_FULL))
            throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        return historyPlayService.findAllByDate(DateTimeUtils.dateToMillis(date));
    }
}
