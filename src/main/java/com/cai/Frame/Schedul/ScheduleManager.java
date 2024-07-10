package com.cai.Frame.Schedul;

import com.cai.dao.ScheduleMapper;
import com.cai.pojo.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.List;

public class ScheduleManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton, addButton, deleteButton;
    private SqlSessionFactory sqlSessionFactory;

    public ScheduleManager() {
        setTitle("�γ̱����");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        // Initialize MyBatis
        String resource = "mybatis-config.xml";
        InputStream inputStream = ScheduleManager.class.getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Initialize table model and table
        tableModel = new DefaultTableModel(new Object[]{"�γ̱�ID", "�༶ID", "�γ�ID", "��ʦID", "���ڼ�", "ʱ���ID"}, 0);
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // �γ̱�ID
        columnModel.getColumn(1).setPreferredWidth(150); // �༶ID
        columnModel.getColumn(2).setPreferredWidth(150); // �γ�ID
        columnModel.getColumn(3).setPreferredWidth(150); // ��ʦID
        columnModel.getColumn(4).setPreferredWidth(100); // ���ڼ�
        columnModel.getColumn(5).setPreferredWidth(150); // ʱ���ID

        // Initialize buttons
        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("����");
        addButton = new JButton("����");
        deleteButton = new JButton("ɾ��");
        buttonPanel.add(updateButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data from database
        loadScheduleData();

        // Add action listeners to the buttons
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScheduleData();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{"", "", "", "", "", ""});
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });
    }

    private void loadScheduleData() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper mapper = session.getMapper(ScheduleMapper.class);
            List<Schedule> schedules = mapper.getAllSchedules();
            for (Schedule schedule : schedules) {
                tableModel.addRow(new Object[]{
                        schedule.getScheduleID(),
                        schedule.getClassID(),
                        schedule.getCourseID(),
                        schedule.getTeacherID(),
                        schedule.getDayOfWeek(),
                        schedule.getTimeSlotID()
                });
            }
        }
    }

    private void updateScheduleData() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper mapper = session.getMapper(ScheduleMapper.class);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Schedule schedule = new Schedule();
                if (!tableModel.getValueAt(i, 0).toString().isEmpty()) {
                    schedule.setScheduleID(Integer.parseInt(tableModel.getValueAt(i, 0).toString()));
                }
                schedule.setClassID(Integer.parseInt(tableModel.getValueAt(i, 1).toString()));
                schedule.setCourseID(Integer.parseInt(tableModel.getValueAt(i, 2).toString()));
                schedule.setTeacherID(Integer.parseInt(tableModel.getValueAt(i, 3).toString()));
                schedule.setDayOfWeek(Integer.parseInt(tableModel.getValueAt(i, 4).toString()));
                schedule.setTimeSlotID(Integer.parseInt(tableModel.getValueAt(i, 5).toString()));

                if (tableModel.getValueAt(i, 0).toString().isEmpty()) {
                    mapper.insertSchedule(schedule);
                    tableModel.setValueAt(schedule.getScheduleID(), i, 0); // �����²����¼��ID
                } else {
                    mapper.updateSchedule(schedule);
                }
            }
            session.commit();
            JOptionPane.showMessageDialog(this, "���³ɹ�!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "����ʧ��: " + e.getMessage());
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ�����У�");
            return;
        }

        int scheduleID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ScheduleMapper mapper = session.getMapper(ScheduleMapper.class);
            mapper.deleteSchedule(scheduleID);
            session.commit();
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "ɾ���ɹ�!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "ɾ��ʧ��: " + e.getMessage());
        }
    }


}