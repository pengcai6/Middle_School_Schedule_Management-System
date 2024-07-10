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
    private mybatisUtil() throws IOException {

        //���������ļ�·���������ֽ�����������
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
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