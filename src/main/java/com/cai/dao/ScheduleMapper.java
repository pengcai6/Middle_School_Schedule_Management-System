package com.cai.dao;

import com.cai.pojo.Schedule;
import com.cai.pojo.ScheduleEntry;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

public interface ScheduleMapper {

    @Select("SELECT * FROM Schedule")
    List<Schedule> getAllSchedules();

    @Update("UPDATE Schedule SET ClassID = #{classID}, CourseID = #{courseID}, TeacherID = #{teacherID}, DayOfWeek = #{dayOfWeek}, TimeSlotID = #{timeSlotID} WHERE ScheduleID = #{scheduleID}")
    void updateSchedule(Schedule schedule);

    @Insert("INSERT INTO Schedule (ClassID, CourseID, TeacherID, DayOfWeek, TimeSlotID) VALUES (#{classID}, #{courseID}, #{teacherID}, #{dayOfWeek}, #{timeSlotID})")
    @Options(useGeneratedKeys = true, keyProperty = "scheduleID")
    void insertSchedule(Schedule schedule);

    @Delete("DELETE FROM Schedule WHERE ScheduleID = #{scheduleID}")
    void deleteSchedule(int scheduleID);
    @Select("CALL GenerateClassSchedule(#{classId})")
    List<ScheduleEntry> getClassSchedule(int classId);
    @Select("select timeSlotID,courseName,teacherName,dayOfWeek from schedule join test_manage_system.class c on c.ClassID = schedule.ClassID join test_manage_system.course c2 on c2.CourseID = schedule.CourseID join test_manage_system.teacher t on t.TeacherID = schedule.TeacherID where ClassName=#{className}")
    List<ScheduleEntry> getAllSchedulesByClassName(String className);
    @Select("CALL GenerateTeacherSchedule(#{teacherId})")
    List<ScheduleEntry> getTeacherSchedule(int teacherId);
    @Select("select timeSlotID,courseName,ClassName,dayOfWeek from schedule join test_manage_system.class c on c.ClassID = schedule.ClassID join test_manage_system.course c2 on c2.CourseID = schedule.CourseID join test_manage_system.teacher t on t.TeacherID = schedule.TeacherID where TeacherName=#{teacherName}")
    List<ScheduleEntry> getAllSchedulesByTeacherName(String teacherName);

    @Select("SELECT COUNT(*) FROM Schedule WHERE ClassID = #{classId} AND TeacherID = #{teacherId}")
    int countTeacherCoursesInClass(@Param("teacherId") int teacherId, @Param("classId") int classId);

    @Select("SELECT COUNT(*) FROM Schedule WHERE ClassID = #{classId} AND CourseID = #{courseId} AND DayOfWeek = #{dayOfWeek}")
    int countCourseInClassOnDay(@Param("classId") int classId, @Param("courseId") int courseId, @Param("dayOfWeek") int dayOfWeek);


    @Select("SELECT COUNT(*) FROM Schedule WHERE ClassID = #{classId} AND TimeSlotID = #{timeSlotId} AND DayOfWeek = #{dayOfWeek}")
    int countClassCourseAtTimeSlot(@Param("classId") int classId, @Param("timeSlotId") int timeSlotId, @Param("dayOfWeek") int dayOfWeek);


    @Select("select count(*) from test_manage_system.schedule where ClassID=#{classID} and courseID=#{courseID} ")
    // 计算班级的某门课程已安排的节数
    int countClassCourse(int classID, int courseID);


    @Select("SELECT COUNT(*) FROM schedule WHERE teacherID = #{teacherID} AND classID = #{classID} AND courseID = #{courseID}")
    boolean hasTeacherTaughtCourseInClass(int teacherID, int classID, int courseID);

    @Select("SELECT COUNT(*) > 0 FROM schedule WHERE teacherID = #{teacherID} AND classID = #{classID} AND courseID != #{courseID}")
    boolean hasTeacherTaughtOtherCourseInClass(int teacherID, int classID);

}
