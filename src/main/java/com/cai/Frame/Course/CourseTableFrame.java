package com.cai.Frame.Course;

import com.cai.dao.CourseMapper;
import com.cai.pojo.Course;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.cai.Test.getsqlSession.getSqlSession;

public class CourseTableFrame extends JFrame {
    private final List<Course> courseList;

    public CourseTableFrame() throws IOException {
        setTitle("�γ���Ϣ��");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);
        CourseMapper courseMapper = getSqlSession(CourseMapper.class);
        this.courseList = courseMapper.FindAllCourse();

        String[] columnNames = {"�γ� ID", "�γ���", "���ȼ�"};
        Object[][] data = new Object[courseList.size()][columnNames.length];
        for (int i = 0; i < courseList.size(); i++) {
            data[i][0] = courseList.get(i).getCourseID();
            data[i][1] = courseList.get(i).getCourseName();
            data[i][2] = courseList.get(i).getPriority(); // �޸�������ƥ������
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
