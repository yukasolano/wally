package com.warren.wally.db;

import com.warren.wally.WallyApplication;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Classe base para a execução de testes que requerem fazer uso dos beans do Spring, bem como ter acesso ao banco
 * de dados e demais configurações da aplicação.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
@TestPropertySource(locations = "/application.properties")
public class WallyTestCase {

}
