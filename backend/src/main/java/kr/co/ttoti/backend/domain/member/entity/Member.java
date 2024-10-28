package kr.co.ttoti.backend.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.ttoti.backend.global.base.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_social_category")
    private OAuth2Provider memberSocialCategory;

    @NotNull
    @Column(name = "member_email")
    private String memberEmail;

    @NotNull
    @Column(name = "member_name")
    private String memberName;

    @NotNull
    @Column(name = "member_profile_image_url")
    private String memberProfileImageUrl;

    @NotNull
    @Column(name = "refresh_token")
    private String refreshToken;

    @NotNull
    @Column(name = "member_is_deleted")
    private Boolean memberIsDeleted = false;

    @Column(name = "member_deleted_at")
    private LocalDateTime memberDeletedAt;
}
