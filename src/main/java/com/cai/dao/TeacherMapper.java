package com.cai.dao;

import com.cai.pojo.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherMapper {
    @Select("select *from test_manage_system.teacher")
    List<Teacher> FindAllTeacher();
    @Insert("insert into test_manage_system.teacher (TeacherName) values (#{teacherName})" )
    void insertTeacher(String teacherName);
    @Delete("delete from test_manage_system.teacher where TeacherID =#{teacherID}")
    void deleteByTeacherID(int teacherID);
    @Delete("delete  from test_manage_system.teacher where TeacherName=#{nameText}")
    void deleteByTeacherName(String nameText);
    @Select("select *from test_manage_system.teacher where TeacherID=#{i}")
    List<Teacher> getTeacherById(int i);
    @Select("select *from test_manage_system.teacher where TeacherName=#{searchText}")
    List<Teacher> getTeacherByName(String searchText);
    @Update("update test_manage_system.teacher set TeacherName=#{TeacherName} where TeacherID=#{TeacherID}")
    void updateTeacher(Teacher updatedTeacher);
}
