package com.cai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Timeslot {
   int TimeSlotID;
    LocalTime StartTime;
    LocalTime   EndTime;

}
