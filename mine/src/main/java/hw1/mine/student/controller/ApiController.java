package hw1.mine.student.controller;


import hw1.mine.restapi.Message;
import hw1.mine.restapi.RestResponse;
import hw1.mine.student.model.CreateStudent;
import hw1.mine.student.model.Student;
import hw1.mine.student.model.UpdateStudent;
import hw1.mine.student.service.StudentService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final StudentService studentService;

    @Autowired
    public ApiController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<RestResponse> find(@PathVariable Long id) {
        RestResponse<Object> restResponse = new RestResponse<>();
        Optional<Student> student = studentService.find(id);
        if (student.isPresent()) {
            restResponse = RestResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message(Message.READ_STUDENTS.label())
                    .data(student)
                    .build();
        } else {
            restResponse = RestResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(Message.NOT_FOUND_STUDENTS.label())
                    .build();
        }

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PutMapping("/students") // id, name, dept 입력
    public ResponseEntity<RestResponse> update(@RequestBody @Valid UpdateStudent student) {
        studentService.update(student);
        return ResponseEntity.ok(
                RestResponse.builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message(Message.UPDATE_STUDENTS.label())
                        .build()
        );
    }

    @PostMapping("/students") // name, dept 입력
    public ResponseEntity<RestResponse> create(@RequestBody @Valid CreateStudent student) {
        studentService.save(student);
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse = RestResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(Message.CREATE_STUDENTS.label())
                .build();

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<RestResponse> delete(@PathVariable Long id) {
        studentService.delete(id);
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse = RestResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(Message.DELETE_STUDENTS.label())
                .build();

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }


}
