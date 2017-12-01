package ccb.interaction.action;

import ccb.interaction.Interactions;

/**
 * Created by user on 2017/9/18.
 */
abstract class InteractionsSubImp {
    protected final Interactions interaction;
    public InteractionsSubImp(Interactions interaction) throws Exception{
        this.interaction = interaction;
        init();
        execute();
    }
    protected void init(){}
    abstract void execute() throws Exception ;

}
