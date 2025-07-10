package hw1.mine.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hw1.mine.student.model.Student;
import hw1.mine.student.repository.StudentRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void afterEach() {
        studentRepository.clearAll();
    }

    @Test
    @DisplayName("create 성공")
    void createUser() throws Exception {
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"진규\", \"dept\": \"컴공\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("학생 추가 성공"))
                .andDo(print());
    }

    @Test
    @DisplayName("create 실패")
    void createUserFail() throws Exception {
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"진규\"}"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("update 성공")
    void updateUser() throws Exception {
        // given
        final String url = "/students";
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        String JSONContent = String.format("{\"id\": \"%d\", \"name\": \"형진\", \"dept\": \"컴공\"}",
                savedStudent.getId());

        // when
        final ResultActions result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONContent)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("학생 업데이트 성공"))
                .andDo(print());
        Student updatedStudent = studentRepository.findById(savedStudent.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        assertThat(updatedStudent.getName()).isEqualTo("형진");
        assertThat(updatedStudent.getDept()).isEqualTo("컴공");
    }

    @Test
    @DisplayName("update 실패")
    void updateUserFail() throws Exception {
        // given
        final String url = "/students";
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        String JSONContent = "{\"id\": \"0\", \"name\": \"형진\", \"dept\": \"컴공\"}";

        // when
        final ResultActions result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONContent)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("해당 학생 없음"))
                .andDo(print());
        Student updatedStudent = studentRepository.findById(savedStudent.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        assertThat(updatedStudent.getName()).isEqualTo("진규");
        assertThat(updatedStudent.getDept()).isEqualTo("컴공");
    }

    @Test
    @DisplayName("read 성공")
    void readUser() throws Exception {
        // given
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        final String url = String.format("/students/%d", savedStudent.getId());

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("학생 조회 성공"))
                .andExpect(jsonPath("$.data.name").value("진규"))
                .andExpect(jsonPath("$.data.dept").value("컴공"))
                .andDo(print());
    }

    @Test
    @DisplayName("read 실패")
    void readUserFail() throws Exception {
        // given
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        final String url = "/students/3";

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("학생 조회 실패"))
                .andDo(print());
    }

    @Test
    @DisplayName("delete 성공")
    void deleteUser() throws Exception {
        // given
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        final String url = String.format("/students/%d", savedStudent.getId());

        // when
        final ResultActions result = mockMvc.perform(delete(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("학생 제거 성공"))
                .andDo(print());
        Optional<Student> updatedStudent = studentRepository.findById(savedStudent.getId());
        assertFalse(updatedStudent.isPresent());
    }

    @Test
    @DisplayName("delete 실패")
    void deleteUserFail() throws Exception {
        // given
        Student savedStudent = new Student();
        savedStudent.setName("진규");
        savedStudent.setDept("컴공");
        studentRepository.save(savedStudent);
        final String url = "/students/3";

        // when
        final ResultActions result = mockMvc.perform(delete(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 학생 없음"))
                .andDo(print());
        Student updatedStudent = studentRepository.findById(savedStudent.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
