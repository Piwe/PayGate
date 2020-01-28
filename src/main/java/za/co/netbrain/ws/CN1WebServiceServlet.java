/*
 * This is generated code, copyright free and unrestricted
 */
package za.co.netbrain.ws;

import com.codename1.proxy.server.ProxyServerHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import za.co.netbrain.netbrainmiddleware.Impl.NetbrainWebServiceImpl;
import za.co.netbrain.netbrainmiddleware.Impl.Redirect;
import za.co.paygate.KeyValueType;
import za.co.paygate.SinglePaymentResponse;

/**
 * This is a generated servlet that maps calls to the Server class static
 * methods, your code should go in that class!
 *
 * @author Codename One
 */
@WebServlet(name = "CN1WebServiceServlet", urlPatterns = {"/cn1proxy"})
public class CN1WebServiceServlet extends HttpServlet {

    private String response_1;
    private String response_2;
    private String[] redirectValues;
    private NetbrainWebServiceImpl netbrainWebServiceImpl = new NetbrainWebServiceImpl();
    private static final ProxyServerHelper.WSDefinition def_serviceHealthCheck = ProxyServerHelper.createServiceDefinition("serviceHealthCheck", ProxyServerHelper.TYPE_STRING);
    private static final ProxyServerHelper.WSDefinition def_singlePaymentRequest = ProxyServerHelper.createServiceDefinition("singlePaymentRequest", ProxyServerHelper.TYPE_STRING, ProxyServerHelper.TYPE_STRING, ProxyServerHelper.TYPE_STRING);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Webservice access only!</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DataInputStream di = new DataInputStream(request.getInputStream());
        String methodName = di.readUTF();

        SOAPMessage myMessage = null;

        if (methodName.equals("singlePaymentRequest")) {
            String[] args = ProxyServerHelper.readMethodArguments(di, def_singlePaymentRequest);

            String[] myParameters = args;

            String myCardNumber = myParameters[0];
            String myCardExpiryDate = myParameters[1];
            // Pass parameters to PayHost
            String transactionStatusDescription = null;
            String resultDescription = null;

            try {
                myMessage = netbrainWebServiceImpl.getSOAPResponse(myCardNumber, myCardExpiryDate);

                Unmarshaller unmarshaller = JAXBContext.newInstance(SinglePaymentResponse.class).createUnmarshaller();
                SinglePaymentResponse paymentResponse = (SinglePaymentResponse) unmarshaller.unmarshal(myMessage.getSOAPBody().extractContentAsDocument());

                if (paymentResponse != null) {
                    transactionStatusDescription = paymentResponse.getCardPaymentResponse().getStatus().getTransactionStatusDescription();
                    resultDescription = paymentResponse.getCardPaymentResponse().getStatus().getResultDescription();

                }

                String redirectUrl = null;
                try {
                    redirectUrl = paymentResponse.getCardPaymentResponse().getRedirect().getRedirectUrl();
                } catch (NullPointerException nlp) {

                }

                if (redirectUrl != null) {

                    List<KeyValueType> params = paymentResponse.getCardPaymentResponse().getRedirect().getUrlParams();

                    Redirect redirect = new Redirect();
                    redirect.setRedirectUrl(redirectUrl);

                    for (KeyValueType keyValueType : params) {
                        if ("PAY_REQUEST_ID".equals(keyValueType.getKey())) {
                            redirect.setPAY_REQUEST_ID(keyValueType.getValue());
                        }
                        if ("PAYGATE_ID".equals(keyValueType.getKey())) {
                            redirect.setPAYGATE_ID(keyValueType.getValue());
                        }
                        if ("CHECKSUM".equals(keyValueType.getKey())) {
                            redirect.setCHECKSUM(keyValueType.getValue());
                        }
                    }

                    String[] paramValues = {redirect.getRedirectUrl(), redirect.getPAY_REQUEST_ID(), redirect.getPAYGATE_ID(), redirect.getCHECKSUM()};
                    setRedirectValues(paramValues);
                }

            } catch (Exception ex) {

            }

            if (myMessage != null) {
                // print SOAP Response
                System.out.print("Response SOAP Message:");

                try {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myMessage.writeTo(baos);
                    String message = new String(baos.toByteArray(), "utf-8");

                    /*for (int x=0;x < getRedirectValues().length;x++) {
                            response_1 += getRedirectValues()[x] + "|";
                        }*/
                    ProxyServerHelper.writeResponse(response, def_singlePaymentRequest, WebServiceProxyServer.singlePaymentRequest(transactionStatusDescription, resultDescription));
                } catch (SOAPException | IOException ex) {
                    Logger.getLogger(CN1WebServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CN1WebServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public String getResponse_1() {
        return response_1;
    }

    public void setResponse_1(String response_1) {
        this.response_1 = response_1;
    }

    public String getResponse_2() {
        return response_2;
    }

    public void setResponse_2(String response_2) {
        this.response_2 = response_2;
    }

    public String[] getRedirectValues() {
        return redirectValues;
    }

    public void setRedirectValues(String[] redirectValues) {
        this.redirectValues = redirectValues;
    }

}
