package model;

import java.util.Objects;

/**
 * node entity
 *
 * 21.07.2019 10:20
 *
 * @author Edward
 */
public class Node {

    private int number;
    private boolean isFail;

    Node(int number, boolean isFail) {
        this.number = number;
        this.isFail = isFail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getNumber() == node.getNumber() &&
                isFail() == node.isFail();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), isFail());
    }

    @Override
    public String toString() {
        return "Node{" +
                "number=" + number +
                ", isFail=" + isFail +
                '}';
    }
}
