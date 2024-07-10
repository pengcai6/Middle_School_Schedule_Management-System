package com.cai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntry {
    private int timeSlotID;
    private String startTime;
    private String endTime;
    private String courseName;
    private String teacherName;
    private String ClassName;
    private int dayOfWeek;
}
