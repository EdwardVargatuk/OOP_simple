package model;

import exeptions.TransactionFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyOptional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 19.08.2019 22:39
 *
 * @author Edward
 */
class ServerTest {

    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server(555);
    }

    @Test
    void getNumber() {
        assertEquals(server.getNumber(), 555);
    }

    /**
     * size cannot be 0 and not greater than 15
     */
    @Test
    void getSize() {
        assertTrue(server.getSize() > 0);
        assertFalse(server.getSize() > 15);
    }

    @Test
    void isTransactionPassed_byDefault() {
        assertFalse(server.isTransactionPassed());
    }

    @Test
    void getInnerFallible() {
        for (int i = 0; i < server.getSize() - 1; i++) {
            MyOptional<?> innerFallible = server.getInnerFallible(i);
            if (innerFallible.isPresent()) {
                assertEquals(innerFallible.get().getClass(), Node.class);
            } else {
                assertEquals(innerFallible, MyOptional.empty());
                assertThrows(NoSuchElementException.class, innerFallible::get);
            }
        }
    }

    @Test
    void getAllPresentInnerFallible() {
        List<MyOptional<? extends FallibleWithInners>> allPresentInnerFallible = server.getAllPresentInnerFallible();
        for (int i = 0; i < allPresentInnerFallible.size() - 1; i++) {
            assertTrue(allPresentInnerFallible.get(i).isPresent());
        }
    }

    @Test
    void doTransaction_throwException() {
        TransactionFailedException transactionFailedException = assertThrows(TransactionFailedException.class, () -> {
            do {
                server.doTransaction();
            } while (server.isTransactionPassed());
        });
        assertEquals("Server № " + server.getNumber() + " not passed transaction", transactionFailedException.getMessage());
    }

    @Test
    void doTransaction() {
        try {
            server.doTransaction();
            assertTrue(server.isTransactionPassed());
        } catch (TransactionFailedException e) {
            assertFalse(server.isTransactionPassed());
        }
    }

}