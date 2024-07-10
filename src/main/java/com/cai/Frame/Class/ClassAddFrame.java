package com.cai.Frame.Class;

import com.cai.dao.ClassMapper;
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

public class ClassAddFrame extends JFrame {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(ClassAddFrame.class);

    static {
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    public ClassAddFrame() {
        // ���ÿ������
        setTitle("��Ӱ༶");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);

        // ������������
        JLabel nameLabel = new JLabel("������༶����:");
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
            String ClassName = nameField.getText();
            if (ClassName == null || ClassName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "�༶���Ʋ���Ϊ��");
                return;
            }

            try (SqlSession session = sqlSessionFactory.openSession()) {
                ClassMapper mapper = session.getMapper(ClassMapper.class);
                mapper.insertClass(ClassName);
                session.commit();
                JOptionPane.showMessageDialog(this, "�༶��ӳɹ�");
            } catch (Exception ex) {
                logger.error("�����쳣", ex);
                JOptionPane.showMessageDialog(this, "��Ӱ༶ʧ��: " + ex.getMessage());
            }
        });

        // ���ÿ�ܿɼ�
        setVisible(true);
    }

}
