package com.springboot.shoppy_fullstack_app.repository;

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
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    //장바구니 아이템 삭제
    @Modifying
    @Query("delete from CartItem c where c.cid =:cid")
    int deleteItem(@Param("cid")int cid);

    //장바구니 아이템 카운트
//    @Query("""
//                select ifnull(sum(c.qty), 0) as sumQty from CartItem c where c.member.id = :id
//            """)
    @Query("""
            select coalesce(sum(c.qty),0) from CartItem c where c.member.id =:id
            """)
    int countById(@Param("id") String id);


    //장바구니 전체리스트 조회
    @Query("""
           select c 
              from CartItem c
              join fetch c.product p
              join fetch c.member m
                 where m.id =:id
           """)
    List<CartItem> findList(@Param("id") String id);

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
                where c.product.pid =:pid and c.size =:size and c.member.id =:id
                group by c.cid
            """)
    CartCheckQtyDto checkQty(@Param("pid") int pid,
                             @Param("size") String size,
                             @Param("id") String id);

    //장바구니 상품 추가
    CartItem save(CartItem cartItem);

//    //step3: 주문/결제 - 장바구니(Cart) 아이템 삭제
//    @Modifying
//    @Query("delete from CartItem c where c.cid in (:cidList)")
//    int deleteItemList(@Param("cidList") List<Integer> cidList);
}
