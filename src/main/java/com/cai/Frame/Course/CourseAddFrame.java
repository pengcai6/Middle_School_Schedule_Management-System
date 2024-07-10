package com.cai.Frame.Course;

import com.cai.dao.CourseMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class CourseAddFrame extends JFrame {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(CourseAddFrame.class);

    static {
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    public CourseAddFrame() {
        // 设置框架属性
        setTitle("添加课程");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // 创建并添加组件
        JLabel nameLabel = new JLabel("请输入课程名称:");
        JTextField nameField = new JTextField(20);
        JLabel priorityLabel = new JLabel("请输入优先级 (1-10):");
        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JButton submitButton = new JButton("提交");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // 使用GridLayout布局

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priorityLabel);
        panel.add(prioritySpinner);
        panel.add(new JLabel()); // 占位符
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // 按钮监听器
        submitButton.addActionListener(e -> {
            String courseName = nameField.getText();
            int priority = (int) prioritySpinner.getValue();
            if (courseName == null || courseName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "课程名称不能为空");
                return;
            }

            new Thread(() -> {
                try (SqlSession session = sqlSessionFactory.openSession()) {
                    CourseMapper mapper = session.getMapper(CourseMapper.class);
                    mapper.insertCourse(courseName, priority);
                    session.commit();
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "课程添加成功"));
                } catch (Exception ex) {
                    logger.error("发生异常", ex);
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "添加课程失败: " + ex.getMessage()));
                }
            }).start();
        });

        // 设置框架可见
        setVisible(true);
    }

}