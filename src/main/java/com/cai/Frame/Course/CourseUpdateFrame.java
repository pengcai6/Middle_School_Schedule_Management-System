package com.cai.Frame.Course;

import com.cai.dao.CourseMapper;
import com.cai.pojo.Course;
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

public class CourseUpdateFrame extends JFrame {
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

    public CourseUpdateFrame() {
        setTitle("�޸Ŀγ���Ϣ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("����γ�ID������:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("��ѯ");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ���ģ��
        String[] columnNames = {"�γ�ID", "�γ���", "���ȼ�"}; // ���� "���ȼ�" ��
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("����");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchCourses());

        updateButton.addActionListener(e -> updateCourseData());

        setVisible(true);
    }

    private void searchCourses() {
        String searchText = searchField.getText().trim();
        List<Course> courses;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������γ�ID������", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            CourseMapper courseDAO = session.getMapper(CourseMapper.class);
            if (isNumeric(searchText)) {
                courses = courseDAO.getCourseById(Integer.parseInt(searchText));
            } else {
                courses = courseDAO.getCourseByName(searchText);
            }

            tableModel.setRowCount(0); // ����������
            for (Course course : courses) {
                tableModel.addRow(new Object[]{
                        course.getCourseID(),
                        course.getCourseName(),
                        course.getPriority(), // ��ʾ���ȼ�
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "��ѯ�����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourseData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���µĿγ�", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Object idObject = tableModel.getValueAt(selectedRow, 0);
            Object nameObject = tableModel.getValueAt(selectedRow, 1);
            Object priorityObject = tableModel.getValueAt(selectedRow, 2);

            int id = Integer.parseInt(idObject.toString());
            String name = nameObject.toString();
            int priority = Integer.parseInt(priorityObject.toString());

            Course updatedCourse = new Course(id, name, priority);

            CourseMapper courseDAO = session.getMapper(CourseMapper.class);
            courseDAO.updateCourse(updatedCourse);
            session.commit();

            JOptionPane.showMessageDialog(this, "�γ���Ϣ���³ɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID�����ȼ�����������", "����", JOptionPane.ERROR_MESSAGE);
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
