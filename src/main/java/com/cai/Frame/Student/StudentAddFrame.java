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
        // ���ÿ������
        setTitle("���ѧ��");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // ������������
        JLabel nameLabel = new JLabel("������ѧ������:");
        JTextField nameField = new JTextField(20);
        JLabel classIDLabel = new JLabel("������༶ ID:");
        JTextField classIDField = new JTextField(20);
        JButton submitButton = new JButton("�ύ");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // ʹ��GridLayout����

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(classIDLabel);
        panel.add(classIDField);
        panel.add(new JLabel()); // ռλ��
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);

        // ��ť������
        submitButton.addActionListener(e -> {
            String studentName = nameField.getText();
            String classIDText = classIDField.getText();

            if (studentName == null || studentName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ѧ����������Ϊ��");
                return;
            }

            if (classIDText == null || classIDText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "�༶ ID ����Ϊ��");
                return;
            }

            int classID;
            try {
                classID = Integer.parseInt(classIDText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "�༶ ID ������һ������");
                return;
            }

            // �����ļ�·��
            String resource = "mybatis-config.xml"; // ���������ļ�
            // ���������ļ�·���������ֽ�����������
            InputStream is;
            try {
                is = Resources.getResourceAsStream(resource);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // ����SqlSessionFactory���������
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper mapper = session.getMapper(StudentMapper.class);
                mapper.insertStudent(studentName, classID); // ������������
                session.commit();
                JOptionPane.showMessageDialog(this, "ѧ����ӳɹ�");
            } catch (Exception ex) {
                logger.error("�����쳣", ex);
                JOptionPane.showMessageDialog(this, "���ѧ��ʧ��: " + ex.getMessage());
            }
        });

        // ���ÿ�ܿɼ�
        setVisible(true);
    }
}
