package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.SerieVO;
import com.warren.wally.model.bcb.BcbClient;
import com.warren.wally.model.calculadora.repository.CdiEntity;
import com.warren.wally.model.calculadora.repository.CdiRepository;
import com.warren.wally.utils.DataValor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DMcdiActor {

    @Autowired
    private BcbClient bcbClient;

    @Resource
    private CdiRepository cdiRepository;

    public void atualiza() {
        //verifica ultima data no banco
        List<CdiEntity> cdiEntity = cdiRepository.findAllByOrderByDataDesc();
        LocalDate ultimaData = cdiEntity.isEmpty() ? LocalDate.of(2015, 1, 1) : cdiEntity.get(0).getData();

        //busca dados
        List<DataValor> cdi = bcbClient.getCDI(ultimaData.plusDays(1), LocalDate.now());

        //atualzia banco
        List<CdiEntity> novosDados = cdi.stream().map(it -> {
            CdiEntity entity = new CdiEntity();
            entity.setData(it.getData());
            entity.setValor(it.getValor());
            return entity;
        }).collect(Collectors.toList());

        cdiRepository.saveAll(novosDados);
    }

    public SerieVO get() {
        List<CdiEntity> cdiEntity = cdiRepository.findAllByOrderByDataDesc();
        List<DataValor> cdi = cdiEntity.stream().map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
        SerieVO vo = new SerieVO();
        vo.setNome("CDI");
        vo.setValores(cdi);
        return vo;
    }
}
