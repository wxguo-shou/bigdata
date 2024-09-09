package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.javassist.compiler.ast.Variable;

import java.util.List;

/**
 * @author name 婉然从物
 * @create 2024-07-20 17:55
 * 定义数据访问的CRUD方法
 *      规则： 编写什么类型的SQL，就使用什么样的注解即可
 *
 *  占位符的格式：
 *      在JDBC中使用? 作为占位符
 *      在Mybatis中， 一般在参数位置使用#{xxx}作为占位符。
 *          xxx 如何编写？
 *              ①如果sql语句中只有单个占位符。
 *                      xxx可以随便写
*               ②如果sql语句中有多个占位符，占位符需要从方法传入的Bean中获取Bean的属性，填入
 *                      xxx 需要写对应属性的名字
 *
 *   --------------
 *      Mybatis提供了动态代理技术，可以快速为接口创建实例。
 *       无需手动为接口提供实现类
 *
 *   --------------
 *      在公司中有两种情况:
 *          简单查询的sql，例如对单个实体类，进行CRUD。不经常更新，我们编写在接口类中。
 *          如果当前sql语句需要经常更新,，不推荐将sql直接编写在接口类中，耦合度高，不利于维护。可以将sql编写在xml文件中，通过配置动态引入。
 *              不涉及
 */
public interface EmployeeMapper {
    // 查询单个员工
    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

    // 查询所有员工
    @Select("select * from employee")
    List<Employee> getAll();

    // 新增员工
    @Insert("insert into employee(last_name,gender,email) values(#{lastName},#{gender},#{email})")
    void insertEmployee(Employee e);

    // 更新员工
    @Update("update employee set last_name = #{lastName}, gender = #{gender}, email = #{email} where id = #{id}")
    void updateEmployee(Employee e);

    // 删除员工
    @Delete("delete from employee where id = #{id}")
    void deleteEmployeeById(Integer id);

    /*
        如果方法中有多个参数， 此时有多个选择方案。
            ①把多个参数封装为bean的属性， xxx 还写 bean的属性名即可
                繁琐
            ②可以把多个参数封装为一个Map集合， xxx 写map集合中key的名字
                繁琐
                getEmpByCondition(Map map)
                    map: {gender = 'male', id = 1}

                xxx实现： where id > #{id} and gender  = #{xxx}
            ③可以使用@Parma("xx")注解标注在参数前， 之后 可以使用 xx 来引用参数
     */
    @Select("select * from employee where id > #{b} and gender = #{a}")
    List<Employee> getEmpByCondition(@Param("a") String gender, @Param("b") Integer id);
}
