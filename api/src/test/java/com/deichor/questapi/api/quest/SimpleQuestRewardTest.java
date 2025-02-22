package com.deichor.questapi.api.quest;

import com.deichor.questapi.core.model.QuestOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimpleQuestRewardTest {
    @Mock
    private QuestOwner<Long> owner;
    
    @Mock
    private Consumer<QuestOwner<Long>> rewardAction;
    
    private SimpleQuestReward<Long, QuestOwner<Long>> reward;
    
    private static final Long REWARD_VALUE = 100L;
    private static final String REWARD_DESCRIPTION = "Test reward";
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(owner.getId()).thenReturn(1L);
        reward = new SimpleQuestReward<>(REWARD_VALUE, rewardAction, REWARD_DESCRIPTION);
    }
    
    @Test
    void giveReward_ShouldExecuteRewardAction() {
        reward.giveReward(owner);
        verify(rewardAction).accept(owner);
    }
    
    @Test
    void giveReward_WithNullOwner_ShouldNotExecuteRewardAction() {
        reward.giveReward(null);
        verify(rewardAction, never()).accept(any());
    }
    
    @Test
    void getReward_ShouldReturnConfiguredReward() {
        assertEquals(REWARD_VALUE, reward.getReward());
    }
    
    @Test
    void getDescription_ShouldReturnConfiguredDescription() {
        assertEquals(REWARD_DESCRIPTION, reward.getDescription());
    }
}