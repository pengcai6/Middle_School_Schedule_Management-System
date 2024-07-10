package com.cai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    int ScheduleID;
    int ClassID;
    int CourseID;
    int TeacherID;
    int DayOfWeek;
    int TimeSlotID;
}
