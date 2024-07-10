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
        String resource = "mybatis-config.xml"; // 核心配置文件
        try (InputStream is = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException ex) {
            throw new RuntimeException("SqlSessionFactory 初始化失败: " + ex.getMessage(), ex);
        }
    }

    public ClassUpdateFrame() {
        setTitle("修改班级信息");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchPanel.add(new JLabel("输入班级ID或班级名称:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("查询");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // 表格模型
        String[] columnNames = {"班级ID", "班级名称"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateButton = new JButton("更新");
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchClasses());

        updateButton.addActionListener(e -> updateClassData());

        setVisible(true);
    }

    private void searchClasses() {
        String searchText = searchField.getText().trim();
        List<Class> classes;

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入班级ID或班级名称", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ClassMapper classDAO = session.getMapper(ClassMapper.class);
            if (isNumeric(searchText)) {
                classes = classDAO.getClassById(Integer.parseInt(searchText));
            } else {
                classes = classDAO.getClassByName(searchText);
            }

            tableModel.setRowCount(0); // 清除表格内容
            for (Class cls : classes) {
                tableModel.addRow(new Object[]{cls.getClassID(), cls.getClassName()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询过程中出现错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClassData() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要更新的班级", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);

            Class updatedClass = new Class(id, name);

            ClassMapper classDAO = session.getMapper(ClassMapper.class);
            classDAO.updateClass(updatedClass);
            session.commit();

            JOptionPane.showMessageDialog(this, "班级信息更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "更新过程中出现错误: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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

