package com.cai.Frame.Teacher;

import com.cai.dao.TeacherMapper;
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

public class TeacherAddFrame extends JFrame {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TeacherMapper.class);

    static {
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    public TeacherAddFrame() {
        // 设置框架属性
        setTitle("添加老师");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // 创建并添加组件
        JLabel nameLabel = new JLabel("请输入老师名称:");
        JTextField nameField = new JTextField(20);
        JButton submitButton = new JButton("提交");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // 使用GridLayout布局

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); // 占位符
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // 按钮监听器
        submitButton.addActionListener(e -> {
            String TeacherName = nameField.getText();
            if (TeacherName == null || TeacherName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "老师名称不能为空");
                return;
            }

            try (SqlSession session = sqlSessionFactory.openSession()) {
                TeacherMapper mapper = session.getMapper(TeacherMapper.class);
                mapper.insertTeacher(TeacherName);
                session.commit();
                JOptionPane.showMessageDialog(this, "老师添加成功");
            } catch (Exception ex) {
                logger.error("发生异常", ex);
                JOptionPane.showMessageDialog(this, "添加老师失败: " + ex.getMessage());
            }
        });

        // 设置框架可见
        setVisible(true);
    }


}
