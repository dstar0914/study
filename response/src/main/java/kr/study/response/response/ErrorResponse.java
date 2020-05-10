package kr.study.response.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private int code;
    private String errorMessage;
    private String path;
}
