package service.messages;

import service.core.ClientInfo;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 * @author Hassan
 */

public class QuotationRequest implements MySerializable{
      private int id;
      private ClientInfo clientInfo;
      public QuotationRequest(int id, ClientInfo clientInfo) {
      this.id = id;
      this.clientInfo = clientInfo;
      }
      // add get and set methods for each field
      public int getId() {
            return id;
      }
      public void setId(int id) {
            this.id = id;
      }
      public ClientInfo getClientInfo() {
            return clientInfo;
      }
      public void setClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
      }
}