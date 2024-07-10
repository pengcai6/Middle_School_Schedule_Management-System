package com.cai.dao;

import com.cai.pojo.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClassMapper {
    @Select("select *from test_manage_system.class")
    List<Class> FindAllClass();
    @Delete("DELETE FROM test_manage_system.class WHERE ClassID = #{classID}")
    void deleteByClassID(@Param("classID") int classID);

    @Delete("DELETE FROM test_manage_system.class WHERE ClassName = #{className}")
    void deleteByClassName(@Param("className") String className);

    @Insert("INSERT INTO Class (ClassName) VALUES (#{className})")
    void insertClass(String className);

    @Select("select * from test_manage_system.class where ClassID= #{i}")
    List<Class> getClassById(int i);
    @Select("select * from test_manage_system.class where ClassName= #{searchText}")
    List<Class> getClassByName(String searchText);

    @Update("UPDATE class SET className= #{className} WHERE classID = #{classID}")
    void updateClass(Class updatedClass);
}
