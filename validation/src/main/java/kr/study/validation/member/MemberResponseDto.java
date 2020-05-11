package kr.study.validation.member;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.phoneNumber = toStringPhone(member.getPhone1(), member.getPhone2(), member.getPhone3());
        this.email = member.getEmail();
    }

    private String toStringPhone(String phone1, String phone2, String phone3){
        return phone1+"-"+phone2+"-"+phone3;
    }
}
