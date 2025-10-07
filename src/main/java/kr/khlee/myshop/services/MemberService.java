package kr.khlee.myshop.services;

import kr.khlee.myshop.models.Member;

public interface MemberServices {

    public void isUniqueId(Member member) throws Exception;
}
