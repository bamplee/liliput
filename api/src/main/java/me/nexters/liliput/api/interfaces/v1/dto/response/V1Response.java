package me.nexters.liliput.api.interfaces.v1.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class V1Response<T> {
    private T data;
    private int status;
    private int code;
    private String message;

    public V1Response(T body) {
        this.data = body;
    }
}