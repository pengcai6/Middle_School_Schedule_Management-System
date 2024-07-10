package com.cai.Frame.Student;

import com.cai.dao.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class StudentDeleteFrame {
    private final JTextField idTextField;
    private final JTextField nameTextField;
    private final JPanel panel;

    private final SqlSessionFactory sqlSessionFactory;

    public StudentDeleteFrame() throws IOException {
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // ��ʼ���������
        idTextField = new JTextField(10);
        nameTextField = new JTextField(10);
        JButton deleteByIdButton = new JButton("��IDɾ��");
        JButton deleteByNameButton = new JButton("������ɾ��");
        panel = new JPanel();

        // �����������
        panel.add(new JLabel("ID:"));
        panel.add(idTextField);
        panel.add(deleteByIdButton);
        panel.add(new JLabel("����:"));
        panel.add(nameTextField);
        panel.add(deleteByNameButton);

        // ��Ӱ�IDɾ���Ķ���������
        deleteByIdButton.addActionListener(e -> {
            String idText = idTextField.getText();
            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper StudentMapper = session.getMapper(StudentMapper.class);
                int StudentID = Integer.parseInt(idText);
                StudentMapper.deleteByStudentID(StudentID);
                session.commit();
                JOptionPane.showMessageDialog(panel, "ɾ���ɹ���ID=" + StudentID);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "ID��ʽ����", "����", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "ɾ��ʧ�ܣ�" + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ��Ӱ�����ɾ���Ķ���������
        deleteByNameButton.addActionListener(e -> {
            String nameText = nameTextField.getText();
            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper StudentMapper = session.getMapper(StudentMapper.class);
                StudentMapper.deleteByStudentName(nameText);
                session.commit();
                JOptionPane.showMessageDialog(panel, "ɾ���ɹ�������=" + nameText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "ɾ��ʧ�ܣ�" + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            }
        });

        JFrame frame = new JFrame("ɾ��ѧ��");
        frame.setContentPane(panel);
        frame.setAlwaysOnTop(true);
        frame.setBounds(100, 100, 461, 371);
        frame.pack();
        frame.setVisible(true);
    }


}
