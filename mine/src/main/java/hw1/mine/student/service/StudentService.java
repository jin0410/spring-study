package hw1.mine.student.service;

import hw1.mine.student.model.Student;
import hw1.mine.student.model.UpdateStudent;
import java.util.Optional;

public interface StudentService {
    void save(Student student);

    Optional<Student> find(Long id);

    void update(UpdateStudent student);

    void delete(Long id);
}
