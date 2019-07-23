package model;

/**
 * 21.07.2019 10:18
 *
 * @author Edward
 */
public interface Failable {

    boolean isFailed(int serverNumber, int nodeNumber);
}
