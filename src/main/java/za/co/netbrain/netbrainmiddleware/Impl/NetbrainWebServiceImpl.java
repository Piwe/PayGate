package za.co.netbrain.netbrainmiddleware.Impl;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 *
 * @author stwala
 */
public class NetbrainWebServiceImpl {

    private SOAPMessage soapMessage;
    
    // Send SOAP Message to SOAP Server
    private final String url = "https://secure.paygate.co.za/PayHost/process.trans";

    public  SOAPMessage getSOAPResponse(String cardNumber, String cardExpiryDate) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
                
        SOAPMessage soapResponse = soapConnection.call(getSinglePaymentRequest(cardNumber, cardExpiryDate), url);

        // print SOAP Response
        System.out.print("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        soapConnection.close();
        return soapResponse;
    }
    
    
    public SOAPMessage getSinglePaymentRequest(String cardNumber, String cardExpiryDate) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://www.paygate.co.za/PayHOST";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ns1", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement singlePaymentRequest = soapBody.addChildElement("SinglePaymentRequest", "ns1");
        SOAPElement cardPaymentRequest = singlePaymentRequest.addChildElement("CardPaymentRequest", "ns1");
        SOAPElement account = cardPaymentRequest.addChildElement("Account", "ns1");
        SOAPElement payGateId = account.addChildElement("PayGateId", "ns1");
        payGateId.addTextNode("10011064270"); // 10011064270 // 10011072130
        SOAPElement password = account.addChildElement("Password", "ns1");
        password.addTextNode("test");
        SOAPElement customer = cardPaymentRequest.addChildElement("Customer", "ns1");
        SOAPElement title = customer.addChildElement("Title", "ns1");
        title.addTextNode("Mr");
        SOAPElement firstName = customer.addChildElement("FirstName", "ns1");
        firstName.addTextNode("John");
        SOAPElement lastName = customer.addChildElement("LastName", "ns1");
        lastName.addTextNode("Doe");
        SOAPElement telephone = customer.addChildElement("Telephone", "ns1");
        telephone.addTextNode("0826659657");
        SOAPElement mobile = customer.addChildElement("Mobile", "ns1");
        mobile.addTextNode("0826659657");
        SOAPElement email = customer.addChildElement("Email", "ns1");
        email.addTextNode("sim_piwe@hotmail.com");
        SOAPElement paramCardNumber = cardPaymentRequest.addChildElement("CardNumber", "ns1");
        paramCardNumber.setTextContent(cardNumber); // 5200000000000015
        SOAPElement paramCardExpiryDate = cardPaymentRequest.addChildElement("CardExpiryDate", "ns1");
        paramCardExpiryDate.setTextContent(cardExpiryDate); // 122017
        SOAPElement cvv = cardPaymentRequest.addChildElement("CVV", "ns1");
        cvv.setTextContent("999");
        SOAPElement budgetPeriod = cardPaymentRequest.addChildElement("BudgetPeriod", "ns1");
        budgetPeriod.setTextContent("0");
        SOAPElement redirect = cardPaymentRequest.addChildElement("Redirect", "ns1");
        SOAPElement notifyUrl = redirect.addChildElement("NotifyUrl", "ns1");
        notifyUrl.setTextContent("http://gatewaymanagementservices.com/ws/gotNotify.php");
        SOAPElement returnUrl = redirect.addChildElement("ReturnUrl", "ns1");
        returnUrl.setTextContent("http://gatewaymanagementservices.com/payhost/result.php");
        SOAPElement order = cardPaymentRequest.addChildElement("Order", "ns1");
        SOAPElement merchantOrderId = order.addChildElement("MerchantOrderId", "ns1");
        merchantOrderId.setTextContent("INV101");
        SOAPElement currency = order.addChildElement("Currency", "ns1");
        currency.setTextContent("ZAR");
        SOAPElement amount = order.addChildElement("Amount", "ns1");
        amount.setTextContent("101");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + "VerifyEmail");

        soapMessage.saveChanges();
        setSoapMessage(soapMessage);

        /* Print the request message */
        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
        
    }

    public SOAPMessage getSoapMessage() {
        return soapMessage;
    }

    public void setSoapMessage(SOAPMessage soapMessage) {
        this.soapMessage = soapMessage;
    }

    public String getServiceHealthCheck() {
        return "Ok";
    }

}
