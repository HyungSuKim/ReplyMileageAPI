package com.triple.replymileageapi.reply;

import com.triple.replymileageapi.useracct.UserAcctRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReplyTest {
    @Autowired
    private ReplyRepo replyRepo;

    @Test
    public void checkReplyRepoNotNull() {
        Assertions.assertThat(replyRepo).isNotNull();
    }

}
