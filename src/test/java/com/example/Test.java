package com.example;

import evl.io.dto.DeepSeekStreamService;

public class Test {

    public static void main(String[] args) {
        String apiKey = "";
        DeepSeekStreamService streamService = new DeepSeekStreamService(apiKey);

        String userMessage = "请用Java 打印Hello, World";

        System.out.println("用户: " + userMessage);
        System.out.println("AI: ");

        StringBuilder fullResponse = new StringBuilder();

        streamService.streamChat(
                userMessage,
                // 内容回调
                content -> {
                    if ("[END]".equals(content)) {
                        System.out.println("\n\n=== 完整响应 ===");
                        System.out.println(fullResponse.toString());
                    } else {
                        System.out.print(content);
                        fullResponse.append(content);
                    }
                },
                // 错误回调
                error -> {
                    System.err.println("错误: " + error);
                }
        );

        try {
            // 等待流式响应完成
            Thread.sleep(30000);
            streamService.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
