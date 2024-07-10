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
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    public CourseUpdateFrame() {
        setTitle("修改课程信息");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("输入课程ID或姓名:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("查询");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // 表格模型
        String[] columnNames = {"课程ID", "课程名", "优先级"}; // 增加 "优先级" 列
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("更新");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchCourses());

        updateButton.addActionListener(e -> updateCourseData());

        setVisible(true);
    }

    private void searchCourses() {
        String searchText = searchField.getText().trim();
        List<Course> courses;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入课程ID或姓名", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            CourseMapper courseDAO = session.getMapper(CourseMapper.class);
            if (isNumeric(searchText)) {
                courses = courseDAO.getCourseById(Integer.parseInt(searchText));
            } else {
                courses = courseDAO.getCourseByName(searchText);
            }

            tableModel.setRowCount(0); // 清除表格内容
            for (Course course : courses) {
                tableModel.addRow(new Object[]{
                        course.getCourseID(),
                        course.getCourseName(),
                        course.getPriority(), // 显示优先级
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询过程中出现错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourseData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要更新的课程", "错误", JOptionPane.ERROR_MESSAGE);
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

            JOptionPane.showMessageDialog(this, "课程信息更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID和优先级必须是整数", "错误", JOptionPane.ERROR_MESSAGE);
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
