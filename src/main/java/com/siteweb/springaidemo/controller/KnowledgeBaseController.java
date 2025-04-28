package com.siteweb.springaidemo.controller;

import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/knowledge-base")
public class KnowledgeBaseController {
    @Autowired
    @Qualifier("knowledgeBase")
    ChatClient chatClient;

    @GetMapping("/question")
    public Flux<String> questionAnswer(String question, ServletResponse response) {
        response.setCharacterEncoding("UTF-8"); // 设置字符编码防止中文乱码
        return chatClient.prompt().user(question).stream().content();
    }
}
