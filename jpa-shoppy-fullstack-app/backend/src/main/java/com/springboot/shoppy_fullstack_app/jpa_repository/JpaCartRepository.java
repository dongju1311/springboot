package com.springboot.shoppy_fullstack_app.jpa_repository;

import com.springboot.shoppy_fullstack_app.dto.CartCheckQtyDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;
import com.springboot.shoppy_fullstack_app.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCartRepository extends JpaRepository<CartItem, Integer> {
    //장바구니 아이템 삭제
    @Modifying
    @Query("delete from CartItem c where c.cid =:cid")
    int deleteItem(@Param("cid")int cid);

    //장바구니 아이템 카운트 - Native Query 방식
    @Query(value = """
                select ifnull(sum(qty), 0) as sumQty from cart where id = :id
            """, nativeQuery = true)
    int countById(@Param("id") String id);


    //장바구니 전체리스트 조회
    @Query("""
           select new com.springboot.shoppy_fullstack_app.dto.CartListResponseDto(v)
              from CartListView v
              where v.id =:id
           """)
    List<CartListResponseDto> findByUsername(@Param("id") String id);

    //장바구니 상품 수량 업데이트
    @Modifying
    @Query("update CartItem c set c.qty = c.qty + 1 where c.cid =:cid")
    int increaseQty(@Param("cid") int cid);
    @Modifying
    @Query("update CartItem c set c.qty = c.qty - 1 where c.cid =:cid")
    int decreaseQty(@Param("cid") int cid);

    //장바구니 수량 체크
    @Query("""
            select new com.springboot.shoppy_fullstack_app.dto.CartCheckQtyDto(c.cid, count(c))
                from CartItem c
                where c.pid =:pid and c.size =:size and c.id =:id
                group by c.cid
            """)
    CartCheckQtyDto checkQty(@Param("pid") int pid,
                             @Param("size") String size,
                             @Param("id") String id);

    //장바구니 상품 추가
    CartItem save(CartItem cartItem);

}
