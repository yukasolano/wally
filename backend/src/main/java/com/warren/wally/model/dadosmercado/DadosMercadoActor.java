package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.DadosMercadoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DadosMercadoActor {

    @Resource
    private FeriadoActor feriadoActor;
    @Autowired
    private DMcdiActor cdiActor;

    @Autowired
    private  DMipcaActor ipcaActor;

    @Autowired
    private DMequitiesActor equitiesActor;

    public void atualizaDadosMercado() {
        feriadoActor.atualiza();
        cdiActor.atualiza();
        ipcaActor.atualiza();
        equitiesActor.atualiza();
    }


    public DadosMercadoVO busca() {
        DadosMercadoVO dadosMercadoVO = new DadosMercadoVO();
        dadosMercadoVO.addSerie(cdiActor.get());
        dadosMercadoVO.addSerie(ipcaActor.get());
        dadosMercadoVO.addSeries(equitiesActor.get());
        return dadosMercadoVO;
    }

    public void limpa() {
        feriadoActor.limpa();
        cdiActor.limpa();
        ipcaActor.limpa();
        equitiesActor.limpa();
    }
}
