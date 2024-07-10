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
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // 初始化界面组件
        idTextField = new JTextField(10);
        nameTextField = new JTextField(10);
        JButton deleteByIdButton = new JButton("按ID删除");
        JButton deleteByNameButton = new JButton("按名字删除");
        panel = new JPanel();

        // 添加组件到面板
        panel.add(new JLabel("ID:"));
        panel.add(idTextField);
        panel.add(deleteByIdButton);
        panel.add(new JLabel("名字:"));
        panel.add(nameTextField);
        panel.add(deleteByNameButton);

        // 添加按ID删除的动作监听器
        deleteByIdButton.addActionListener(e -> {
            String idText = idTextField.getText();
            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper StudentMapper = session.getMapper(StudentMapper.class);
                int StudentID = Integer.parseInt(idText);
                StudentMapper.deleteByStudentID(StudentID);
                session.commit();
                JOptionPane.showMessageDialog(panel, "删除成功：ID=" + StudentID);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "ID格式错误", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "删除失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 添加按名称删除的动作监听器
        deleteByNameButton.addActionListener(e -> {
            String nameText = nameTextField.getText();
            try (SqlSession session = sqlSessionFactory.openSession()) {
                StudentMapper StudentMapper = session.getMapper(StudentMapper.class);
                StudentMapper.deleteByStudentName(nameText);
                session.commit();
                JOptionPane.showMessageDialog(panel, "删除成功：名字=" + nameText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "删除失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        JFrame frame = new JFrame("删除学生");
        frame.setContentPane(panel);
        frame.setAlwaysOnTop(true);
        frame.setBounds(100, 100, 461, 371);
        frame.pack();
        frame.setVisible(true);
    }


}
