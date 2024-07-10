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
        JFrame frame = new JFrame("ѧ���ſι���ϵͳ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1000, 800);
        frame.setAlwaysOnTop(true);

        // ���������
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(255, 255, 255));  // ʹ�ø����ı�����ɫ

        // ������ť���
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "����ѡ��", 0, 0, new Font("����", Font.BOLD, 18), Color.BLACK));
        buttonPanel.setBackground(new Color(245, 245, 245));  // ʹ�ø����ı�����ɫ
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ���������ð�ť
        JButton button1 = createStyledButton("���а༶");
        JButton button2 = createStyledButton("��Ӱ༶");
        JButton button3 = createStyledButton("ɾ���༶");
        JButton button4 = createStyledButton("�޸İ༶");

        JButton button5 = createStyledButton("����ѧ��");
        JButton button6 = createStyledButton("���ѧ��");
        JButton button7 = createStyledButton("ɾ��ѧ��");
        JButton button8 = createStyledButton("�޸�ѧ��");

        JButton button9 = createStyledButton("���пγ�");
        JButton button10 = createStyledButton("��ӿγ�");
        JButton button11 = createStyledButton("ɾ���γ�");
        JButton button12 = createStyledButton("�޸Ŀγ�");

        JButton button13 = createStyledButton("������ʦ");
        JButton button14 = createStyledButton("�����ʦ");
        JButton button15 = createStyledButton("ɾ����ʦ");
        JButton button16 = createStyledButton("�޸���ʦ");

        JButton button17 = createStyledButton("��ѯ�α�");
        JButton button18 = createStyledButton("���ɰ༶�α�");
        JButton button19 = createStyledButton("������ʦ�α�");
        JButton button20 = createStyledButton("�γ̱�һ���ſ�");

        // ���ְ�ť
        addComponent(buttonPanel, new JLabel("�༶����", SwingConstants.CENTER), 0, 0, 2, gbc);
        addComponent(buttonPanel, button1, 0, 1, 1, gbc);
        addComponent(buttonPanel, button2, 1, 1, 1, gbc);
        addComponent(buttonPanel, button3, 0, 2, 1, gbc);
        addComponent(buttonPanel, button4, 1, 2, 1, gbc);

        addComponent(buttonPanel, new JLabel("ѧ������", SwingConstants.CENTER), 0, 3, 2, gbc);
        addComponent(buttonPanel, button5, 0, 4, 1, gbc);
        addComponent(buttonPanel, button6, 1, 4, 1, gbc);
        addComponent(buttonPanel, button7, 0, 5, 1, gbc);
        addComponent(buttonPanel, button8, 1, 5, 1, gbc);

        addComponent(buttonPanel, new JLabel("�γ̹���", SwingConstants.CENTER), 0, 6, 2, gbc);
        addComponent(buttonPanel, button9, 0, 7, 1, gbc);
        addComponent(buttonPanel, button10, 1, 7, 1, gbc);
        addComponent(buttonPanel, button11, 0, 8, 1, gbc);
        addComponent(buttonPanel, button12, 1, 8, 1, gbc);

        addComponent(buttonPanel, new JLabel("��ʦ����", SwingConstants.CENTER), 0, 9, 2, gbc);
        addComponent(buttonPanel, button13, 0, 10, 1, gbc);
        addComponent(buttonPanel, button14, 1, 10, 1, gbc);
        addComponent(buttonPanel, button15, 0, 11, 1, gbc);
        addComponent(buttonPanel, button16, 1, 11, 1, gbc);

        addComponent(buttonPanel, new JLabel("�α����", SwingConstants.CENTER), 0, 12, 2, gbc);
        addComponent(buttonPanel, button17, 0, 13, 1, gbc);
        addComponent(buttonPanel, button18, 1, 13, 1, gbc);
        addComponent(buttonPanel, button19, 0, 14, 1, gbc);
        addComponent(buttonPanel, button20, 1, 14, 1, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        // ���ð�ť����
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

        // ���ÿ�ܿɼ�
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("����", Font.PLAIN, 14));  // ʹ����������
        button.setBackground(new Color(100, 149, 237));  // ʹ�ø����İ�ť������ɫ
        button.setForeground(Color.BLACK);  // ʹ�ú�ɫ����
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
