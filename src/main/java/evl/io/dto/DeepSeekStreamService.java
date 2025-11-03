package evl.io.dto;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DeepSeekStreamService {
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/chat/completions";
    private static final Logger log = LoggerFactory.getLogger(DeepSeekStreamService.class);
    private final String apiKey;
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient httpClient;

    public DeepSeekStreamService(String apiKey) {
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();

        // 配置 HttpClient（支持长连接和流式读取）
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setResponseTimeout(Timeout.of(5, TimeUnit.MINUTES))
                        .setConnectionRequestTimeout(Timeout.of(30, TimeUnit.SECONDS))
                        .build())
                .build();
    }

    /**
     * 流式调用 DeepSeek API
     */
    public void streamChat(String userMessage, Consumer<String> onContent, Consumer<String> onError) {
        HttpPost httpPost = new HttpPost(DEEPSEEK_API_URL);

        // 设置请求头
        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "text/event-stream");

        try {
            // 创建请求体
            DeepSeekStreamRequest request = DeepSeekStreamRequest.create(userMessage);
            String requestBody = objectMapper.writeValueAsString(request);
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();

                if (statusCode == 200) {
                    processStreamResponse(response, onContent);
                } else {
                    String errorResponse = EntityUtils.toString(response.getEntity());
                    onError.accept("HTTP Error: " + statusCode + " - " + errorResponse);
                }
            }
        } catch (Exception e) {
            onError.accept("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理流式响应
     */
    private void processStreamResponse(CloseableHttpResponse response, Consumer<String> onContent) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                log.info("origin-line: {}", line);
                if (line.startsWith("data: ")) {
                    String data = line.substring(6).trim();
                    if ("[DONE]".equals(data)) {
                        break;
                    }
                    if (!data.isEmpty()) {
                        processDataLine(data, onContent);
                    }
                }
            }
        }
    }

    /**
     * 处理单条数据行
     */
    private void processDataLine(String data, Consumer<String> onContent) {
        try {
            JSONObject response = JSONObject.parseObject(data);
            JSONArray choices = response.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject delta = choice.getJSONObject("delta");
                if (delta != null && delta.getString("content") != null) {
                    String content = delta.getString("content");

                    ObjectMapper mapper = new ObjectMapper();
                    String originalFormat = mapper.writeValueAsString(content);
                    onContent.accept(originalFormat);
                }
                if ("stop".equals(choice.getString("finish_reason"))) {
                    onContent.accept("[END]");
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing response data: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
