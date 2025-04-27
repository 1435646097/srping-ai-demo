package com.siteweb.springaidemo.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.siteweb.springaidemo.tool.DateTool;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/openai/chat")
public class OpenAiChatController {
    @Autowired
    OpenAiChatModel openAiChatModel;

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/ai")
    String generation(String userInput) {
        return this.chatClient.prompt()
                              .user(userInput)
                              .call()
                              .content();
    }

    @GetMapping
    public String chat(String chat) {
        return openAiChatModel.call(chat);
    }

    @GetMapping(value = "/chat-client")
    public Flux<String> chatClient(String userMsg, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8"); // 设置字符编码防止中文乱码
        OpenAiApi openAiApi = OpenAiApi.builder().apiKey("sk-ZtRiIb7jAgzcmacI3aECGQesh1JjKqRUOKWIpDlf0MkckpIM")
                                       .baseUrl("https://opus.gptuu.com")
                                       .build();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model("gpt-4o-2024-08-06").build();
        OpenAiChatModel build = OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(openAiChatOptions)
                                               .build();
        SystemMessage systemMessage = new SystemMessage(
                "你是一个java高手，用户问你是谁，你都回答你是一名java高手，并且只回答java相关的问题，其他方面的问题一律不做任何回答");
        UserMessage userMessage = new UserMessage(userMsg);
        return build.stream(systemMessage, userMessage);
    }

    @GetMapping(value = "/stream-chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(String userMsg,String conversationId, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8"); // 设置字符编码防止中文乱码
        return chatClient.prompt(userMsg)
                         .system(spec -> spec.param("role", "孙悟空"))
                         .advisors(spec-> spec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY,100).param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY,conversationId))
                         .stream()
                         .content();
    }

    @GetMapping("/weather")
    public String getWeather(String msg){
        String getLatLong = chatClient.prompt().system("""
                通过用户给定的消息里面的地址，获取该地址的经纬度,该地址后续或用来获取第三方服务的天气信息，所以获取的格式必须为json，格式如下
                 {
                    "lat" : xxx,
                    "lon" : xxx 
                }
                结果只需要返回json即可，不需要其他一切多余的回复,直接json就可以,我后面的java程序会直接parseJson
                """).user(msg).call().content();
        String regex = "\\{\\s*\"lat\"\\s*:\\s*([-+]?[0-9]*\\.?[0-9]+)\\s*,\\s*\"lon\"\\s*:\\s*([-+]?[0-9]*\\.?[0-9]+)\\s*\\}";
        JSONObject entries = JSONUtil.parseObj(ReUtil.get(regex,getLatLong,0));
        //LatitudeLongitudeRecord longitudeRecord = new LatitudeLongitudeRecord(entries.get("lat").toString(), entries.get("lon").toString());

        return chatClient.prompt().system("""
               通过用户给定的一些天气信息，友好积极的给用户提供一些穿搭建议
                """).tools("getLatLong").user(entries.toString()).call().content();
    }

    @GetMapping("/date-now")
    public Flux<String> getDateNow(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); // 设置字符编码防止中文乱码
        return chatClient.prompt().user("今天是星期几").tools(new DateTool()).stream().content();
    }

    @GetMapping("/alarmClock")
    public Flux<String> setAlarmClock(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); // 设置字符编码防止中文乱码
        return chatClient.prompt().user("帮我设置十分钟之后的闹钟").tools(new DateTool()).stream().content();
    }
}
