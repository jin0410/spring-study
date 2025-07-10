package hw1.mine.restapi;

public enum Message {
    READ_STUDENTS("학생 조회 성공"),
    NOT_FOUND_STUDENTS("학생 조회 실패"),
    UPDATE_STUDENTS("학생 업데이트 성공"),
    FAIL_UPDATE_STUDENTS("학생 업데이트 실패"),
    CREATE_STUDENTS("학생 추가 성공"),
    FAIL_CREATE_STUDENTS("학생 추가 실패"),
    DELETE_STUDENTS("학생 제거 성공"),
    FAIL_DELETE_STUDENTS("학생 제거 실패"),
    WRONG_ARGUMENTS("유효하지 않은 요청");

    private final String label;

    Message(String label) {
        this.label = label;
    }

    public String label() {
        return this.label;
    }
}
