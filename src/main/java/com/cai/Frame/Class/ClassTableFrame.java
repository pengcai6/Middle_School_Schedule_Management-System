package com.cai.Frame.Class;

import com.cai.dao.ClassMapper;
import com.cai.pojo.Class;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.cai.Test.getsqlSession.getSqlSession;

public class ClassTableFrame extends JFrame {
    public ClassTableFrame() throws IOException {
        setTitle("班级信息表");
        setBounds(100,100,461,371);
        setAlwaysOnTop(true);
        ClassMapper classMapper = getSqlSession(ClassMapper.class);
        List<Class> classList = classMapper.FindAllClass();

        String[] columnNames = {"班级 ID", "班级名"};
        Object[][] data = new Object[classList.size()][2];
        for (int i = 0; i < classList.size(); i++) {
            data[i][0] = classList.get(i).getClassID();
            data[i][1] = classList.get(i).getClassName();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
