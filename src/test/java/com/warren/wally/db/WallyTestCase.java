package com.warren.wally.db;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe base para a execução de testes que requerem fazer uso dos beans do Spring, bem como ter acesso ao banco
 * de dados e demais configurações da aplicação.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WallyTestCase {

}
