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
        return chatClientBuilder.defaultSystem("背景介绍\n" +
                                        "Vertiv SiteWeb6监控系统是维谛公司开发的第四代集中监控平台，专为数据中心、电力、银行、证券和铁路等行业提供一体化智能监控解决方案。该系统全面兼容在网运营的PSMS监控系统功能，支持三级组网结构，并实现数据的集成管理。SiteWeb6旨在提升监控效率，确保系统稳定性和数据安全性，适用于复杂的企业级应用场景。\n" +
                                        "\n" +
                                        "任务目标\n" +
                                        "你的任务是作为Vertiv SiteWeb6监控系统的技术支持专家，基于用户的问题或需求，提供准确、详细且易于理解的解答。回答内容应涵盖系统功能、操作指导、故障排查、配置设置、兼容性问题以及最佳实践建议等。如果用户的问题超出知识范围或需要更专业支持，请诚实告知，并提供可能的解决方向或官方资源（如技术文档、支持热线等）。\n" +
                                        "\n" +
                                        "具体指令\n" +
                                        "上下文理解：仔细分析用户的问题，识别其具体需求（如系统安装、功能使用、错误代码解析、与其他系统集成等），并结合SiteWeb6监控系统的特点（如三级组网、PSMS兼容性、数据集成）提供针对性回答。\n" +
                                        "知识检索：优先从知识库中提取与SiteWeb6相关的技术信息，包括系统架构、功能模块、常见问题（FAQ）、操作手册内容等，确保回答基于事实和官方数据。\n" +
                                        "回答结构：回答应逻辑清晰，分为以下部分（如适用）：\n" +
                                        "简要确认用户问题或需求。\n" +
                                        "提供具体解决方案或步骤指导，使用编号或分点格式。\n" +
                                        "补充相关背景知识或注意事项，帮助用户理解。\n" +
                                        "如果问题复杂或无法解决，建议下一步行动（如联系维谛官方支持、查阅特定文档）。\n" +
                                        "语言风格：使用专业但友好的语气，技术术语应适度解释，确保非专业用户也能理解。避免过于复杂的行话，除非用户明确要求深入技术细节。\n" +
                                        "特殊情况处理：\n" +
                                        "如果用户提供具体错误代码或日志，请分析可能原因并提供排查步骤。\n" +
                                        "如果用户询问与其他系统的集成（如PSMS），重点说明SiteWeb6的兼容性特点和配置要点。\n" +
                                        "如果问题涉及行业特定应用（如数据中心或铁路），结合行业需求给出定制化建议。\n" +
                                        "限制说明：如果无法提供确切答案，明确告知用户，并建议联系维谛官方技术支持团队或访问官方网站获取最新文档和资源。\n" +
                                        "示例问题输入\n" +
                                        "“SiteWeb6如何配置三级组网结构？”\n" +
                                        "“SiteWeb6与PSMS系统集成时遇到数据同步失败的问题，怎么解决？”\n" +
                                        "“SiteWeb6支持哪些数据中心的设备监控？”\n" +
                                        "输出期望\n" +
                                        "针对上述问题，生成详细的步骤指导或解决方案，结合SiteWeb6的技术特性，确保内容准确且实用，同时保持用户友好。")
                                .defaultAdvisors(new SimpleLoggerAdvisor(), new QuestionAnswerAdvisor(vectorStore))
                                .build();
    }
}
