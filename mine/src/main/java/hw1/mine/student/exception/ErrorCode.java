package hw1.mine.student.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NO_SUCH_STUDENT(HttpStatus.BAD_REQUEST, "해당 학생 없음");

    private final HttpStatus httpStatus;
    private final String label;

    ErrorCode(HttpStatus httpstatus, String label) {
        this.httpStatus = httpstatus;
        this.label = label;
    }

    public HttpStatus status() {
        return this.httpStatus;
    }

    public String label() {
        return this.label;
    }
}