package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageDto implements Serializable {
    private String message;
}
