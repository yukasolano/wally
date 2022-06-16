package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.SerieVO;
import com.warren.wally.model.dadosmercado.bcb.BcbClient;
import com.warren.wally.model.dadosmercado.repository.CdiEntity;
import com.warren.wally.model.dadosmercado.repository.CdiRepository;
import com.warren.wally.utils.BussinessDaysCalendar;
import com.warren.wally.utils.DataValor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DMcdiActor {

    @Autowired
    private BcbClient bcbClient;

    @Resource
    private CdiRepository cdiRepository;

    @Resource
    private BussinessDaysCalendar bc;

    private List<DataValor> cdi = new ArrayList<>();

    private LocalDate startDate = LocalDate.of(2015, 1, 1);

    public void limpa() {
        cdiRepository.deleteAll();
        cdi.clear();
    }

    public void atualiza() {
        //verifica ultima data no banco
        List<CdiEntity> cdiEntity = cdiRepository.findAllByOrderByDataDesc();
        LocalDate ultimaData = cdiEntity.isEmpty() ? startDate : cdiEntity.get(0).getData();

        //busca dados
        List<DataValor> buscaCdi = bcbClient.getCDI(ultimaData.plusDays(1), LocalDate.now());

        //atualiza banco
        List<CdiEntity> novosDados = buscaCdi.stream().map(it -> {
            CdiEntity entity = new CdiEntity();
            entity.setData(it.getData());
            entity.setValor(it.getValor());
            return entity;
        }).collect(Collectors.toList());

        cdiRepository.saveAll(novosDados);
        cdi.clear();
    }

    public SerieVO get() {
        List<CdiEntity> cdiEntity = cdiRepository.findAllByOrderByDataDesc();
        List<DataValor> buscaCdi = cdiEntity.stream().map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
        SerieVO vo = new SerieVO();
        vo.setNome("CDI");
        vo.setValores(buscaCdi);
        return vo;
    }


    public List<DataValor> filtraCDI(LocalDate dataInicio, LocalDate dataFim) {

        return getCdis().stream().filter(Objects::nonNull)
                .filter(dt -> dt.getData().isAfter(dataInicio)
                        && (dt.getData().isBefore(dataFim) || dt.getData().isEqual(dataFim)))
                .collect(Collectors.toList());
    }

    private List<DataValor> getCdis() {
        if (cdi.isEmpty()) {
            cdiRepository.findAllByOrderByData().forEach(it -> cdi.add(new DataValor(it.getData(), it.getValor())));
            completaDados(cdi, LocalDate.now());
        }
        return cdi;
    }

    private void completaDados(List<DataValor> cdi, LocalDate dataFim) {
        LocalDate today = bc.getNextWorkDay(dataFim);
        LocalDate lastDate = cdi.get(cdi.size() - 1).getData();
        double lastValue = cdi.get(cdi.size() - 1).getValor();
        while (lastDate.isBefore(today)) {
            lastDate = bc.getNextWorkDay(lastDate.plusDays(1));
            cdi.add(new DataValor(lastDate, lastValue));
        }
    }
}
