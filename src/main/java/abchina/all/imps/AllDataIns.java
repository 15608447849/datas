package abchina.all.imps;

import abchina.all.AllData;

import java.util.ArrayList;

/**
 * Created by user on 2017/10/10.
 */
public abstract class AllDataIns {
    protected final AllData mData;

    public AllDataIns(AllData mData)  throws Exception {
        this.mData = mData;
        init();
        execute();
    }

    protected abstract void init();

    public abstract void execute() throws Exception;


}

