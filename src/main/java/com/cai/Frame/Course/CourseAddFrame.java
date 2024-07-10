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
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    public CourseAddFrame() {
        // ���ÿ������
        setTitle("��ӿγ�");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // ������������
        JLabel nameLabel = new JLabel("������γ�����:");
        JTextField nameField = new JTextField(20);
        JLabel priorityLabel = new JLabel("���������ȼ� (1-10):");
        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JButton submitButton = new JButton("�ύ");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // ʹ��GridLayout����

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priorityLabel);
        panel.add(prioritySpinner);
        panel.add(new JLabel()); // ռλ��
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // ��ť������
        submitButton.addActionListener(e -> {
            String courseName = nameField.getText();
            int priority = (int) prioritySpinner.getValue();
            if (courseName == null || courseName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "�γ����Ʋ���Ϊ��");
                return;
            }

            new Thread(() -> {
                try (SqlSession session = sqlSessionFactory.openSession()) {
                    CourseMapper mapper = session.getMapper(CourseMapper.class);
                    mapper.insertCourse(courseName, priority);
                    session.commit();
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "�γ���ӳɹ�"));
                } catch (Exception ex) {
                    logger.error("�����쳣", ex);
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(this, "��ӿγ�ʧ��: " + ex.getMessage()));
                }
            }).start();
        });

        // ���ÿ�ܿɼ�
        setVisible(true);
    }

}