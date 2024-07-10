package com.cai.Frame.Class;

import com.cai.dao.ClassMapper;
import com.cai.pojo.Class;
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

public class ClassUpdateFrame extends JFrame {
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

    public ClassUpdateFrame() {
        setTitle("�޸İ༶��Ϣ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("����༶ID��༶����:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("��ѯ");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // ���ģ��
        String[] columnNames = {"�༶ID", "�༶����"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("����");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchClasses());

        updateButton.addActionListener(e -> updateClassData());

        setVisible(true);
    }

    private void searchClasses() {
        String searchText = searchField.getText().trim();
        List<Class> classes;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������༶ID��༶����", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ClassMapper classDAO = session.getMapper(ClassMapper.class);
            if (isNumeric(searchText)) {
                classes = classDAO.getClassById(Integer.parseInt(searchText));
            } else {
                classes = classDAO.getClassByName(searchText);
            }

            tableModel.setRowCount(0); // ����������
            for (Class cls : classes) {
                tableModel.addRow(new Object[]{cls.getClassID(), cls.getClassName()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "��ѯ�����г��ִ���: " + ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClassData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���µİ༶", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);

            Class updatedClass = new Class(id, name);

            ClassMapper classDAO = session.getMapper(ClassMapper.class);
            classDAO.updateClass(updatedClass);
            session.commit();

            JOptionPane.showMessageDialog(this, "�༶��Ϣ���³ɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
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

