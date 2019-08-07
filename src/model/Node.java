package model;


import exeptions.TransactionFailedException;
import utils.MyOptional;

import java.util.*;

/**
 * node entity
 *
 * 21.07.2019 10:20
 *
 * @author Edward
 */
public class Node  implements  FallibleWithInners{

    private final int number;
    private boolean transactionPassed;

    Node(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void doTransaction() throws TransactionFailedException {
        Random random = new Random();
        int randomNumber = random.nextInt(30);
        if (randomNumber==0){
            throw new TransactionFailedException("Node № "+ getNumber() + " not passed transaction");
        }
        transactionPassed = true;
    }

    @Override
    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    /**
     * @param number of node
     * @return MyOptional
     */
    @Override
    public MyOptional<FallibleWithInners> getInnerFallible(int number) {
        return MyOptional.empty();
    }

    @Override
    public List<MyOptional<? extends FallibleWithInners>> getAllPresentInnerFallible() {
        return Collections.singletonList(MyOptional.empty());
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "number=" + number +
                ", transactionPassed=" + transactionPassed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getNumber() == node.getNumber() &&
                isTransactionPassed() == node.isTransactionPassed();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), isTransactionPassed());
    }
}
