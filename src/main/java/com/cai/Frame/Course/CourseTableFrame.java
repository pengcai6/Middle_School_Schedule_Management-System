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
        setTitle("课程信息表");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);
        CourseMapper courseMapper = getSqlSession(CourseMapper.class);
        this.courseList = courseMapper.FindAllCourse();

        String[] columnNames = {"课程 ID", "课程名", "优先级"};
        Object[][] data = new Object[courseList.size()][columnNames.length];
        for (int i = 0; i < courseList.size(); i++) {
            data[i][0] = courseList.get(i).getCourseID();
            data[i][1] = courseList.get(i).getCourseName();
            data[i][2] = courseList.get(i).getPriority(); // 修改索引以匹配列名
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
