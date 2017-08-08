package interfaces;

/**
 * Created by user on 2017/8/7.
 */
public interface ActionCall {
    void error(Exception e);
    void onComplete(CatchBean catchBean);
}
