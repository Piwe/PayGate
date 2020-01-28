package za.co.netbrain.netbrainmiddleware;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.soap.SOAPMessage;
import za.co.netbrain.netbrainmiddleware.Impl.NetbrainWebServiceImpl;

/**
 *
 * @author stwala
 */
@WebService(serviceName = "NetbrainWebService")
@Stateless()
public class NetbrainWebService {
    
    private NetbrainWebServiceImpl netbrainWebServiceImpl;

    public NetbrainWebService() {
        this.netbrainWebServiceImpl = new NetbrainWebServiceImpl();
    }

    /**
     * This is a SinglePaymentRequest web service operation
     *
     * @param cardNumber
     * @param cardExpiryDate
     * @return
     */
    @WebMethod(operationName = "singlePaymentRequest")
    public SOAPMessage singlePaymentRequest(@WebParam(name = "cardNumber") String cardNumber, @WebParam(name = "cardExpiryDate") String cardExpiryDate) throws Exception {        
        return netbrainWebServiceImpl.getSOAPResponse(cardNumber, cardExpiryDate);
    }
    
    @WebMethod(operationName = "serviceHealthCheck")
    public String serviceHealthCheck() {
       return netbrainWebServiceImpl.getServiceHealthCheck();
    }

}
