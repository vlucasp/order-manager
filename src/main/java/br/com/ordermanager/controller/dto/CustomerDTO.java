package br.com.ordermanager.controller.dto;

import br.com.ordermanager.service.vo.CustomerVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CustomerDTO {

    private Long id;

    private String name;

    public CustomerDTO(CustomerVO vo) {
        this.id = vo.getId();
        this.name = vo.getName();
    }
}
