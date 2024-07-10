package com.cai.dao;

import com.cai.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {
    @Select("select *from test_manage_system.course")
    List<Course> FindAllCourse();

    @Insert("INSERT INTO Course (CourseName, Priority) VALUES (#{courseName}, #{priority})")
    void insertCourse(@Param("courseName") String courseName, @Param("priority") int priority);

    @Delete("delete from test_manage_system.course where courseID=#{courseID}")
    void deleteByCourseID(int courseID);

    @Delete("delete from test_manage_system.course where CourseName=#{nameText}")
    void deleteByCourseName(String nameText);

    @Select("select *from course where CourseID =#{i}")
    List<Course> getCourseById(int i);

    @Select("select *from course where CourseName =#{searchText}")
    List<Course> getCourseByName(String searchText);

    @Select("SELECT COUNT(*) FROM Schedule WHERE TeacherID = #{teacherId} AND TimeSlotID = #{timeSlot} AND DayOfWeek = #{weeklyHours};")
    int CourserCheck(@Param("teacherId") int teacherId, @Param("timeSlot") int timeSlot, @Param("weeklyHours") int weeklyHours);

    @Update("update course set CourseName=#{CourseName} ,Priority=#{Priority} where CourseID=#{CourseID}")
    void updateCourse(Course updatedCourse);
}
