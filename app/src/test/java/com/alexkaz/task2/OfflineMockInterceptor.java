package com.alexkaz.task2;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OfflineMockInterceptor implements Interceptor {

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json");

    private int code = 200;
    private String responseBody = "";
    private String errorBody = "";

    public OfflineMockInterceptor(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = new Response.Builder()
                .body(ResponseBody.create(MEDIA_JSON, responseBody))
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .message(errorBody)
                .code(code)
                .build();

        return response;
    }

    public OfflineMockInterceptor setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public OfflineMockInterceptor setCode(int code) {
        this.code = code;
        return this;
    }

    public OfflineMockInterceptor setErrorBody(String errorBody) {
        this.errorBody = errorBody;
        return this;
    }
}
