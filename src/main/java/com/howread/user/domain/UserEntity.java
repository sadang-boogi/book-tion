package com.howread.user.domain;

import com.howread.common.domain.BaseEntity;
import com.howread.review.domain.ReviewEntity;
import com.howread.user.util.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_social_type_social_id", columnNames = {"social_type", "social_id"})}
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(255)")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", columnDefinition = "varchar(255)")
    private SocialType socialType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @Column(name = "avatar_url", nullable = true)
    private String avatarUrl;

    @Builder
    public UserEntity(Long id, String nickname, String email, Role role, SocialType socialType, String socialId, String avatarUrl) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.avatarUrl = avatarUrl;
    }

    public UserEntity update(String nickname, String avatarUrl) {
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        return this;
    }

    // 연관관계 편의 메서드
    public void addReview(ReviewEntity review) {
        reviews.add(review);
        review.setUser(this);
    }

    public void removeReview(ReviewEntity review) {
        reviews.remove(review);
        review.softDelete();
    }
}
