package kr.study.response.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(0, "Success!");
//    FAIL(-1, "실패하였습니다.");

    private int code;
    private String message;
}
