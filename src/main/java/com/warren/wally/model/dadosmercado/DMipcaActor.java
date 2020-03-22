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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DMipcaActor {

    @Autowired
    private BcbClient bcbClient;

    @Resource
    private IpcaRepository ipcaRepository;

    public void atualiza() {
        //verifica ultima data no banco
        List<IpcaEntity> ipcaEntities = ipcaRepository.findAllByOrderByDataDesc();
        LocalDate ultimaData = ipcaEntities.isEmpty() ? LocalDate.of(2015, 1, 1) : ipcaEntities.get(0).getData();

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
    }

    public SerieVO get() {
        List<IpcaEntity> ipcaEntities = ipcaRepository.findAllByOrderByDataDesc();
        List<DataValor> ipca = ipcaEntities.stream().map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
        SerieVO vo = new SerieVO();
        vo.setNome("IPCA");
        vo.setValores(ipca);
        return vo;
    }
}
