package com.siteweb.springaidemo.tool;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.Date;

@Slf4j
public class DateTool {
    @Tool(description = "获取当前的时间")
    public String getNowDate() {
        return DateUtil.now();
    }

    @Tool(description = "根据时间设置闹钟")
    public String alarmClock(@ToolParam(description = "时间戳 毫秒级") Long time) {
        Date date = new Date(time);
        log.info("你成功设置闹钟为：{}", date);
        return date.toString();
    }
}
