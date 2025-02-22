package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.service.MemberServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MemberRepositoryV1Test {

    public static final String MEMBER_A= "memberA";
    public static final String MEMBER_B= "memberB";
    public static final String MEMBER_EX= "ex";

    private MemberRepositoryV1 repository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void beforeEach() throws Exception {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);

        repository = new MemberRepositoryV1(dataSource);

    }

    @Test
    void crud() throws SQLException, InterruptedException {
        //save
        Member member = new Member("memberV0", 10000);
//        repository.save(member); // memberId가 pk로 설정되어있기에 한번만 호출됨.

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update
        // money : 10000 -> 2000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        Assertions.assertThatThrownBy(
                ()->repository.findById(member.getMemberId())
        ).isInstanceOf(NoSuchElementException.class);
    }

}
