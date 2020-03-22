package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.bcb.BcbClient;
import com.warren.wally.utils.DataValor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.warren.wally.utils.DateUtils.dateOf;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestBcbConnector extends WallyTestCase {

    @Autowired
    private BcbClient bcbClient;

    @Test
    public void testBcbCDI() throws Exception {
        List<DataValor> cdi = bcbClient.getCDI(dateOf("10/07/2019"), dateOf("20/07/2019"));

        Optional<DataValor> hey = cdi.stream().findFirst();

        assertTrue(hey.isPresent());

        assertEquals(6.4, hey.get().getValor());
        assertEquals(dateOf("10/07/2019"), hey.get().getData());

    }


}
