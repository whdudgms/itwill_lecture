package xyz.itwill05.lombok;

import java.util.List;

public interface MemberDAO {
	int insertMember(Member member);
	int updateMember(Member member);
	int deleteMember(String id);
	Member seleteMember(String id);
	List<Member> seleteMemberList();
}
