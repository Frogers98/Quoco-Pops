package messages.registry;

import core.Member;

public class RetrieveMemberDetailsResponse {
    String libraryRef;
    Member memberDetails;

    public RetrieveMemberDetailsResponse () {}

    public RetrieveMemberDetailsResponse (String libraryRef, Member memberDetails) {
        this.libraryRef = libraryRef;
        this.memberDetails = memberDetails;
    }

    public Member getMember() {
        return this.memberDetails;
    }

    public void setMember(Member memberDetails) {
        this.memberDetails = memberDetails;
    }

    public String getLibraryRef() {
        return this.libraryRef;
    }

    public void setLibraryRef(String libraryRef) {
        this.libraryRef = libraryRef;
    }
    
}
