package model;

/**
 * 21.07.2019 10:18
 *
 * @author Edward
 */
public interface FallibleWithInners {


    boolean isFailed();

    FallibleWithInners getInnerFallible(int number);

    int getSize();

    int getNumber();
}
