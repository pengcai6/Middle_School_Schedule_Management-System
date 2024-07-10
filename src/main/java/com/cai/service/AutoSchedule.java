package com.cai.service;

import com.cai.dao.ClassMapper;
import com.cai.dao.CourseMapper;
import com.cai.dao.ScheduleMapper;
import com.cai.dao.TeacherMapper;
import com.cai.pojo.Class;
import com.cai.pojo.Course;
import com.cai.pojo.Schedule;
import com.cai.pojo.Teacher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

public class AutoSchedule {

    private static final Logger logger = LoggerFactory.getLogger(AutoSchedule.class);
    private static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    private List<Course> courses; // 课程列表
    private List<Teacher> teachers; // 教师列表

    // 安排课程
    public AutoSchedule() {
        loadResources(); // 加载课程和教师列表

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = session.getMapper(ScheduleMapper.class);
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            ClassMapper classMapper = session.getMapper(ClassMapper.class);

            // 按优先级排序课程
            List<Class> classList = classMapper.FindAllClass();
            logger.info("Loaded {} classes", classList.size());
            courses.sort(Comparator.comparingInt(Course::getPriority).reversed());

            // 记录每个班级的每门课程已经被分配的节数
            int[][] classCourseMap = new int[classList.size() + 1][courses.size() + 1];
            // 记录每个教师是否已经在某个班级教授了某门课程
            boolean[][][] teacherClassCourseMap = new boolean[teachers.size() + 1][classList.size() + 1][courses.size() + 1];
            boolean[][] teacherClassMap = new boolean[teachers.size() + 1][classList.size() + 1];

            for (Class aClass : classList) {
                logger.info("Scheduling for class {}", aClass.getClassID());
                for (int timeSlot = 1; timeSlot <= 6; timeSlot++) { // 假设每天6节课
                for (int day = 1; day <= 5; day++) { // 假设一周5天
                        // 检查是否在该时间段该班级已经安排了其他课程
                        int classCourseAtTimeSlot = scheduleMapper.countClassCourseAtTimeSlot(aClass.getClassID(), timeSlot, day);
                        if (classCourseAtTimeSlot > 0) {
                            continue; // 已经有课程安排，跳过
                        }

                        for (Course course : courses) {
                            int courseID = course.getCourseID();

                            if (classCourseMap[aClass.getClassID()][courseID] >= course.getPriority()) {
                                continue; // 如果该课程的节数已经安排完毕，跳过
                            }

                            boolean scheduled = false; // 标记是否已安排课程

                            // 优先考虑已经教授过该课程的教师
                            for (Teacher teacher : teachers) {
                                int teacherId = teacher.getTeacherID();
                                int classId = aClass.getClassID();

                                if (teacherClassMap[teacherId][classId]) { // 检测该老师在该班级是否教授过课程
                                    if (teacherClassCourseMap[teacherId][classId][courseID]) { // 优先考虑已经教授过该课程的教师
                                        // 检查教师是否在当前时间段有冲突
                                        int check = courseMapper.CourserCheck(teacherId, timeSlot, day); // 是否有时间
                                        if (check == 0) {
                                            // 没有冲突，安排课程
                                            Schedule entry = new Schedule();
                                            entry.setCourseID(course.getCourseID());
                                            entry.setTeacherID(teacherId);
                                            entry.setClassID(aClass.getClassID());
                                            entry.setTimeSlotID(timeSlot);
                                            entry.setDayOfWeek(day);

                                            // 保存到schedule
                                            scheduleMapper.insertSchedule(entry);
                                            logger.info("Scheduled course {} for class {} with teacher {} at day {} slot {}", course.getCourseID(), aClass.getClassID(), teacherId, day, timeSlot);

                                            // 记录班级的课程已经被分配的节数
                                            classCourseMap[aClass.getClassID()][course.getCourseID()]++;

                                            // 记录教师已经在当前班级教授了课程
                                            teacherClassCourseMap[teacherId][aClass.getClassID()][course.getCourseID()] = true;
                                            // 记录该班级该老师已经教授过课程
                                            teacherClassMap[teacher.getTeacherID()][aClass.getClassID()] = true;
                                            scheduled = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!scheduled) { // 如果未安排，则检查其他教师
                                for (Teacher teacher : teachers) {
                                    int teacherId = teacher.getTeacherID();
                                    int classId = aClass.getClassID();

                                    if (!teacherClassMap[teacherId][classId]) { // 该老师在该班级没有教授过课程
                                        // 检查教师是否在当前时间段有冲突
                                        int check = courseMapper.CourserCheck(teacherId, timeSlot, day);
                                        if (check == 0) {
                                            // 没有冲突，安排课程
                                            Schedule entry = new Schedule();
                                            entry.setCourseID(course.getCourseID());
                                            entry.setTeacherID(teacherId);
                                            entry.setClassID(aClass.getClassID());
                                            entry.setTimeSlotID(timeSlot);
                                            entry.setDayOfWeek(day);

                                            // 保存到schedule
                                            scheduleMapper.insertSchedule(entry);
                                            logger.info("Scheduled course {} for class {} with teacher {} at day {} slot {}", course.getCourseID(), aClass.getClassID(), teacherId, day, timeSlot);

                                            // 记录班级的课程已经被分配的节数
                                            classCourseMap[aClass.getClassID()][course.getCourseID()]++;

                                            // 记录教师已经在当前班级教授了课程
                                            teacherClassCourseMap[teacherId][aClass.getClassID()][course.getCourseID()] = true;
                                            // 记录该班级该老师已经教授过课程
                                            teacherClassMap[teacher.getTeacherID()][aClass.getClassID()] = true;
                                            scheduled = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (scheduled) {
                                break; // 如果课程已经安排，跳出当前课程循环
                            }
                        }
                    }
                }
            }

            session.commit(); // 提交事务
            logger.info("Scheduling completed successfully");
        } catch (Exception e) {
            logger.error("Error during scheduling: {}", e.getMessage(), e);
        }
    }

    // 初始化课程列表和教师列表
    private void loadResources() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
            this.courses = courseMapper.FindAllCourse();
            this.teachers = teacherMapper.FindAllTeacher(); // 获取所有教师
            logger.info("Loaded {} courses and {} teachers", courses.size(), teachers.size());
        }
    }
}
