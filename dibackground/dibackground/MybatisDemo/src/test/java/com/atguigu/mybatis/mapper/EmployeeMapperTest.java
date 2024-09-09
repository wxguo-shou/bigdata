package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author name 婉然从物
 * @create 2024-07-20 18:22
 *
 * JDBC中程序是如何获取数据库连接的？
 *      Connection
 *
 * Mybatis中如何获取数据库连接？
 *      SqlSession： SqlSessionFactory中获取。
 *
 *      注意事项：
 *          SqlSession 不是线程安全的， 不建议作为属性或静态属性。
 *          在每个方法中创建各自独立使用的SqlSession对象。
 *
 *  ---------------
     *  ①调用方法，根据方法的参数，填充sql语句的占位符
     *  ②将sql语句发送给ysql服务器执行，接收返回的
     *  ③将返回的结果封装为数据模型
     *      a)根据返回值类型，使用反射创建一个数据模型对象
     *          Employee e = new Employe();
     *      b)将查询的结果的列赋值给 数据模型对象的属性
     *          规则:调用 数据模型的 setXxx(列值)
     *          xxx指查询返回的列名。
     *          e.setId(1):
     *          e.setLast_name("Tom");
     *          ...
     *
     * 解决思路:
     *  ①手动为Employee提供setLast_name方法
     *      不优雅
     *  ②为sql语句返回的列起别名
     *  select id,last_name lastName,gender,email from employee where id = #lid}
     *      不优雅，工作量大
     *  ③方便方式:
     *  通过设置，让Mybatis自动把 数据库的下划线命名方式转换为java流行的驼峰式命名
     *  last_name ----> lastName
     *  方便，最优雅
 */
public class EmployeeMapperTest {
    private SqlSessionFactory sqlSessionFactory;
    {
        // 读取配置文件
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从配置文件中构造SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

/*
    绑定异常： 一般指sql和接口要调用的方法无法正常绑定。
    org.apache.ibatis.binding.BindingException:
        EmployeeMapper 所对应的sql无法被 MapperRegistry 所知晓。
        MapperRegistry： 负责Dao中方法对应的sql映射
    Type interface com.atguigu.mybatis.mapper.EmployeeMapper is not
 */
    @Test
    public void getEmployee() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            Employee employee = mapper.getEmpById(1);

            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            List<Employee> employees = mapper.getAll();

            System.out.println(employees);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void insertEmployee() {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "zhangsan", "male", "zhangsan@qq.com");

            mapper.insertEmployee(employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void updateEmployee() {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(2);
            employee.setLastName("LiSi");
            mapper.updateEmployee(employee);
        } finally {
            sqlSession.close();
        }
    }

    /*
        读： 查询
        写： insert,update,delete 必须提交事务才有效。
            原生JDBC，自动提交事务。
            Mybatis中需要进行设置!
     */
    @Test
    public void deleteEmployeeById() {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            mapper.deleteEmployeeById(1);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void getEmpByCondition() {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        try {
            // 编写业务代码  mapper就是EmployeeMapper的一个实例
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            List<Employee> emps = mapper.getEmpByCondition("male", 1);

            System.out.println(emps);
        } finally {
            sqlSession.close();
        }
    }
}