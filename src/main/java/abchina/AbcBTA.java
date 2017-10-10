package abchina;

import interfaces.ActionCall;
import interfaces.BaseThreadAdapter;

/**
 * Created by user on 2017/10/10.
 */
public abstract class AbcBTA extends BaseThreadAdapter {
        protected AbcBTA(ActionCall action) {
            super(Abchina.getInstance(), action);
        }
}
