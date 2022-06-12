package com.example.migrationwithpostgres.data.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseWrapper {

    private Object data;
}
