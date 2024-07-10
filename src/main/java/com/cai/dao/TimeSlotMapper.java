package com.cai.dao;

import com.cai.pojo.Timeslot;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TimeSlotMapper {
    @Select("select * from test_manage_system.timeslot")
    List<Timeslot> FindAllTimeSlot();
}
