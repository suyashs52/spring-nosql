package com.mongo.nosql.service;

import com.mongo.nosql.entity.Student;
import com.mongo.nosql.repository.DepartmentRepository;
import com.mongo.nosql.repository.StudentRepository;
import com.mongo.nosql.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SubjectRepository subjectRepository;


    public Student createStudent(Student student) {

        if(student.getDepartment()!=null){
           departmentRepository.save(student.getDepartment());
        }

        if(student.getSubjects()!=null && student.getSubjects().size()>0) {
            subjectRepository.saveAll(student.getSubjects());
        }

        Student student1 = studentRepository.save(student);
        return  student1;
    }

    public Student getStudentById(String id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()) return student.get();
        return new Student();
    }

    public List<Student> getAllStudent() {
      return   studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        if(student.getId()==null){
            throw new RuntimeException("Id expected");
        }
        return studentRepository.save(student);
    }

    public String deleteStudent(String id) {
         studentRepository.deleteById(id);

         return "student deleted with id: "+id;

    }

    public List<Student> getStudentByName(String name) {
      //  return studentRepository.findByName(name);
        return studentRepository.getByName(name);
    }

    public List<Student> getStudentByNamendEmail(String name, String email) {
            return studentRepository.findByNameAndEmail(name,email);
    }

    public List<Student> getStudentByNameOrEmail(String name, String email) {
        return studentRepository.findByNameOrEmail(name,email);

    }

    public List<Student> getStudentByPagination(int page_number, int size) {
        //page number is 0 based index
        //skip is (pageno-1)*pagesize this ll done by mongodb
        Pageable pageable= PageRequest.of(page_number-1,size);

        Page<Student> all = studentRepository.findAll(pageable);
       return all.getContent();
    }

    public List<Student> getAllStudentAsc() {

        Sort sort= Sort.by(Sort.Direction.ASC,"name");
        return  studentRepository.findAll(sort);
    }

    public List<Student> getStudentByDepartmentName(String name) {
        return studentRepository.findByDepartmentDepartmentName(name);
    }

    public List<Student> getStudentBySubjectName(String name) {

        return studentRepository.findBySubjectsSubjectName(name);

    }

    public List<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmailIsLike(email);

    }

    public List<Student> getStudentByDepartmentId(String id) {

        return studentRepository.findByDepartmentId(id);
    }
}
