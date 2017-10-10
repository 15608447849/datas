package ccb.accountGold.obj;

import javax.xml.bind.annotation.*;
import java.beans.Transient;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 *
<ReferencePriceSettlements>
     <ReferencePriceSettlement name="1">
     <PM_Txn_Vrty_Cd>01</PM_Txn_Vrty_Cd>
     <CcyCd>156</CcyCd>
     <Cst_Buy_Prc>280.45</Cst_Buy_Prc>
     <MdlRate>280.2</MdlRate>
     <Cst_Sell_Prc>279.95</Cst_Sell_Prc>
     <Tms>2017-09-15 13:47:48.838</Tms>
     </ReferencePriceSettlement>
 </ReferencePriceSettlements>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ReferencePriceSettlements")
public class ReferencePriceSettlements {
    @XmlElement(name = "ReferencePriceSettlement")
    private List<ReferencePriceSettlement> list;

    public List<ReferencePriceSettlement> getList() {
        return list;
    }

    public void setList(List<ReferencePriceSettlement> list) {
        this.list = list;
    }

}
