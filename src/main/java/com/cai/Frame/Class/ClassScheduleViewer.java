package com.cai.Frame.Class;

import com.cai.dao.ScheduleMapper;
import com.cai.pojo.ScheduleEntry;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class ClassScheduleViewer extends JFrame {
    private final JTextField classNameField;
    private final JSpinner classIdSpinner;
    private final DefaultTableModel tableModel;
    private final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(ClassScheduleViewer.class);

    public ClassScheduleViewer() {
        setTitle("班级课程表查看");
        setSize(1200, 800);  // 调整窗口大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // Initialize MyBatis
        String resource = "mybatis-config.xml";
        InputStream inputStream = ClassScheduleViewer.class.getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Initialize input fields with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel classIdLabel = new JLabel("班级ID：");
        classIdLabel.setFont(new Font("宋体", Font.PLAIN, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(classIdLabel, gbc);

        classIdSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        classIdSpinner.setFont(new Font("宋体", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(classIdSpinner, gbc);

        JLabel classNameLabel = new JLabel("班级名：");
        classNameLabel.setFont(new Font("宋体", Font.PLAIN, 22));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(classNameLabel, gbc);

        classNameField = new JTextField();
        classNameField.setFont(new Font("宋体", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(classNameField, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Initialize the search button
        JButton searchButton = new JButton("查询课程表");
        searchButton.setFont(new Font("宋体", Font.PLAIN, 22));
        add(searchButton, BorderLayout.SOUTH);

        // Initialize the schedule table
        tableModel = new DefaultTableModel(new Object[]{"时间段", "星期一", "星期二", "星期三", "星期四", "星期五"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] timeSlots = {"08:00:00-09:30:00", "10:00:00-11:30:00", "13:00:00-14:30:00", "15:00:00-16:30:00", "18:00:00-19:30:00", "20:00:00-21:30:00"};
        for (String timeSlot : timeSlots) {
            tableModel.addRow(new Object[]{timeSlot, "", "", "", "", ""});
        }
        JTable scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(60);  // 调整行高
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        add(scrollPane, BorderLayout.CENTER);

        // 设置表格字体
        Font font = new Font("宋体", Font.PLAIN, 20);  // 使用支持中文的字体
        scheduleTable.setFont(font);
        scheduleTable.getTableHeader().setFont(new Font("宋体", Font.BOLD, 22));

        // 设置列宽
        TableColumnModel columnModel = scheduleTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(200);
        }

        // Add action listener to the search button
        searchButton.addActionListener(e -> loadClassSchedule());
    }

    private void loadClassSchedule() {
        int classId = (int) classIdSpinner.getValue();
        String className = classNameField.getText();

        if (classId <= 0 && (className == null || className.isEmpty())) {
            JOptionPane.showMessageDialog(this, "请输入班级ID或班级名");
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper mapper = session.getMapper(ScheduleMapper.class);
            List<ScheduleEntry> scheduleEntries;

            if (classId > 0) {
                scheduleEntries = mapper.getClassSchedule(classId);
            } else {
                scheduleEntries = mapper.getAllSchedulesByClassName(className);
                if (scheduleEntries.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "班级名不存在，请输入有效的班级名");
                    return;
                }
            }

            // Clear previous data but keep the time slots
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 1; j < tableModel.getColumnCount(); j++) {
                    tableModel.setValueAt("", i, j);
                }
            }

            for (ScheduleEntry entry : scheduleEntries) {
                int timeSlotIndex = entry.getTimeSlotID() - 1;
                int dayOfWeekIndex = entry.getDayOfWeek();
                String cellValue = "<html>" + entry.getCourseName() + "<br>(" + entry.getTeacherName() + ")</html>";
                tableModel.setValueAt(cellValue, timeSlotIndex, dayOfWeekIndex);
            }

            // 输出 tableModel 内容到控制台
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    System.out.print(tableModel.getValueAt(i, j) + "  ");
                }
                System.out.println();
            }

            tableModel.fireTableDataChanged(); // 确保数据变化通知表格刷新

        } catch (Exception e) {
            logger.error("发生异常", e);
            JOptionPane.showMessageDialog(this, "查询失败: " + e.getMessage());
        }
    }
}
