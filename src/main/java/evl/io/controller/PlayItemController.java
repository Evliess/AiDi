package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.service.PlayItemService;
import evl.io.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/v1")
@Tag(name = "增值项目管理", description = "增值项目管理的操作接口")
public class PlayItemController {

    private final PlayItemService playItemService;

    @Autowired
    public PlayItemController(PlayItemService playItemService) {
        this.playItemService = playItemService;
    }

    @GetMapping("/playitems")
    public ResponseEntity<String> hello() {
        return playItemService.findAll();
    }

    @Operation(summary = "新增一个项目")
    @Parameters({
        @Parameter(name = "body",
                description = "{<br>" +
                        "name: 寻宝,<br>" +
                        "money: 39.9<br>}")
    })
    @PostMapping("/play-items")
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String name = jsonNode.getString("name");
        String money = jsonNode.getString("money");
        return playItemService.save(name, money);
    }
    @Operation(summary = "根据项目名字修改价格")
    @Parameters({
            @Parameter(name = "name", description = "项目名字", example = "寻宝"),
            @Parameter(name = "money", description = "收费", example = "39.9")})
    @PutMapping("/play-item/{name}/{money}")
    public ResponseEntity<String> update(@PathVariable("name") String name,
                                         @PathVariable("money") String money) {
        if (ValidationUtils.isBlank(name) || ValidationUtils.isNotBlank(money))
            throw new IllegalArgumentException("参数错误");
        return playItemService.updateByName(name, money);
    }

    @Operation(summary = "根据项目名字查看项目")
    @Parameters({
            @Parameter(name = "name", description = "项目名字", example = "寻宝"),
            })
    @GetMapping("/play-item/{name}")
    public ResponseEntity<String> findByName(@PathVariable("name") String name) {
        if (ValidationUtils.isBlank(name))
            throw new IllegalArgumentException("参数错误");
        return playItemService.findByName(name);
    }

    @Operation(summary = "根据项目名字删除项目")
    @Parameters({
            @Parameter(name = "name", description = "项目名字", example = "寻宝"),
    })
    @DeleteMapping("/play-item/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable("name") String name) {
        if (ValidationUtils.isBlank(name))
            throw new IllegalArgumentException("参数错误");
        return playItemService.deleteByName(name);
    }


}
