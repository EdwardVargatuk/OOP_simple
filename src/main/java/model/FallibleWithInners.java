package model;

import exeptions.TransactionFailedException;
import utils.MyOptional;

import java.util.List;

/**
 * 21.07.2019 10:18
 *
 * @author Edward
 */
public interface FallibleWithInners{


    boolean isTransactionPassed();

    MyOptional<?> getInnerFallible(int number);

    List<MyOptional<? extends FallibleWithInners>> getAllPresentInnerFallible();

    int getSize();

    int getNumber();

    void doTransaction() throws TransactionFailedException;
}
