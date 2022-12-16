package com.project.novelnet.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageService {
    String message = "";
    String href = "";

    public MessageService(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
