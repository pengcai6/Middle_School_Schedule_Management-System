package com.cai.Test;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
//工具类：静态方法+构造器
public class mybatisUtil {
    static SqlSessionFactory sqlSessionFactory = null;
    private mybatisUtil() throws IOException {

        //基于配置文件路径，创建字节输入流对象
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //创建SqlSessionFactory工厂类对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    /*
     *自动事务
     */
    public static SqlSession getSession(boolean flag) {//使用SqlSessionFactory工厂类,创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession(flag);
        return sqlSessionFactory.openSession();
    }

    /**
     * 手动事务
     * @return
     */
    public static SqlSession getSession() {//使用SqlSessionFactory工厂类,创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSessionFactory.openSession();
    }

    public static void closesqlSession(SqlSession sqlSession){
        if (sqlSession!=null)
            sqlSession.close();
    }
}