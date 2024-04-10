package br.com.ordermanager.service.vo;

import br.com.ordermanager.controller.dto.OrderDTO;
import br.com.ordermanager.db.model.ProductOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderVO {

    private String numero_controle;

    private Date data_cadastro;

    private String nome;

    private BigDecimal valor;

    private Integer quantidade;

    private Long codigo_cliente;

    private BigDecimal valor_total;

    public OrderVO(ProductOrder model) {
        this.numero_controle = model.getOrderNumber();
        this.data_cadastro = model.getRegistryDate();
        this.nome = model.getProductName();
        this.valor = model.getPrice();
        this.quantidade = model.getQuantity();
        this.codigo_cliente = model.getCustomer().getId();
        this.valor_total = model.getTotalPrice();
    }

    public OrderVO(OrderDTO dto) {
        this.numero_controle = dto.getNumero_controle();
        this.data_cadastro = dto.getData_cadastro();
        this.nome = dto.getNome();
        this.valor = dto.getValor();
        this.quantidade = dto.getQuantidade();
        this.codigo_cliente = dto.getCodigo_cliente();
    }
}
