package br.com.ordermanager.service.vo;

import br.com.ordermanager.controller.dto.CustomerDTO;
import br.com.ordermanager.db.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerVO {

    private Long id;

    private String name;

    public CustomerVO(Customer model) {
        this.id = model.getId();
        this.name = model.getName();
    }

    public CustomerVO(CustomerDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }
}
