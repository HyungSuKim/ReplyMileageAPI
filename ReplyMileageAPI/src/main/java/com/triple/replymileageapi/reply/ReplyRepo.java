package com.triple.replymileageapi.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReplyRepo extends JpaRepository<Reply, UUID> {
    Reply findByReplyIdAndPlaceIdAndUseFlag(UUID replyId, UUID placeId, String useFlag);
}
