package com.rebook.studygroup.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study_group")
@Entity
public class StudyGroupEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private UserEntity leader;

    private StudyGroupEntity(
            Long id,
            String name,
            int maxMembers,
            UserEntity leader
    ) {
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.leader = leader;
    }

    public static StudyGroupEntity of(
            final String name,
            final int maxMembers,
            final UserEntity leader
    ) {
        return new StudyGroupEntity(null, name, maxMembers, leader);
    }
}
