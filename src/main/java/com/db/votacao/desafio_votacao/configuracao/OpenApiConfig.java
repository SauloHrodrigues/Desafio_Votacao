package com.db.votacao.desafio_votacao.configuracao;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Votação API")
                        .version("v1")
                        .description(
                                "No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você \n" +
                                        "deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação. "
                        )
                        .contact(new Contact()
                                .name("Saulo Henrique Rodrigues")
                                .email("saulo.rodrigues@db.tec.br"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local")
                ));
    }
}