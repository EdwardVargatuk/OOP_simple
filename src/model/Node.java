package model;


import java.util.Objects;

/**
 * node entity
 *
 * 21.07.2019 10:20
 *
 * @author Edward
 */
public class Node  implements  FallibleWithInners{

    private final int number;
    private boolean failed;

    Node(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    void setFailed() {
        this.failed = true;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    /**
     * @param number of node
     * @return null because is on the bottom
     */
    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "number=" + number +
                ", failed=" + failed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getNumber() == node.getNumber() &&
                isFailed() == node.isFailed();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), isFailed());
    }
}
