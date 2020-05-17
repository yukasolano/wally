package com.warren.wally.model.dadosmercado.bcb;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BcbClientConfiguration {

    //https://api.bcb.gov.br/dados/serie/bcdata.sgs.4389/dados?formato=json&dataInicial=01/01/2010&dataFinal=31/12/2016
    @Bean
    public BcbConnector client() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(BcbConnector.class))
                .logLevel(Logger.Level.BASIC)
                .options(new Request.Options(3600000, 3600000))
                .target(BcbConnector.class, "https://api.bcb.gov.br/dados/serie/");
    }

}
