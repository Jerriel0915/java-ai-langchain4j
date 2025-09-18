package cn.cdut.ai.store;

import cn.cdut.ai.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.*;


import javax.annotation.PostConstruct;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 应用启动时执行初始化操作
    @PostConstruct
    public void init() {
        // 清除临时用户（ID为999999999）的聊天记录
        deleteTemporaryUserData();
    }

    // 清除临时用户数据的方法
    public void deleteTemporaryUserData() {
        // 删除userId为999999999的记录
        Criteria criteria = Criteria.where("userId").is(999999999L);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
        System.out.println("已清除临时用户(999999999)的聊天记录");
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // 使用userId来查询对应用户的聊天记录
        Criteria criteria = Criteria.where("userId").is(memoryId);
        Query query = new Query(criteria);
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        if(chatMessages == null)
            return new LinkedList<>();
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }


    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        // 使用userId来存储对应用户的聊天记录
        Criteria criteria = Criteria.where("userId").is(memoryId);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        update.set("userId", memoryId); // 设置用户ID
        // 根据userId来更新或插入文档
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        // 使用userId来删除对应用户的聊天记录
        Criteria criteria = Criteria.where("userId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}
