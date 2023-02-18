package com.mongo.nosql.repository;

import com.mongo.nosql.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByName(String name);

    List<Student> findByNameAndEmail(String name, String email);

    //if you change to Student and return is muliple value and this ll throw exception
    List<Student> findByNameOrEmail(String name, String email);


    List<Student> findByDepartmentDepartmentName(String name);

    List<Student> findBySubjectsSubjectName(String name);

    List<Student> findByEmailIsLike(String email);

    List<Student> findByNameStartsWith(String name);

    List<Student> findByDepartmentId(String deptId);


    @Query("\"name\":\"?0\"}")
    List<Student> getByName(String name);
}
