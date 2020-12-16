package com.test.first.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ComUitl {
    public static String mapToJson(Map map) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return  om.writeValueAsString(map);
    }
}
