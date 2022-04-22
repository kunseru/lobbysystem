package com.github.decompilen.lobbysystem.service.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private final String key;
    private final String message;
}
