package com.cai.Frame.Student;

import com.cai.dao.StudentMapper;
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

public class StudentAddFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(StudentAddFrame.class);

    public StudentAddFrame() {
        // 设置框架属性
        setTitle("添加学生");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // 创建并添加组件
        JLabel nameLabel = new JLabel("请输入学生姓名:");
        JTextField nameField = new JTextField(20);
        JLabel classIDLabel = new JLabel("请输入班级 ID:");
        JTextField classIDField = new JTextField(20);
        JButton submitButton = new JButton("提交");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // 使用GridLayout布局

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(classIDLabel);
        panel.add(classIDField);
        panel.add(new JLabel()); // 占位符
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // 按钮监听器
        submitButton.addActionListener(e -> {
            String studentName = nameField.getText();
            String classIDText = classIDField.getText();

            if (studentName == null || studentName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "学生姓名不能为空");
                return;
            }

            if (classIDText == null || classIDText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "班级 ID 不能为空");
                return;
            }

            int classID;
            try {
                classID = Integer.parseInt(classIDText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "班级 ID 必须是一个整数");
                return;
            }

            // 配置文件路径
            String resource = "mybatis-config.xml"; // 核心配置文件
            // 基于配置文件路径，创建字节输入流对象
            InputStream is;
            try {
                is = Resources.getResourceAsStream(resource);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // 创建SqlSessionFactory工厂类对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper mapper = session.getMapper(StudentMapper.class);
                mapper.insertStudent(studentName, classID); // 传递两个参数
                session.commit();
                JOptionPane.showMessageDialog(this, "学生添加成功");
            } catch (Exception ex) {
                logger.error("发生异常", ex);
                JOptionPane.showMessageDialog(this, "添加学生失败: " + ex.getMessage());
            }
        });

        // 设置框架可见
        setVisible(true);
    }
}
