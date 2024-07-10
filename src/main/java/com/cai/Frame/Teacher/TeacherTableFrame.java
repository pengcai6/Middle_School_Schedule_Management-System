package com.cai.Frame.Teacher;

import com.cai.dao.TeacherMapper;
import com.cai.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.cai.Test.getsqlSession.getSqlSession;

public class TeacherTableFrame extends JFrame {

    public TeacherTableFrame() throws IOException {
        setTitle("老师信息表");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);

        // 获取老师数据
        TeacherMapper TeacherMapper = getSqlSession(TeacherMapper.class);
        List<Teacher> TeacherList = TeacherMapper.FindAllTeacher();

        // 定义表格列名
        String[] columnNames = {"老师 ID", "姓名"};

        // 填充表格数据
        Object[][] data = new Object[TeacherList.size()][3];
        for (int i = 0; i < TeacherList.size(); i++) {
            data[i][0] = TeacherList.get(i).getTeacherID();
            data[i][1] = TeacherList.get(i).getTeacherName();
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
