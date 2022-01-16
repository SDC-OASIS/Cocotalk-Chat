package com.cocotalk.chat.service;

import com.cocotalk.chat.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomMemberService {
    private final RoomMemberRepository roomMemberRepository;
}
