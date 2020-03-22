package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.DadosMercadoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DadosMercadoActor {

    @Autowired
    private DMcdiActor cdiActor;

    @Autowired
    private  DMipcaActor ipcaActor;

    public void atualizaDadosMercado() {
        cdiActor.atualiza();
        ipcaActor.atualiza();
    }


    public DadosMercadoVO busca() {
        DadosMercadoVO dadosMercadoVO = new DadosMercadoVO();
        dadosMercadoVO.addSerie(cdiActor.get());
        dadosMercadoVO.addSerie(ipcaActor.get());
        return dadosMercadoVO;
    }

}
