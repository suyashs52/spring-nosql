package com.mongo.nosql.controller;

import com.mongo.nosql.entity.Student;
import com.mongo.nosql.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        //create document
        return studentService.createStudent(student);
    }


    @GetMapping
    public List<Student> getAllStudent() {
        //get document
        return studentService.getAllStudent();
    }

    @GetMapping("/asc")
    public List<Student> getAllStudentAsc() {
        //get document
        return studentService.getAllStudentAsc();
    }


    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        //get document
        return studentService.getStudentById(id);
    }

    @GetMapping("/name/{name}")
    public List<Student> getStudentByName(@PathVariable String name) {
        //get document
        Set<Integer> set=new TreeSet<>();
         Integer[] arr= (Integer[]) set.toArray();
        return studentService.getStudentByName(name);
    }

    @GetMapping("/department/{name}")
    public List<Student> getStudentByDepartmentName(@PathVariable String name) {
        //get document
        return studentService.getStudentByDepartmentName(name);
    }

    @GetMapping("/department/id/{id}")
    public List<Student> getStudentByDepartmentId(@PathVariable String id) {
        //get document
        return studentService.getStudentByDepartmentId(id);
    }


    @GetMapping("/subject/{name}")
    public List<Student> getStudentBySubjectName(@PathVariable String name) {
        //get document
        return studentService.getStudentBySubjectName(name);
    }

    @GetMapping("/email/{email}")
    public List<Student> getStudentByEmailLike(@PathVariable String email) {
        //get document by email id
        return studentService.getStudentByEmail(email);
    }


    @GetMapping("/name/{name}/{email}")
    public List<Student> getStudentByNameNdEmail(@PathVariable String name, @PathVariable String email) {
        //get document
        return studentService.getStudentByNamendEmail(name, email);
    }

    @GetMapping("/name/{name}/email/{email}")
    public List<Student> getStudentByNameOrEmail(@PathVariable String name, @PathVariable String email) {
        //get document
        return studentService.getStudentByNameOrEmail(name, email);
    }


    @GetMapping("/page/{number}/size/{size}")
    public List<Student> getStudentWithPagination(@PathVariable int number, @PathVariable int size) {
        //get document
        return studentService.getStudentByPagination(number, size);
    }


    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        //update student
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable String id) {
        //delete student
        return studentService.deleteStudent(id);
    }
}
