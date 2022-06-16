package com.warren.wally.model.dadosmercado;

import com.warren.wally.file.FileFeriadoReader;
import com.warren.wally.model.dadosmercado.repository.FeriadoEntity;
import com.warren.wally.model.dadosmercado.repository.FeriadoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Component
public class FeriadoActor {


    @Resource
    private FeriadoRepository feriadoRepository;

    @Resource
    private FileFeriadoReader reader;

    public void atualiza() {
        if(!feriadoRepository.findAll().iterator().hasNext()) {
            feriadoRepository.saveAll(
            reader.read().stream().map(it -> {
               FeriadoEntity entity = new FeriadoEntity();
               entity.setData(it);
               return entity;
            }).collect(Collectors.toList()));
        }
    }

    public void limpa() {
        feriadoRepository.deleteAll();
    }
}
