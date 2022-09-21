package study.yk.kim.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.yk.kim.sample.dto.ErrResponseDto;
import study.yk.kim.sample.util.exception.AllApiException;
import study.yk.kim.sample.util.exception.InvalidArgumentPage;
import study.yk.kim.sample.util.exception.InvalidArgumentSize;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    // search
    @ExceptionHandler({InvalidArgumentPage.class
            , InvalidArgumentSize.class})
    public ResponseEntity<ErrResponseDto> SearchException(Exception e) {
        log.info(e.getMessage());
        ErrResponseDto responseDto = ErrResponseDto.builder()
                .status(400)
                .message(e.getMessage())
                .errorType("InvalidArgument")
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    // search
    @ExceptionHandler(AllApiException.class)
    public ResponseEntity<ErrResponseDto> AllApiException(Exception e) {
        log.info(e.getMessage());
        ErrResponseDto responseDto = ErrResponseDto.builder()
                .status(500)
                .message(e.getMessage())
                .errorType("InternalServerError")
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 400
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrResponseDto> BadRequestException(Exception e) {
        log.warn("400 error 발생 | {}", e);
        ErrResponseDto errResponseDto = ErrResponseDto.builder()
                .status(400)
                .message("잘못된 요청입니다. 다시 시도해주세요.")
                .errorType("BadRequestError")
                .build();
        return new ResponseEntity<>(errResponseDto, HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrResponseDto> handleAccessDeniedException(Exception e) {
        log.warn("401 error 발생 | {}", e);
        ErrResponseDto errResponseDto = ErrResponseDto.builder()
                .status(401)
                .message("권한이 없습니다.")
                .errorType("AccessDeniedError")
                .build();
        return new ResponseEntity<>(errResponseDto, HttpStatus.UNAUTHORIZED);
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrResponseDto> handleAll(Exception e) {
        log.error("500 error 발생 | {}", e);
        ErrResponseDto errResponseDto = ErrResponseDto.builder()
                .status(500)
                .message("서버에 문제가 발생하였습니다. 잠시후 다시 시도해주세요. 문제가 지속될 시 문의해주세요.")
                .errorType("InternalServerError")
                .build();
        return new ResponseEntity<>(errResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
