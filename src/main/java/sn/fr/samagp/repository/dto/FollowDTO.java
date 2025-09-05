package sn.fr.samagp.repository.dto;

import lombok.Data;

public record FollowDTO(
        String followerId,
        String followedId,
        boolean isFollowing,
        int followersCount
) {
}