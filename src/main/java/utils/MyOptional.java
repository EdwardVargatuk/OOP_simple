package utils;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 04.08.2019 19:18
 *
 * @author Edward
 */
public class MyOptional<T> {

    private T value;
    private static MyOptional<?> EMPTY = new MyOptional<>();

    private MyOptional() {
        this.value = null;
    }

    public MyOptional(T value) {
        Random random = new Random();
        boolean b = random.nextBoolean();
        if (b) {
            this.value = value;
        } else {
            this.value = null;
        }
    }

    public static <T> MyOptional<T> empty() {
        @SuppressWarnings("unchecked")
        MyOptional<T> t = (MyOptional<T>) EMPTY;
        return t;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }

}
