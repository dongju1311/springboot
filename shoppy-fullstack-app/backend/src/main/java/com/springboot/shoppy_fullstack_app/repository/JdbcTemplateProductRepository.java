package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateProductRepository implements ProductRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateProductRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll(){
        String sql = "select pid,name,price,info,rate,trim(image) as image ,imgList from product";
        List<Product> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
        return list;
    }

    @Override
    public Product findByPid(int pid){
        String sql = "select pid,name,price,info,rate,trim(image)as image,imgList from product where pid = ?";
        Product product = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Product.class),pid);
        return product;
    }

}
