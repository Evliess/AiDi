package evl.io.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SlackUtils {
    private final String webhookUrl;

    public SlackUtils(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    private RestTemplate buildRestTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofMinutes(2L))
                .setReadTimeout(Duration.ofMinutes(2L)).build();
    }


    private HttpEntity<String> buildReqHttpEntity(HttpHeaders headers) throws JsonProcessingException {
        String markdownText = "*这是加粗文本*\n" +
                "_这是斜体文本_\n" +
                "`这是代码片段`\n" +
                "• 列表项1\n" +
                "• 列表项2\n" +
                ">>> 引用文本";
        Map<String, Object> body = new HashMap<>();
        // 使用 blocks 来支持 mrkdwn
        JSONArray blocks = new JSONArray();

        // 创建一个 section block 使用 mrkdwn
        JSONObject sectionBlock = new JSONObject();
        sectionBlock.put("type", "section");

        JSONObject textObject = new JSONObject();
        textObject.put("type", "mrkdwn");
        textObject.put("text", markdownText);

        sectionBlock.put("text", textObject);
        blocks.add(sectionBlock);

        body.put("blocks", blocks);
        return new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers);
    }

    public void sendMessage(String text, String username, String channel) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = buildReqHttpEntity(headers);
        RestTemplate restTemplate = buildRestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String respBody = response.getBody();
                System.out.println(respBody);
            } else {
                System.out.println(response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
