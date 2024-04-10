package br.com.ordermanager.db.model;

import br.com.ordermanager.service.vo.CustomerVO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Customer(CustomerVO vo) {
        this.id = vo.getId();
        this.name = vo.getName();
    }
}
