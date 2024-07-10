package com.cai.Test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class getsqlSession {
    public static <T> T getSqlSession(Class<T> mapperClass) throws IOException  {
        //�����ļ�·��
        String resource = "mybatis-config.xml";//���������ļ�
        //���������ļ�·���������ֽ�����������
        InputStream is = Resources.getResourceAsStream(resource);
        //����SqlSessionFactory���������
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        //ʹ��SqlSessionFactory������,����SqlSession����
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.getMapper(mapperClass);
    }



}
