package com.cai.Frame.Teacher;

import com.cai.dao.TeacherMapper;
import com.cai.pojo.Teacher;
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

public class TeacherUpdateFrame extends JFrame {
    private final JTextField searchField;
    private final JTable resultTable;
    private final DefaultTableModel tableModel;
    private static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml"; // ���������ļ�
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory ��ʼ��ʧ��: " + ex.getMessage(), ex);
        }
    }

    public TeacherUpdateFrame() {
        setTitle("�޸���ʦ��Ϣ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("������ʦID������:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("��ѯ");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ���ģ��
        String[] columnNames = {"��ʦID", "��ʦ��"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("����");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchTeachers());

        updateButton.addActionListener(e -> updateTeacherData());

        setVisible(true);
    }

    private void searchTeachers() {
        String searchText = searchField.getText().trim();
        List<Teacher> Teachers;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "��������ʦID������", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TeacherMapper TeacherDAO = session.getMapper(TeacherMapper.class);
            if (isNumeric(searchText)) {
                Teachers = TeacherDAO.getTeacherById(Integer.parseInt(searchText));
            } else {
                Teachers = TeacherDAO.getTeacherByName(searchText);
            }

            tableModel.setRowCount(0); // ����������
            for (Teacher Teacher : Teachers) {
                tableModel.addRow(new Object[]{
                        Teacher.getTeacherID(),
                        Teacher.getTeacherName(),
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "��ѯ�����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTeacherData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���µ���ʦ", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Object idObject = tableModel.getValueAt(selectedRow, 0);
            Object nameObject = tableModel.getValueAt(selectedRow, 1);

            int id;
            if (idObject instanceof Integer) {
                id = (Integer) idObject;
            } else {
                id = Integer.parseInt(idObject.toString());
            }

            String name = nameObject.toString();

            Teacher updatedTeacher = new Teacher(id, name);

            TeacherMapper TeacherDAO = session.getMapper(TeacherMapper.class);
            TeacherDAO.updateTeacher(updatedTeacher);
            session.commit();

            JOptionPane.showMessageDialog(this, "��ʦ��Ϣ���³ɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID����������", "����", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "���¹����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
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