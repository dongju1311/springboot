package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import com.springboot.shoppy_fullstack_app.dto.CartListResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateCartRepository implements CartRepository{
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateCartRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CartListResponse> findList(CartItem cartItem) {
        String sql = """
                select
                	m.id,
                    p.pid,
                    p.name,
                    p.image,
                    p.price,
                    c.cid,
                    c.size,
                    c.qty,
                    (select sum(c.qty*p.price)
                		from cart c
                		inner join product p
                		on c.pid = p.pid
                		where c.id = ?) as totalPrice
                from member m, product p, cart c
                where m.id = c.id
                	and p.pid = c.pid
                    and m.id = ?
        """;
        Object [] params = {cartItem.getId(), cartItem.getId()};
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CartListResponse.class),params);
    }

    @Override
    public CartItem getCount(CartItem cartItem){
        String sql = "select ifnull(sum(qty),0)as sumQty from cart where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItem.class),cartItem.getId());
    }

    @Override
    public int updateQty(CartItem cartItem) {
        String sql = "";
        if(cartItem.getType().equals("+")) {
            sql = " update cart set qty = qty + 1 where cid =? ";
        } else {
            sql = " update cart set qty = qty - 1 where cid =? ";
        }
        return jdbcTemplate.update(sql, cartItem.getCid());
    }

    @Override
    public CartItem checkQty(CartItem cartItem) {
        String sql = """
                SELECT
                      ifnull(MAX(cid), 0) AS cid,
                      COUNT(*) AS checkQty
                    FROM cart
                    WHERE pid = ? AND size = ? AND id = ?
                """;
        Object[] params = { cartItem.getPid(), cartItem.getSize(), cartItem.getId() };
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItem.class), params);
    }

    @Override
    public int add(CartItem cartItem) {
        String sql = """
                insert into cart(size, qty, pid, id, cdate)
                    values(?, ?, ?, ?, now())                
                """;
        Object [] params = {
                cartItem.getSize(),
                cartItem.getQty(),
                cartItem.getPid(),
                cartItem.getId()
        };
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int deleteItem(CartItem cartItem){
        String sql= """
                delete from cart where cid = ?
                """;
        return jdbcTemplate.update(sql, cartItem.getCid());
    }
}