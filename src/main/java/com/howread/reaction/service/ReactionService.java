package com.howread.reaction.service;

import com.howread.common.exception.ExceptionCode;
import com.howread.common.exception.NotFoundException;
import com.howread.reaction.domain.ReactionEntity;
import com.howread.reaction.repository.ReactionRepository;
import com.howread.reaction.service.command.ReactionCommand;
import com.howread.reaction.service.dto.ReactionDto;
import com.howread.user.domain.UserEntity;
import com.howread.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReactionDto on(ReactionCommand reactionCommand) {
        UserEntity userEntity = userRepository.findById(reactionCommand.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER_ID));

        Optional<ReactionEntity> optionalReaction = reactionRepository.findReaction(
                reactionCommand.getUserId(),
                reactionCommand.getReactionType(),
                reactionCommand.getTargetType(),
                reactionCommand.getTargetId());

        ReactionEntity reactionEntity;

        if (optionalReaction.isPresent()) { // 리액션이 있을 때
            reactionEntity = optionalReaction.get();
            if (!reactionEntity.getIsOn()) { // 리액션이 꺼져있을 때
                reactionEntity.turnOn();
            }
        } else {
            reactionEntity = reactionRepository.save( // 리액션 없을 때
                    new ReactionEntity(
                            null,
                            userEntity,
                            reactionCommand.getReactionType(),
                            reactionCommand.getTargetType(),
                            reactionCommand.getTargetId(),
                            true
                    )
            );
        }
        return ReactionDto.from(reactionEntity);
    }

    @Transactional
    public void off(ReactionCommand reactionCommand) {
        ReactionEntity reactionEntity = reactionRepository.findReaction(
                        reactionCommand.getUserId(),
                        reactionCommand.getReactionType(),
                        reactionCommand.getTargetType(),
                        reactionCommand.getTargetId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_REACTION_ID));

        if (reactionEntity.getIsOn()) { // 리액션이 켜져 있을 때
            reactionEntity.turnOff();
            reactionRepository.save(reactionEntity);
        }
    }
}
