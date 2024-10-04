package com.sb.simpletask.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseDTO implements Serializable {
    private String message;
    private String resultType;
    private boolean success;
    private Map<String, Object> resultMap;
}
