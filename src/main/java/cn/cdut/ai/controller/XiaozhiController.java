package cn.cdut.ai.controller;

import cn.cdut.ai.assistant.XiaozhiAgent;
import cn.cdut.ai.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

/**
 * AI相关逻辑
 */
@Tag(name = "法律小智")
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

    @Operation(summary = "流式对话")
    @GetMapping("/chat/stream")
    public SseEmitter streamChat(@RequestParam Long memoryId, @RequestParam String message) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        CompletableFuture.runAsync(() -> {
            try {
                // 获取完整响应
                String response = xiaozhiAgent.chat(memoryId, message);

                // 按字符流式发送，但需要正确处理特殊字符
                for (int i = 0; i < response.length(); i++) {
                    char ch = response.charAt(i);
                    String charStr;

                    // 处理特殊字符
                    if (ch == '\n') {
                        charStr = "\n";  // 保持换行符，让前端处理转换
                    } else {
                        charStr = String.valueOf(ch);
                    }

                    emitter.send(charStr);

                    // 添加小延迟以模拟真实流式效果
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                // 发送结束信号
                emitter.send(SseEmitter.event().name("end").data(""));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }


    // 首页访问：转发到 index.html
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
