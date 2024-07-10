package com.cai.Test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class getsqlSession {
    public static <T> T getSqlSession(Class<T> mapperClass) throws IOException  {
        //配置文件路径
        String resource = "mybatis-config.xml";//核心配置文件
        //基于配置文件路径，创建字节输入流对象
        InputStream is = Resources.getResourceAsStream(resource);
        //创建SqlSessionFactory工厂类对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        //使用SqlSessionFactory工厂类,创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.getMapper(mapperClass);
    }



}
