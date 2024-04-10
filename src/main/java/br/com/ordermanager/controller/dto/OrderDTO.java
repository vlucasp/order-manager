package br.com.ordermanager.controller.dto;

import br.com.ordermanager.service.vo.OrderVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String numero_controle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date data_cadastro;

    private String nome;

    private BigDecimal valor;

    private Integer quantidade;

    private Long codigo_cliente;

    private BigDecimal valor_total;

    public OrderDTO(OrderVO vo) {
        this.numero_controle = vo.getNumero_controle();
        this.data_cadastro = vo.getData_cadastro();
        this.nome = vo.getNome();
        this.valor = vo.getValor();
        this.quantidade = vo.getQuantidade();
        this.codigo_cliente = vo.getCodigo_cliente();
        this.valor_total = vo.getValor_total();
    }
}
