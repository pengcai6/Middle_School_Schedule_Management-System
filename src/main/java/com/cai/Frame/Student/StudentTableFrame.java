package com.cai.Frame.Student;

import com.cai.dao.StudentMapper;
import com.cai.pojo.Student;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.cai.Test.getsqlSession.getSqlSession;

public class StudentTableFrame extends JFrame {

    public StudentTableFrame() throws IOException {
        setTitle("学生信息表");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);

        // 获取学生数据
        StudentMapper studentMapper = getSqlSession(StudentMapper.class);
        List<Student> studentList = studentMapper.FindAllStudent();

        // 定义表格列名
        String[] columnNames = {"学生 ID", "姓名", "班级 ID"};

        // 填充表格数据
        Object[][] data = new Object[studentList.size()][3];
        for (int i = 0; i < studentList.size(); i++) {
            data[i][0] = studentList.get(i).getStudentID();
            data[i][1] = studentList.get(i).getStudentName();
            data[i][2] = studentList.get(i).getClassID();  // 添加班级 ID 列
        }

        // 创建表格
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // 设置布局并添加组件
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // 显示窗口
        setVisible(true);
    }
}
