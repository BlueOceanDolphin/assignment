package org.koreait.commons.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONData<T> {
    private boolean success;
    private String message;
    private T data;
}
