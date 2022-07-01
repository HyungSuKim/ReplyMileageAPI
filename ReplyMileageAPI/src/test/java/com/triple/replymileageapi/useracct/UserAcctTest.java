package com.triple.replymileageapi.useracct;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UserAcctTest {

    @Autowired
    private UserAcctRepo userAcctRepo;

    @Test
    public void UserAcctRepoNotNull() {
        assertThat(userAcctRepo).isNotNull();
    }

    @Test
    public void 전달받은UUID로사용자계좌조회() {
        //given
        final UserAcct userAcct = new UserAcct.UserAcctBuilder().userId(UUID.randomUUID()).build();
        //when
        List<UserAcct> result = userAcctRepo.findByUserId(userAcct.getUserId());
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void 전달받은UUID로조회된계좌가없으면신규생성() {
        //given
        final UUID input = UUID.randomUUID();
        final UserAcct userAcct = new UserAcct.UserAcctBuilder().userId(input).build();
        //when
        List<UserAcct> result = userAcctRepo.findByUserId(userAcct.getUserId());

        UserAcct saveResult = null;

        if(result.isEmpty()) {
            saveResult = userAcctRepo.save(userAcct);
        }

        System.out.println(userAcctRepo.findByUserId(saveResult.getUserId()).get(0));
        //then
        assertThat(saveResult.getUserId()).isEqualTo(input);
    }
}
