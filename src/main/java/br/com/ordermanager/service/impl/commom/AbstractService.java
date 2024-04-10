package br.com.ordermanager.service.impl.commom;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import java.util.Properties;

@Service
public abstract class AbstractService {
    private static final String ERRO_OBJETO_NAO_ENCONTRADO = "erro.objeto.nao.encontrado";

    private static final String ERRO_CONSULTA_VAZIA = "erro.consulta.vazia";

    @Autowired
    @Qualifier("messages")
    private Properties mensagens;

    public String objectNotFoundMessage(String objeto) {
        return format(mensagens.getProperty(ERRO_OBJETO_NAO_ENCONTRADO), objeto);
    }

    public String emptyResponseMessage() {
        return mensagens.getProperty(ERRO_CONSULTA_VAZIA);
    }

    public static void copyProperties(Object source, Object dest, String... propertyNames) {
        if (source == null) {
            throw new IllegalArgumentException("\"source\": null");
        } else if (dest == null) {
            throw new IllegalArgumentException("\"dest\": null");
        } else if (propertyNames != null) {
            try {
                for (String propertyName : propertyNames) {
                    Object sourcePropertyValue = PropertyUtils.getProperty(source, propertyName);
                    PropertyUtils.setProperty(dest, propertyName, sourcePropertyValue);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao mapear propriedades do objeto.", e);
            }
        }
    }
}
