package com.cocotalk.chat.domain.entity.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class InviteMessage extends ChatMessage {
    List<Long> inviteeIds;
}
