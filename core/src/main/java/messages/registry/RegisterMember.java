package messages.registry;

import core.Member;
import messages.MySerializable;

public class RegisterMember implements MySerializable {
    private Member member;

    public RegisterMember() {}

    public RegisterMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
}
