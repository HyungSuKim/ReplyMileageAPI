package com.triple.replymileageapi.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReviewTest {
    @Autowired
    private ReviewRepo reviewRepo;

    @Test
    public void checkReplyRepoNotNull() {
        Assertions.assertThat(reviewRepo).isNotNull();
    }

}
