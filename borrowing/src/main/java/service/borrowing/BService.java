package service.borrowing;

import service.core.*;


/**
 * Implementation of the Borrow book library service.
 * 
 * @author Hassan
 *
 */

public class BService extends AbstractBorrowService implements LibraryService{

      public static final String PREFIX = "BF";
	public static final String LIBRARY = "Tallaght Library";

      @Override
      public Recipt generateRecipt(ClientInfo info){
      
            double fine = generate(60, 60);
		
		// Write the info to database
		// BookID.qunitiy -=quantity;
		// BookID.Users << User.ID;
		// UserID.Fine << fine;
		 
		// maybe extended time for perimum user?
		int longerDate = (info.getUserType() == ClientInfo.Permium) ? 2:0;
		 
		longerDate += getDateExtension(Clientinfo);
		
		// gen the reciept
		return new Reciept(LIBRARY, generateReference(PREFIX), date, fine, quantity);
		
	}

	private int getDateExtension(ClientInfo info){
            	
		if (info.getPoints >= 120) return -1;       // dont allow if fine exceeds
		if (info.getPoints = 0 ) return 12;         // if no fines give 2 weeks;
		return 7;                                 // default days for lettings?
      }
      
}
