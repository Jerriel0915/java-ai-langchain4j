package cn.cdut.ai.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages") // 指定文档名称chat_messages
public class ChatMessages {
    //唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id
    private ObjectId messageId;

    // 存储用户ID
    private Long userId;

    //存储当前聊天记录列表的json字符串
    private String content;
}
