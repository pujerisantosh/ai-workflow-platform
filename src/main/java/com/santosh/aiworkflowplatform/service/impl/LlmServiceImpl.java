package com.santosh.aiworkflowplatform.service.impl;

import com.santosh.aiworkflowplatform.service.LlmService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmServiceImpl implements LlmService {

    private final ChatClient chatClient;

    @Override
    public String generateResponse(String prompt) {

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}