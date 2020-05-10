package kr.study.response.exception;

import kr.study.response.response.CommonResponse;
import kr.study.response.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-1, "Exception Error", request.getRequestURL().toString());
    }

    @ExceptionHandler(CMemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse memberNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(-11, "Not Found Member data!", request.getRequestURL().toString());
    }
}
