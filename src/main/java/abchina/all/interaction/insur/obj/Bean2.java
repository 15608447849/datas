package abchina.all.interaction.insur.obj;

/**
 * Created by user on 2017/10/11.
 */

public class Bean2 {
    /**
     * id : 1001
     * ranking : 1
     * name : 农行无忧意外伤害保险
     * company : 农行人寿1
     * type : 其他
     * term : 30年,30年,30年,30年,30
     * qr_code : http://www.qq.com
     */

    private int id;
    private String ranking;
    private String name;
    private String company;
    private String type;
    private String term;
    private String qr_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "Bean2{" +
                "id=" + id +
                ", ranking='" + ranking + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", type='" + type + '\'' +
                ", term='" + term + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
