package interfaces;

/**
 * Created by user on 2017/9/15.
 */
public class Tuple<T,R> {
    private T param1;
    private R param2;

    public Tuple(T param1, R param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public T getParam1() {
        return param1;
    }

    public R getParam2() {
        return param2;
    }
}
