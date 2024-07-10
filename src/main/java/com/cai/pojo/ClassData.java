package com.cai.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ClassData implements TableModel {
    private String[] title = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    @Getter
    @Setter
    private String[][] data = new String[8][7];

    public ClassData() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = "";
            }
        }
    }

    @Override
    public int getRowCount() {
        return 8;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int column) {
        return title[column];
    }

    @Override
    public java.lang.Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = (String) aValue;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {}

    @Override
    public void removeTableModelListener(TableModelListener l) {}

}
