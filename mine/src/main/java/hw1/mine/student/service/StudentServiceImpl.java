package hw1.mine.student.service;

import hw1.mine.student.exception.CustomException;
import hw1.mine.student.exception.ErrorCode;
import hw1.mine.student.model.CreateStudent;
import hw1.mine.student.model.Student;
import hw1.mine.student.model.UpdateStudent;
import hw1.mine.student.repository.StudentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(CreateStudent student) {
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setDept(student.getDept());
        studentRepository.save(newStudent);
    }

    @Override
    public Optional<Student> find(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void update(UpdateStudent newStudent) {
        Student student = studentRepository.findById(newStudent.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_STUDENT));
        student.setName(newStudent.getName());
        student.setDept(newStudent.getDept());
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.deleteById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_STUDENT));
    }
}
