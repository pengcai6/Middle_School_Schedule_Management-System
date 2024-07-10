package com.cai.Frame.Teacher;

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

public class TeacherScheduleViewer extends JFrame {
    private final JTextField teacherNameField;
    private final JSpinner teacherIdSpinner;
    private final DefaultTableModel tableModel;
    private final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TeacherScheduleViewer.class);

    public TeacherScheduleViewer() {
        setTitle("��ʦ�γ̱�鿴");
        setSize(1200, 800);  // �������ڴ�С
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // Initialize MyBatis
        String resource = "mybatis-config.xml";
        InputStream inputStream = TeacherScheduleViewer.class.getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Initialize input fields with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel teacherIdLabel = new JLabel("��ʦID��");
        teacherIdLabel.setFont(new Font("����", Font.PLAIN, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(teacherIdLabel, gbc);

        teacherIdSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        teacherIdSpinner.setFont(new Font("����", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(teacherIdSpinner, gbc);

        JLabel teacherNameLabel = new JLabel("��ʦ����");
        teacherNameLabel.setFont(new Font("����", Font.PLAIN, 22));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(teacherNameLabel, gbc);

        teacherNameField = new JTextField();
        teacherNameField.setFont(new Font("����", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(teacherNameField, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Initialize the search button
        JButton searchButton = new JButton("��ѯ�γ̱�");
        searchButton.setFont(new Font("����", Font.PLAIN, 22));
        add(searchButton, BorderLayout.SOUTH);

        // Initialize the schedule table
        tableModel = new DefaultTableModel(new Object[]{"ʱ���", "����һ", "���ڶ�", "������", "������", "������"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] timeSlots = {
                "08:00:00-09:30:00",
                "10:00:00-11:30:00",
                "13:00:00-14:30:00",
                "15:00:00-16:30:00",
                "18:00:00-19:30:00",
                "20:00:00-21:30:00"
        };
        for (String timeSlot : timeSlots) {
            tableModel.addRow(new Object[]{timeSlot, "", "", "", "", ""});
        }
        JTable scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(60);  // �����и�
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        add(scrollPane, BorderLayout.CENTER);

        // ���ñ������
        Font font = new Font("����", Font.PLAIN, 20);  // ʹ��֧�����ĵ�����
        scheduleTable.setFont(font);
        scheduleTable.getTableHeader().setFont(new Font("����", Font.BOLD, 22));

        // �����п�
        TableColumnModel columnModel = scheduleTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(200);
        }

        // Add action listener to the search button
        searchButton.addActionListener(e -> loadTeacherSchedule());
    }

    private void loadTeacherSchedule() {
        int teacherId = (int) teacherIdSpinner.getValue();
        String teacherName = teacherNameField.getText();

        if (teacherId <= 0 && (teacherName == null || teacherName.isEmpty())) {
            JOptionPane.showMessageDialog(this, "��������ʦID����ʦ��");
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper mapper = session.getMapper(ScheduleMapper.class);
            List<ScheduleEntry> scheduleEntries;

            if (teacherId > 0) {
                scheduleEntries = mapper.getTeacherSchedule(teacherId);
            } else {
                scheduleEntries = mapper.getAllSchedulesByTeacherName(teacherName);
                if (scheduleEntries.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "��ʦ�������ڣ���������Ч����ʦ��");
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
                String cellValue = "<html>" + entry.getCourseName() + "<br>(" + entry.getClassName() + ")</html>";
                tableModel.setValueAt(cellValue, timeSlotIndex, dayOfWeekIndex);
            }

            // ��� tableModel ���ݵ�����̨
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    System.out.print(tableModel.getValueAt(i, j) + "  ");
                }
                System.out.println();
            }

            tableModel.fireTableDataChanged(); // ȷ�����ݱ仯֪ͨ���ˢ��

        } catch (Exception e) {
            logger.error("�����쳣", e);
            JOptionPane.showMessageDialog(this, "��ѯʧ��: " + e.getMessage());
        }
    }
}
