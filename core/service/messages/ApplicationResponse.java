package service.messages;

import java.util.*;

import service.core.ClientInfo;
import service.core.Quotation;


/**
 * Implementation of the Application response Message.
 * 
 * @author Hassan
 */

public class ApplicationResponse implements MySerializable{
      private ArrayList<Quotation> quotations;
      private ClientInfo clientInfo;

      
      public ApplicationResponse(ClientInfo clientInfo, ArrayList<Quotation> quotations) {
      this.clientInfo = clientInfo;
      this.quotations = quotations;
      }
      public ApplicationResponse() {
      }

      // add get and set methods for each field
      public ClientInfo getclientInfo() {
            return clientInfo;
      }
      public void setclientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
      }
      public ArrayList<Quotation> getQuotations() {
            return quotations;
      }
      
      public void setQuotation(ArrayList<Quotation> quotations) {
            this.quotations = quotations;
      }
}