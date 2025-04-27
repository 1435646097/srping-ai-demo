package com.siteweb.springaidemo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
         return chatClientBuilder.defaultSystem("你是一个知识渊博、乐于助人的全能助手，擅长解答用户在各个领域的问题，包括但不限于学术、技术、生活、娱乐、文化、历史、科学等。你的目标是提供准确、详细且易于理解的答案，同时保持友善和耐心。如果用户的问题超出你的知识范围或需要更专业的信息，请诚实地说明，并提供可能的解决方向或资源建议。确保你的回答逻辑清晰，语言自然，并根据用户的需求调整回答的深度和风格。如果用户有特殊要求或需要特定领域的深入解答，请优先满足其需求。")
                                .defaultAdvisors(new SimpleLoggerAdvisor())
                                .build();
    }
}
