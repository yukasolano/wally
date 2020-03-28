package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.SerieVO;
import com.warren.wally.model.bcb.BcbClient;
import com.warren.wally.model.calculadora.repository.IpcaEntity;
import com.warren.wally.model.calculadora.repository.IpcaRepository;
import com.warren.wally.utils.DataValor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DMipcaActor {

    @Autowired
    private BcbClient bcbClient;

    @Resource
    private IpcaRepository ipcaRepository;

    private Map<LocalDate, Double> ipca = new HashMap<>();

    private Double ipcaInical = 1046.2758;
    private LocalDate startDate = LocalDate.of(2015, 1, 1);

    public void atualiza() {
        //verifica ultima data no banco
        List<IpcaEntity> ipcaEntities = ipcaRepository.findAllByOrderByDataDesc();
        LocalDate ultimaData = ipcaEntities.isEmpty() ? startDate : ipcaEntities.get(0).getData();

        //busca dados
        List<DataValor> cdi = bcbClient.getIPCA(ultimaData.plusDays(1), LocalDate.now());

        //atualzia banco
        List<IpcaEntity> novosDados = cdi.stream().map(it -> {
            IpcaEntity entity = new IpcaEntity();
            entity.setData(it.getData());
            entity.setValor(it.getValor());
            return entity;
        }).collect(Collectors.toList());

        ipcaRepository.saveAll(novosDados);

        ipca.clear();
    }

    public SerieVO get() {
        List<IpcaEntity> ipcaEntities = ipcaRepository.findAllByOrderByDataDesc();
        List<DataValor> ipcas = ipcaEntities.stream().map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
        SerieVO vo = new SerieVO();
        vo.setNome("IPCA");
        vo.setValores(ipcas);
        return vo;
    }

    public double find(LocalDate dataRef) {
        if (ipca.isEmpty()) {
            Double ipcaAcum = ipcaInical;
            LocalDate lastDate = startDate;
            double lastValue = 0d;
            for (IpcaEntity entity :ipcaRepository.findAllByOrderByData()) {
                ipcaAcum = ipcaAcum*(1+entity.getValor()/100d);
                ipca.put(entity.getData(), ipcaAcum);
                lastDate = entity.getData();
                lastValue = entity.getValor();
            }
            completaDados(lastValue, lastDate, LocalDate.now());
        }
        return ipca.get(dataRef);
    }

    private void completaDados(Double lastValue, LocalDate dataInicio, LocalDate dataFim) {
        LocalDate nextMonth = dataFim.withDayOfMonth(1).plusMonths(1);
        LocalDate lastDate = dataInicio;

        double lastAcum = ipca.get(lastDate);
        while (lastDate.isBefore(nextMonth)) {
            lastDate = lastDate.plusMonths(1);
            lastAcum = lastAcum * (1 + lastValue / 100);
            ipca.put(lastDate, lastAcum);
        }
    }

}
