package br.com.ordermanager.db.repository;

import br.com.ordermanager.db.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("SELECT p FROM ProductOrder p " +
            "WHERE (:orderNumber IS NULL OR p.orderNumber = :orderNumber) " +
            "AND (:registryDate IS NULL OR DATE_FORMAT(p.registryDate, '%d/%m/%Y') = :registryDate)")
    List<ProductOrder> findProductOrderByFilters(@Param("orderNumber") String orderNumber, @Param("registryDate") String registryDate);

    ProductOrder findDistinctFirstByOrderNumber(String orderNumber);
}
