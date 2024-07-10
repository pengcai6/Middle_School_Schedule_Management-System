package com.cai.Frame.Student;

import com.cai.dao.StudentMapper;
import com.cai.pojo.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StudentUpdateFrame extends JFrame {
    private final JTextField searchField;
    private final JTable resultTable;
    private final DefaultTableModel tableModel;
    private static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    public StudentUpdateFrame() {
        setTitle("�޸�ѧ����Ϣ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("����ѧ��ID������:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("��ѯ");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ���ģ��
        String[] columnNames = {"ѧ��ID", "ѧ������", "ѧ���༶"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("����");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchStudents());

        updateButton.addActionListener(e -> {
            try {
                Object idObj = tableModel.getValueAt(resultTable.getSelectedRow(), 0);
                Object classIdObj = tableModel.getValueAt(resultTable.getSelectedRow(), 2);
                if (!(idObj instanceof String) || !(classIdObj instanceof String)) {
                    throw new NumberFormatException("ID�Ͱ༶ID����������");
                }
                Integer.parseInt((String) idObj);
                Integer.parseInt((String) classIdObj);
                updateStudentData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(StudentUpdateFrame.this, "ID�Ͱ༶ID����������", "����", JOptionPane.ERROR_MESSAGE);
            }
        });


        updateButton.addActionListener(e -> updateStudentData());

        setVisible(true);
    }

    private void searchStudents() {
        String searchText = searchField.getText().trim();
        List<Student> students;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������ѧ��ID������", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            StudentMapper studentDAO = session.getMapper(StudentMapper.class);
            if (isNumeric(searchText)) {
                students = studentDAO.getStudentById(Integer.parseInt(searchText));
            } else {
                students = studentDAO.getStudentByName(searchText);
            }

            tableModel.setRowCount(0); // ����������
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                        student.getStudentID(),
                        student.getStudentName(),
                        student.getClassID()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "��ѯ�����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateStudentData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���µ�ѧ��", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Object idObject = tableModel.getValueAt(selectedRow, 0);
            Object nameObject = tableModel.getValueAt(selectedRow, 1);
            Object classIdObject = tableModel.getValueAt(selectedRow, 2);

            int id;
            if (idObject instanceof Integer) {
                id = (Integer) idObject;
            } else {
                id = Integer.parseInt((String) idObject);
            }

            String name = nameObject.toString();

            int classId;
            if (classIdObject instanceof Integer) {
                classId = (Integer) classIdObject;
            } else {
                classId = Integer.parseInt((String) classIdObject);
            }

            Student updatedStudent = new Student(id,name, classId);

            StudentMapper studentDAO = session.getMapper(StudentMapper.class);
            studentDAO.updateStudent(updatedStudent);
            session.commit();

            JOptionPane.showMessageDialog(this, "ѧ����Ϣ���³ɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "���¹����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
        }
    }


    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

