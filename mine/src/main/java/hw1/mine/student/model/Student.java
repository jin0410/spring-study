package hw1.mine.student.model;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private String name;
    private String dept;

//    public Student(String name, String dept) {
//        this.name = name;
//        this.dept = dept;
//    }
//
//    public Student(Long id, String name, String dept) {
//        this.id = id;
//        this.name = name;
//        this.dept = dept;
//    }
}
