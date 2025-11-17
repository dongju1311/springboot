package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {
    //step1 : 주문/결제 - 주문(Orders) 테이블 저장
    Order save(Order entity);
    //step2 : 주문/결제 - 주문상세(Order_detail) 테이블 저장, 서브쿼리, Native-Query
    @Modifying
    @Transactional
    @Query(value = """
            insert into
                    order_detail(order_code,pid,pname,size,qty,pid_total_price,discount)
                select
                    :orderCode, pid, name as pname, size, qty, total_price as pid_total_price,
                    :discount
                from view_cartList
                where cid in (:cidList)
            """, nativeQuery = true)
    int saveOrderDetail(@Param("orderCode") String orderCode,
                        @Param("discount") Integer discount,
                        @Param("cidList") List<Integer> cidList);
}
