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
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    public StudentUpdateFrame() {
        setTitle("修改学生信息");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("输入学生ID或姓名:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("查询");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // 表格模型
        String[] columnNames = {"学生ID", "学生姓名", "学生班级"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("更新");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchStudents());

        updateButton.addActionListener(e -> {
            try {
                Object idObj = tableModel.getValueAt(resultTable.getSelectedRow(), 0);
                Object classIdObj = tableModel.getValueAt(resultTable.getSelectedRow(), 2);
                if (!(idObj instanceof String) || !(classIdObj instanceof String)) {
                    throw new NumberFormatException("ID和班级ID必须是整数");
                }
                Integer.parseInt((String) idObj);
                Integer.parseInt((String) classIdObj);
                updateStudentData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(StudentUpdateFrame.this, "ID和班级ID必须是整数", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });


        updateButton.addActionListener(e -> updateStudentData());

        setVisible(true);
    }

    private void searchStudents() {
        String searchText = searchField.getText().trim();
        List<Student> students;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入学生ID或姓名", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            StudentMapper studentDAO = session.getMapper(StudentMapper.class);
            if (isNumeric(searchText)) {
                students = studentDAO.getStudentById(Integer.parseInt(searchText));
            } else {
                students = studentDAO.getStudentByName(searchText);
            }

            tableModel.setRowCount(0); // 清除表格内容
            for (Student student : students) {
                tableModel.addRow(new Object[]{
                        student.getStudentID(),
                        student.getStudentName(),
                        student.getClassID()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询过程中出现错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateStudentData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要更新的学生", "错误", JOptionPane.ERROR_MESSAGE);
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

            JOptionPane.showMessageDialog(this, "学生信息更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "更新过程中出现错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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

