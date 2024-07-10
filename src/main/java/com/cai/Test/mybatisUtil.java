package com.cai.Test;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
//�����ࣺ��̬����+������
public class mybatisUtil {
    static SqlSessionFactory sqlSessionFactory = null;
    static  {

        //���������ļ�·���������ֽ�����������
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //����SqlSessionFactory���������
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    /*
     *�Զ�����
     */
    public static SqlSession getSession(boolean flag) {//ʹ��SqlSessionFactory������,����SqlSession����
        SqlSession sqlSession = sqlSessionFactory.openSession(flag);
        return sqlSessionFactory.openSession();
    }

    /**
     * �ֶ�����
     * @return
     */
    public static SqlSession getSession() {//ʹ��SqlSessionFactory������,����SqlSession����
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSessionFactory.openSession();
    }

    public static void closesqlSession(SqlSession sqlSession){
        if (sqlSession!=null)
            sqlSession.close();
    }
}