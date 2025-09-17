package cn.cdut.ai.controller;

import cn.cdut.ai.assistant.XiaozhiAgent;
import cn.cdut.ai.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AI相关逻辑
 */
@Tag(name = "医疗小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {
    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }

    // 首页访问：转发到 index.html
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}