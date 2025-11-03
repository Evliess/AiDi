package evl.io.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.dto.DeepSeekStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequestMapping("/public/v1")
@RestController
@Tag(name = "chat", description = "chat接口")
public class DeepSeekStreamController {
    private static final Logger log = LoggerFactory.getLogger(DeepSeekStreamController.class);
    private final DeepSeekStreamService streamService;

    public DeepSeekStreamController() {
        String apiKey = "";
        this.streamService = new DeepSeekStreamService(apiKey);
    }

    @Operation(summary = "chat with stream model")
    @Parameters({
            @Parameter(name = "body",
                    description = "{<br>" +
                            "msg: 你是谁？<br>}")
    })
    @PostMapping("/api/chat/stream")
    public SseEmitter streamChat(@RequestBody String body) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L); // 5分钟超时
        JSONObject jsonNode = JSON.parseObject(body);
        String userMessage = jsonNode.getString("msg");
        log.info("userMessage: {}", userMessage);
        new Thread(() -> {
            streamService.streamChat(
                    userMessage,
                    content -> {
                        try {
                            log.info("content: {}", content);
                            if ("[END]".equals(content)) {
                                emitter.complete();
                            } else {
                                emitter.send(SseEmitter.event()
                                        .data(content)
                                );
                            }
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    },
                    error -> {
                        emitter.completeWithError(new RuntimeException(error));
                    }
            );
        }).start();
        log.info("Thread is running.");
        return emitter;
    }
}
