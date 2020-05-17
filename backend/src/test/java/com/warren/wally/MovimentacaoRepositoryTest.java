package com.warren.wally;

import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovimentacaoRepositoryTest {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Test
    public void movimentacaoSavedWithSuccess() {

        MovimentacaoEntity entity = new MovimentacaoEntity();
        entity.setTipoMovimento(TipoMovimento.COMPRA);
        entity.setCodigo("1");
        entity.setData(DateUtils.dateOf("23/10/2019"));
        entity.setQuantidade(100);
        entity.setValorUnitario(2.5d);

        MovimentacaoEntity savedEntity = this.movimentacaoRepository.save(entity);

        Optional<MovimentacaoEntity> optEntity = this.movimentacaoRepository.findById(savedEntity.getId());

        Assert.assertTrue("Era esperado ter sido possível recuperar a entidade da base de dados.", optEntity.isPresent());
        MovimentacaoEntity foundEntity = optEntity.get();
        assertEquals(entity.getQuantidade(), foundEntity.getQuantidade());
    }

}
