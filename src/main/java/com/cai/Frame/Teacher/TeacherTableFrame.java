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
        setTitle("��ʦ��Ϣ��");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);

        // ��ȡ��ʦ����
        TeacherMapper TeacherMapper = getSqlSession(TeacherMapper.class);
        List<Teacher> TeacherList = TeacherMapper.FindAllTeacher();

        // ����������
        String[] columnNames = {"��ʦ ID", "����"};

        // ���������
        Object[][] data = new Object[TeacherList.size()][3];
        for (int i = 0; i < TeacherList.size(); i++) {
            data[i][0] = TeacherList.get(i).getTeacherID();
            data[i][1] = TeacherList.get(i).getTeacherName();
        }

        // �������
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // ���ò��ֲ�������
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // ��ʾ����
        setVisible(true);
    }
}
