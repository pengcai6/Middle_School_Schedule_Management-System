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
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    private List<Course> courses; // �γ��б�
    private List<Teacher> teachers; // ��ʦ�б�

    // ���ſγ�
    public AutoSchedule() {
        loadResources(); // ���ؿγ̺ͽ�ʦ�б�

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper scheduleMapper = session.getMapper(ScheduleMapper.class);
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            ClassMapper classMapper = session.getMapper(ClassMapper.class);

            // �����ȼ�����γ�
            List<Class> classList = classMapper.FindAllClass();
            logger.info("Loaded {} classes", classList.size());
            courses.sort(Comparator.comparingInt(Course::getPriority).reversed());

            // ��¼ÿ���༶��ÿ�ſγ��Ѿ�������Ľ���
            int[][] classCourseMap = new int[classList.size() + 1][courses.size() + 1];
            // ��¼ÿ����ʦ�Ƿ��Ѿ���ĳ���༶������ĳ�ſγ�
            boolean[][][] teacherClassCourseMap = new boolean[teachers.size() + 1][classList.size() + 1][courses.size() + 1];
            boolean[][] teacherClassMap = new boolean[teachers.size() + 1][classList.size() + 1];

            for (Class aClass : classList) {
                logger.info("Scheduling for class {}", aClass.getClassID());
                for (int timeSlot = 1; timeSlot <= 6; timeSlot++) { // ����ÿ��6�ڿ�
                for (int day = 1; day <= 5; day++) { // ����һ��5��
                        // ����Ƿ��ڸ�ʱ��θð༶�Ѿ������������γ�
                        int classCourseAtTimeSlot = scheduleMapper.countClassCourseAtTimeSlot(aClass.getClassID(), timeSlot, day);
                        if (classCourseAtTimeSlot > 0) {
                            continue; // �Ѿ��пγ̰��ţ�����
                        }

                        for (Course course : courses) {
                            int courseID = course.getCourseID();

                            if (classCourseMap[aClass.getClassID()][courseID] >= course.getPriority()) {
                                continue; // ����ÿγ̵Ľ����Ѿ�������ϣ�����
                            }

                            boolean scheduled = false; // ����Ƿ��Ѱ��ſγ�

                            // ���ȿ����Ѿ����ڹ��ÿγ̵Ľ�ʦ
                            for (Teacher teacher : teachers) {
                                int teacherId = teacher.getTeacherID();
                                int classId = aClass.getClassID();

                                if (teacherClassMap[teacherId][classId]) { // ������ʦ�ڸð༶�Ƿ���ڹ��γ�
                                    if (teacherClassCourseMap[teacherId][classId][courseID]) { // ���ȿ����Ѿ����ڹ��ÿγ̵Ľ�ʦ
                                        // ����ʦ�Ƿ��ڵ�ǰʱ����г�ͻ
                                        int check = courseMapper.CourserCheck(teacherId, timeSlot, day); // �Ƿ���ʱ��
                                        if (check == 0) {
                                            // û�г�ͻ�����ſγ�
                                            Schedule entry = new Schedule();
                                            entry.setCourseID(course.getCourseID());
                                            entry.setTeacherID(teacherId);
                                            entry.setClassID(aClass.getClassID());
                                            entry.setTimeSlotID(timeSlot);
                                            entry.setDayOfWeek(day);

                                            // ���浽schedule
                                            scheduleMapper.insertSchedule(entry);
                                            logger.info("Scheduled course {} for class {} with teacher {} at day {} slot {}", course.getCourseID(), aClass.getClassID(), teacherId, day, timeSlot);

                                            // ��¼�༶�Ŀγ��Ѿ�������Ľ���
                                            classCourseMap[aClass.getClassID()][course.getCourseID()]++;

                                            // ��¼��ʦ�Ѿ��ڵ�ǰ�༶�����˿γ�
                                            teacherClassCourseMap[teacherId][aClass.getClassID()][course.getCourseID()] = true;
                                            // ��¼�ð༶����ʦ�Ѿ����ڹ��γ�
                                            teacherClassMap[teacher.getTeacherID()][aClass.getClassID()] = true;
                                            scheduled = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!scheduled) { // ���δ���ţ�����������ʦ
                                for (Teacher teacher : teachers) {
                                    int teacherId = teacher.getTeacherID();
                                    int classId = aClass.getClassID();

                                    if (!teacherClassMap[teacherId][classId]) { // ����ʦ�ڸð༶û�н��ڹ��γ�
                                        // ����ʦ�Ƿ��ڵ�ǰʱ����г�ͻ
                                        int check = courseMapper.CourserCheck(teacherId, timeSlot, day);
                                        if (check == 0) {
                                            // û�г�ͻ�����ſγ�
                                            Schedule entry = new Schedule();
                                            entry.setCourseID(course.getCourseID());
                                            entry.setTeacherID(teacherId);
                                            entry.setClassID(aClass.getClassID());
                                            entry.setTimeSlotID(timeSlot);
                                            entry.setDayOfWeek(day);

                                            // ���浽schedule
                                            scheduleMapper.insertSchedule(entry);
                                            logger.info("Scheduled course {} for class {} with teacher {} at day {} slot {}", course.getCourseID(), aClass.getClassID(), teacherId, day, timeSlot);

                                            // ��¼�༶�Ŀγ��Ѿ�������Ľ���
                                            classCourseMap[aClass.getClassID()][course.getCourseID()]++;

                                            // ��¼��ʦ�Ѿ��ڵ�ǰ�༶�����˿γ�
                                            teacherClassCourseMap[teacherId][aClass.getClassID()][course.getCourseID()] = true;
                                            // ��¼�ð༶����ʦ�Ѿ����ڹ��γ�
                                            teacherClassMap[teacher.getTeacherID()][aClass.getClassID()] = true;
                                            scheduled = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (scheduled) {
                                break; // ����γ��Ѿ����ţ�������ǰ�γ�ѭ��
                            }
                        }
                    }
                }
            }

            session.commit(); // �ύ����
            logger.info("Scheduling completed successfully");
        } catch (Exception e) {
            logger.error("Error during scheduling: {}", e.getMessage(), e);
        }
    }

    // ��ʼ���γ��б�ͽ�ʦ�б�
    private void loadResources() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            CourseMapper courseMapper = session.getMapper(CourseMapper.class);
            TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
            this.courses = courseMapper.FindAllCourse();
            this.teachers = teacherMapper.FindAllTeacher(); // ��ȡ���н�ʦ
            logger.info("Loaded {} courses and {} teachers", courses.size(), teachers.size());
        }
    }
}
