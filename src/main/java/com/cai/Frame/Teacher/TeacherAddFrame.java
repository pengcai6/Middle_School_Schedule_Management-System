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
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    public TeacherAddFrame() {
        // ���ÿ������
        setTitle("�����ʦ");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // ������������
        JLabel nameLabel = new JLabel("��������ʦ����:");
        JTextField nameField = new JTextField(20);
        JButton submitButton = new JButton("�ύ");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // ʹ��GridLayout����

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); // ռλ��
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // ��ť������
        submitButton.addActionListener(e -> {
            String TeacherName = nameField.getText();
            if (TeacherName == null || TeacherName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "��ʦ���Ʋ���Ϊ��");
                return;
            }

            try (SqlSession session = sqlSessionFactory.openSession()) {
                TeacherMapper mapper = session.getMapper(TeacherMapper.class);
                mapper.insertTeacher(TeacherName);
                session.commit();
                JOptionPane.showMessageDialog(this, "��ʦ��ӳɹ�");
            } catch (Exception ex) {
                logger.error("�����쳣", ex);
                JOptionPane.showMessageDialog(this, "�����ʦʧ��: " + ex.getMessage());
            }
        });

        // ���ÿ�ܿɼ�
        setVisible(true);
    }


}
