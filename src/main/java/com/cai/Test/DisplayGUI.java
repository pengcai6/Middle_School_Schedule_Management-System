package com.cai.Test;

import com.cai.Frame.Class.*;
import com.cai.Frame.Course.CourseAddFrame;
import com.cai.Frame.Course.CourseDeleteFrame;
import com.cai.Frame.Course.CourseTableFrame;
import com.cai.Frame.Course.CourseUpdateFrame;
import com.cai.Frame.Schedul.ScheduleManager;
import com.cai.Frame.Student.StudentAddFrame;
import com.cai.Frame.Student.StudentDeleteFrame;
import com.cai.Frame.Student.StudentTableFrame;
import com.cai.Frame.Student.StudentUpdateFrame;
import com.cai.Frame.Teacher.*;
import com.cai.service.AutoSchedule;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayGUI {

    public DisplayGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("学生排课管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 800);
        frame.setAlwaysOnTop(true);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(255, 255, 255));  // 使用更亮的背景颜色

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "功能选择", 0, 0, new Font("宋体", Font.BOLD, 18), Color.BLACK));
        buttonPanel.setBackground(new Color(245, 245, 245));  // 使用更亮的背景颜色
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 创建和设置按钮
        JButton button1 = createStyledButton("所有班级");
        JButton button2 = createStyledButton("添加班级");
        JButton button3 = createStyledButton("删除班级");
        JButton button4 = createStyledButton("修改班级");

        JButton button5 = createStyledButton("所有学生");
        JButton button6 = createStyledButton("添加学生");
        JButton button7 = createStyledButton("删除学生");
        JButton button8 = createStyledButton("修改学生");

        JButton button9 = createStyledButton("所有课程");
        JButton button10 = createStyledButton("添加课程");
        JButton button11 = createStyledButton("删除课程");
        JButton button12 = createStyledButton("修改课程");

        JButton button13 = createStyledButton("所有老师");
        JButton button14 = createStyledButton("添加老师");
        JButton button15 = createStyledButton("删除老师");
        JButton button16 = createStyledButton("修改老师");

        JButton button17 = createStyledButton("查询课表");
        JButton button18 = createStyledButton("生成班级课表");
        JButton button19 = createStyledButton("生成老师课表");
        JButton button20 = createStyledButton("课程表一键排课");

        // 布局按钮
        addComponent(buttonPanel, new JLabel("班级管理", SwingConstants.CENTER), 0, 0, 2, gbc);
        addComponent(buttonPanel, button1, 0, 1, 1, gbc);
        addComponent(buttonPanel, button2, 1, 1, 1, gbc);
        addComponent(buttonPanel, button3, 0, 2, 1, gbc);
        addComponent(buttonPanel, button4, 1, 2, 1, gbc);

        addComponent(buttonPanel, new JLabel("学生管理", SwingConstants.CENTER), 0, 3, 2, gbc);
        addComponent(buttonPanel, button5, 0, 4, 1, gbc);
        addComponent(buttonPanel, button6, 1, 4, 1, gbc);
        addComponent(buttonPanel, button7, 0, 5, 1, gbc);
        addComponent(buttonPanel, button8, 1, 5, 1, gbc);

        addComponent(buttonPanel, new JLabel("课程管理", SwingConstants.CENTER), 0, 6, 2, gbc);
        addComponent(buttonPanel, button9, 0, 7, 1, gbc);
        addComponent(buttonPanel, button10, 1, 7, 1, gbc);
        addComponent(buttonPanel, button11, 0, 8, 1, gbc);
        addComponent(buttonPanel, button12, 1, 8, 1, gbc);

        addComponent(buttonPanel, new JLabel("老师管理", SwingConstants.CENTER), 0, 9, 2, gbc);
        addComponent(buttonPanel, button13, 0, 10, 1, gbc);
        addComponent(buttonPanel, button14, 1, 10, 1, gbc);
        addComponent(buttonPanel, button15, 0, 11, 1, gbc);
        addComponent(buttonPanel, button16, 1, 11, 1, gbc);

        addComponent(buttonPanel, new JLabel("课表管理", SwingConstants.CENTER), 0, 12, 2, gbc);
        addComponent(buttonPanel, button17, 0, 13, 1, gbc);
        addComponent(buttonPanel, button18, 1, 13, 1, gbc);
        addComponent(buttonPanel, button19, 0, 14, 1, gbc);
        addComponent(buttonPanel, button20, 1, 14, 1, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        // 设置按钮监听
        button1.addActionListener(e -> {
            try {
                new ClassTableFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button2.addActionListener(e -> new ClassAddFrame());

        button3.addActionListener(e -> {
            try {
                new ClassDeleteFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button4.addActionListener(e -> new ClassUpdateFrame());

        button5.addActionListener(e -> {
            try {
                new StudentTableFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button6.addActionListener(e -> new StudentAddFrame());

        button7.addActionListener(e -> {
            try {
                new StudentDeleteFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button8.addActionListener(e -> new StudentUpdateFrame());

        button9.addActionListener(e -> {
            try {
                new CourseTableFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button10.addActionListener(e -> new CourseAddFrame());

        button11.addActionListener(e -> {
            try {
                new CourseDeleteFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button12.addActionListener(e -> {
                new CourseUpdateFrame();
        });

        button13.addActionListener(e -> {
            try {
                new TeacherTableFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button14.addActionListener(e -> {
                new TeacherAddFrame();
        });

        button15.addActionListener(e -> {
            try {
                new TeacherDeleteFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button16.addActionListener(e -> {
                new TeacherUpdateFrame();
        });

        button17.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
            new ScheduleManager().setVisible(true);
        });
        });

        button18.addActionListener(e -> {
                new ClassScheduleViewer().setVisible(true);
        });

        button19.addActionListener(e -> {
                new TeacherScheduleViewer().setVisible(true);
        });

        button20.addActionListener(e -> {
                new AutoSchedule();
        });

        // 设置框架可见
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("宋体", Font.PLAIN, 14));  // 使用宋体字体
        button.setBackground(new Color(100, 149, 237));  // 使用更亮的按钮背景颜色
        button.setForeground(Color.BLACK);  // 使用黑色字体
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    private void addComponent(JPanel panel, Component component, int x, int y, int width, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = 1;
        panel.add(component, gbc);
    }

}
