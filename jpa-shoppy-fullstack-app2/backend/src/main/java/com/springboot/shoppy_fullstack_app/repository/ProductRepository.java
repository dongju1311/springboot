package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.entity.Product;
import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.entity.ProductReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll(); //상품 전체 리스트 조회
    Product findByPid(int pid); //상품 상세 조회
//    @Query("select d from ProductDetailinfo d where d.pid = :pid")
//    ProductDetailinfo findProductDetailinfo(@Param("pid") int pid);
    @Query("""
            select distinct p from Product p left join p.detailinfo di
                where p.pid =:pid
            """)
    Optional<Product> findProductWithDetail(@Param("pid")int pid); //상품 상세 - 디테일 탭
    @Query("""
            select distinct p from Product p left join p.qna q
                        where p.pid =:pid
            """)
    Optional<Product> findProductWithQna(@Param("pid") int pid);  //상품 상세 - qna 탭
    @Query("select r from ProductReturn r")
    ProductReturn findReturn();
}
