package com.cocotalk.chat.websocket.interceptor;

import com.cocotalk.chat.utils.JwtUtil;
import com.cocotalk.chat.utils.logging.ChannelLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// import com.cocotalk.chat.utils.logging.ChannelLogger;


@Slf4j
@Component
@RequiredArgsConstructor
public class InboundChannelInterceptor implements ChannelInterceptor {
    private final ChannelLogger channelLogger;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (command == StompCommand.CONNECT) {
            String accessToken = accessor.getFirstNativeHeader("X-ACCESS-TOKEN");
            JwtUtil.validateToken(accessToken);
        } else if (command == StompCommand.SEND) { // 넣을까 말까
            String accessToken = accessor.getFirstNativeHeader("X-ACCESS-TOKEN");
            JwtUtil.validateToken(accessToken);
        } else if (command == StompCommand.DISCONNECT) {
            String accessToken = accessor.getFirstNativeHeader("X-ACCESS-TOKEN");
            JwtUtil.validateToken(accessToken);
        }
        channelLogger.loggingMessage(message);
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        channelLogger.loggingMessage(message);
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        channelLogger.loggingMessage(message);
        log.info(String.format("<----------------------------------- %s ----------------------------------->",
                Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        // log.info(channelLogger.getPath());
        return ChannelInterceptor.super.preReceive(channel);
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        channelLogger.loggingMessage(message);
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        channelLogger.loggingMessage(message);
        log.info(String.format("<----------------------------------- %s ----------------------------------->",
                Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
}
