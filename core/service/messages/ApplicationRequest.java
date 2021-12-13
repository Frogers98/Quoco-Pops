package service.messages;

import service.core.ClientInfo;

/**
 * Implementation of the Application request .
 * 
 * @author Hassan
 */

public class ApplicationRequest implements MySerializable{

      private ClientInfo clientInfo;


      public ApplicationRequest(){}
      public ApplicationRequest(ClientInfo clientInfo) {

      this.clientInfo = clientInfo;
      }

      // add get and set methods for each field
      public ClientInfo getClientInfo() {
            return clientInfo;
      }

      public void setClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
      }
      
}