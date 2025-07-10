package hw1.mine.student.repository;

import hw1.mine.student.model.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    private static Map<Long, Student> store = new HashMap<>();
    private static long sequence = 0L;

    public void save(Student student) {
        student.setId(++sequence);
        store.put(student.getId(), student);
    }

    public Optional<Student> findById(Long studentId) {
        return Optional.ofNullable(store.get(studentId));
    }

    public Optional<Student> deleteById(Long studentId) {
        return Optional.ofNullable(store.remove(studentId));
    }

    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearAll() {
        store.clear();
    }
}
