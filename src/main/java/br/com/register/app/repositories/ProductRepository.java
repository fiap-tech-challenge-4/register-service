package br.com.register.app.repositories;

import br.com.register.app.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT a FROM Product a WHERE a.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, PageRequest pageable);

}
