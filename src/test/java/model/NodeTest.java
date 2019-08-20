package model;

import exeptions.TransactionFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MyOptional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 19.08.2019 22:38
 *
 * @author Edward
 */
class NodeTest {

    private Node node;
    @BeforeEach
    void setUp(){
        node = new Node(8);
    }

    @Test
    void getNumber() {
        assertEquals(node.getNumber(), 8);
    }

    @Test
    void doTransaction_throwException() {
        TransactionFailedException transactionFailedException = assertThrows(TransactionFailedException.class, () -> {
            do {
                node.doTransaction();
            } while (node.isTransactionPassed());
        });
        assertEquals("Node № "+ node.getNumber() + " not passed transaction", transactionFailedException.getMessage());
    }

    @Test
    void doTransaction() {
        try {
            node.doTransaction();
            assertTrue(node.isTransactionPassed());
        } catch (TransactionFailedException e) {
            assertFalse(node.isTransactionPassed());
        }
    }

    @Test
    void isTransactionPassed_byDefault() {
        assertFalse(node.isTransactionPassed());
    }

    /**
     * node not have any innerFallible
     */
    @Test
    void getInnerFallible() {
        MyOptional<?> innerFallible = node.getInnerFallible(777);
        MyOptional<?> innerFallibleTwo = node.getInnerFallible(0);
        assertFalse(innerFallible.isPresent());
        assertEquals(innerFallible, MyOptional.empty());
        assertFalse(innerFallibleTwo.isPresent());
        assertEquals(innerFallibleTwo, MyOptional.empty());
    }

    /**
     * node not have any innerFallible
     */
    @Test
    void getAllPresentInnerFallible() {
        List<MyOptional<? extends FallibleWithInners>> allPresentInnerFallible = node.getAllPresentInnerFallible();
        assertEquals(allPresentInnerFallible.size(), 1);
        assertEquals(allPresentInnerFallible.get(0), MyOptional.empty());

    }

    /**
     * node not have any innerFallible
     */
    @Test
    void getSize() {
        assertEquals(node.getSize(), 0);
    }

}