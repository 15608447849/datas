package interfaces;

/**
 * Created by user on 2017/8/7.
 */
public class CatchBean {
    private String name;
    private String jsonFileLink;
    private String jsFileLink;
    private String resFileLink;
    private String resZip;
    private String message;

    public String getName() {
        return name;
    }

    public CatchBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getJsonFileLink() {
        return jsonFileLink;
    }

    public void setJsonFileLink(String jsonFileLink) {
        this.jsonFileLink = jsonFileLink;
    }

    public String getJsFileLink() {
        return jsFileLink;
    }

    public void setJsFileLink(String jsFileLink) {
        this.jsFileLink = jsFileLink;
    }

    public String getResFileLink() {
        return resFileLink;
    }

    public void setResFileLink(String resFileLink) {
        this.resFileLink = resFileLink;
    }

    public String getMessage() {
        return message;
    }

    public CatchBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getResZip() {
        return resZip;
    }

    public void setResZip(String resZip) {
        this.resZip = resZip;
    }
}
