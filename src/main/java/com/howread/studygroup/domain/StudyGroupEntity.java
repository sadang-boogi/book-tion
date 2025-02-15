package com.howread.studygroup.domain;

import com.howread.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "study_group")
@Entity
public class StudyGroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @Builder.Default
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<StudyGroupMemberEntity> members = new ArrayList<>();
}
