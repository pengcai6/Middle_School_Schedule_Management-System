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
        setTitle("ѧ����Ϣ��");
        setBounds(100, 100, 461, 371);
        setAlwaysOnTop(true);

        // ��ȡѧ������
        StudentMapper studentMapper = getSqlSession(StudentMapper.class);
        List<Student> studentList = studentMapper.FindAllStudent();

        // ����������
        String[] columnNames = {"ѧ�� ID", "����", "�༶ ID"};

        // ���������
        Object[][] data = new Object[studentList.size()][3];
        for (int i = 0; i < studentList.size(); i++) {
            data[i][0] = studentList.get(i).getStudentID();
            data[i][1] = studentList.get(i).getStudentName();
            data[i][2] = studentList.get(i).getClassID();  // ��Ӱ༶ ID ��
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
