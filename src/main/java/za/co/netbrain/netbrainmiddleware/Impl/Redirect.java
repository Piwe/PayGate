package za.co.netbrain.netbrainmiddleware.Impl;

/**
 *
 * @author stwala
 */
public class Redirect {
    
    private String redirectUrl;
    private String PAY_REQUEST_ID;
    private String PAYGATE_ID;
    private String CHECKSUM;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPAY_REQUEST_ID() {
        return PAY_REQUEST_ID;
    }

    public void setPAY_REQUEST_ID(String PAY_REQUEST_ID) {
        this.PAY_REQUEST_ID = PAY_REQUEST_ID;
    }

    public String getPAYGATE_ID() {
        return PAYGATE_ID;
    }

    public void setPAYGATE_ID(String PAYGATE_ID) {
        this.PAYGATE_ID = PAYGATE_ID;
    }

    public String getCHECKSUM() {
        return CHECKSUM;
    }

    public void setCHECKSUM(String CHECKSUM) {
        this.CHECKSUM = CHECKSUM;
    }
    
    
    
}
