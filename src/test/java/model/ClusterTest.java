package model;

import exeptions.TransactionFailedException;
import org.junit.jupiter.api.*;
import utils.MyOptional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 18.08.2019 18:19
 *
 * @author Edward
 */
class ClusterTest {

    private Cluster cluster;

    @BeforeEach
    void setUp() {
        cluster = new Cluster();
    }

    @Test
    void isTransactionPassed_byDefault() {
        assertFalse(cluster.isTransactionPassed());
    }


    /**
     * size cannot be 0 and not greater than 15
     */
    @Test
    void getSize() {
        assertTrue(cluster.getSize() > 0);
        assertFalse(cluster.getSize() > 15);
    }


    /**
     * by default number is 0
     */
    @Test
    void getNumber() {
        assertEquals(cluster.getNumber(), 0);
    }

    @Test
    void doTransaction_throwException() {
        TransactionFailedException transactionFailedException = assertThrows(TransactionFailedException.class, () -> {
            do {
                cluster.doTransaction();
            } while (cluster.isTransactionPassed());
        });
        assertEquals("Cluster has failed transaction", transactionFailedException.getMessage());
    }

    @Test
    void doTransaction() {
        try {
            cluster.doTransaction();
            assertTrue(cluster.isTransactionPassed());
        } catch (TransactionFailedException e) {
            assertFalse(cluster.isTransactionPassed());
        }
    }

    @Test
    void getInnerFallible() {
        for (int i = 0; i < cluster.getSize() - 1; i++) {
            MyOptional<?> innerFallible = cluster.getInnerFallible(i);
            if (innerFallible.isPresent()) {
                assertEquals(innerFallible.get().getClass(), Server.class);
            } else {
                assertEquals(innerFallible, MyOptional.empty());
                assertThrows(NoSuchElementException.class, innerFallible::get);
            }
        }

        assertNotNull(cluster.getInnerFallible(cluster.getSize() - 1));
        assertNotNull(cluster.getInnerFallible(0));
    }

    @Test
    void getAllPresentInnerFallible() {
        List<MyOptional<? extends FallibleWithInners>> allPresentInnerFallible = cluster.getAllPresentInnerFallible();
        for (int i = 0; i < allPresentInnerFallible.size() - 1; i++) {
            assertTrue(allPresentInnerFallible.get(i).isPresent());
        }
    }
}