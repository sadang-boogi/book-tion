package com.rebook.study.service;

import com.rebook.study.repository.StudyGroupRepository;
import com.rebook.study.service.command.StudyGroupCommand;
import com.rebook.study.service.dto.StudyGroupDto;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyGroupDto create(final StudyGroupCommand studyGroupCommand) {
        return null;
    }
}
