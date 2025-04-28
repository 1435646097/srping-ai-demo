package com.siteweb.springaidemo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {
    @Bean
    @Primary
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
         return chatClientBuilder.defaultSystem("你是一个知识渊博、乐于助人的全能助手，擅长解答用户在各个领域的问题，包括但不限于学术、技术、生活、娱乐、文化、历史、科学等。你的目标是提供准确、详细且易于理解的答案，同时保持友善和耐心。如果用户的问题超出你的知识范围或需要更专业的信息，请诚实地说明，并提供可能的解决方向或资源建议。确保你的回答逻辑清晰，语言自然，并根据用户的需求调整回答的深度和风格。如果用户有特殊要求或需要特定领域的深入解答，请优先满足其需求。")
                                .defaultAdvisors(new SimpleLoggerAdvisor())
                                .build();
    }

    @Bean("knowledgeBase")
    public ChatClient ChatClient(ChatClient.Builder chatClientBuilder, VectorStore vectorStore){
        return chatClientBuilder.defaultSystem("你是维谛（Vertiv）SiteWeb6（简称 S6）集中监控平台领域的资深技术支持工程师，具有以下特点和要求：\n" +
                                        "\n" +
                                        "拥有深厚的 S6 平台架构、三级组网、数据库及集成方法论知识；\n" +
                                        "熟悉数据中心、机房、UPS、电池柜、PDU、空调、发电机等常见设备的接入与监控逻辑；\n" +
                                        "擅长排查常见故障（如设备离线、报警丢失、数据不刷新、界面错误等），能给出可行的排障步骤；\n" +
                                        "善于将复杂问题分解为清晰的操作步骤，提供必要的命令/界面操作示例；\n" +
                                        "回答要专业、准确、简明，必要时可引用官方文档章节或日志路径；\n" +
                                        "如果用户提出超出 S6 平台职责范围的问题，请礼貌说明并指向更合适的资源或部门。\n" +
                                        "你接下来的回答都要按照上述角色及风格来进行。")
                                .defaultAdvisors(new SimpleLoggerAdvisor(), new QuestionAnswerAdvisor(vectorStore))
                                .build();
    }
}
