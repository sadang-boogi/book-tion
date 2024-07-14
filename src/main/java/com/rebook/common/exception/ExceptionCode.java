package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOOK_ID("NOT_FOUND", null, "요청하신 책은 존재하지 않습니다."),
    NOT_FOUND_HASHTAG_ID("NOT_FOUND", null, "요청하신 해쉬태그는 존재하지 않습니다."),
    NOT_FOUND_USER_ID("NOT_FOUND", null, "유저가 존재하지 않습니다."),
    TOKEN_INVALID("TOKEN_ERROR", "로그인 실패", "로그인에 실패했습니다. 다시 로그인 해주세요."),
    LOGIN_REQUIRED("LOGIN_REQUIRED", "로그인 필요", "이 기능을 이용하려면 로그인이 필요합니다."),

    EXCEED_IMAGE_CAPACITY("IMAGE_ERROR", null, "업로드 가능한 이미지 용량을 초과했습니다."),
    NULL_IMAGE("IMAGE_ERROR", null, "업로드한 이미지 파일이 NULL입니다."),
    EXCEED_IMAGE_LIST_SIZE("IMAGE_ERROR", null, "업로드 가능한 이미지 개수를 초과했습니다."),
    INVALID_IMAGE_URL("IMAGE_ERROR", null, "요청한 이미지 URL의 형식이 잘못되었습니다."),
    INVALID_IMAGE_PATH("IMAGE_ERROR", null, "이미지를 저장할 경로가 올바르지 않습니다."),
    FAIL_IMAGE_NAME_HASH("IMAGE_ERROR", null, "이미지 이름을 해싱하는 데 실패했습니다."),
    INVALID_IMAGE("IMAGE_ERROR", null, "올바르지 않은 이미지 파일입니다.");

    private final String code;
    private final String title;
    private final String message;
}
