package com.example.migrationwithpostgres.data.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseWrapper {

    private int statusCode;
    private String message;
    private Object data;
}
