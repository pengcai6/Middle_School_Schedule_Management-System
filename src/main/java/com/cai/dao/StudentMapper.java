package com.cai.dao;

import com.cai.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Select("select * from test_manage_system.student")
    List<Student> FindAllStudent();

    @Insert("INSERT INTO test_manage_system.student (StudentName, ClassID) VALUES (#{studentName}, #{classID})")
    void insertStudent(@Param("studentName") String studentName, @Param("classID") int classID);

    @Delete("delete  from test_manage_system.student where StudentId=#{studentID}")
    void deleteByStudentID(int studentID);
    @Delete("delete  from test_manage_system.student where StudentName=#{studentName}")
    void deleteByStudentName(String StudentName);
    @Select("select * from test_manage_system.student where StudentID= #{i}")
    List<Student> getStudentById(int i);
    @Select("select * from test_manage_system.student where StudentName= #{searchText}")
    List<Student> getStudentByName(String searchText);

    @Update("update test_manage_system.student set StudentName=#{StudentName},ClassID=#{ClassID} where StudentID=#{StudentID}")
    void updateStudent(Student updatedStudent);
}
