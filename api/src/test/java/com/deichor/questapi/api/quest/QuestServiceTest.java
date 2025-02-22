package com.deichor.questapi.api.quest;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.*;
import com.deichor.questapi.core.storage.StorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class QuestServiceTest {
    private DefaultQuestService questService;
    
    @Mock
    private StorageManager storageManager;
    
    @Mock
    private QuestOwner<Long> owner;
    
    @Mock
    private QuestRequirement requirement1;
    
    @Mock
    private QuestRequirement requirement2;
    
    @Mock
    private QuestReward<Long, QuestOwner<Long>> reward;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questService = new DefaultQuestService(storageManager);
        
        // Setup common mocks
        when(owner.getId()).thenReturn(1L);
        when(requirement1.getProgress()).thenReturn(0);
        when(requirement2.getProgress()).thenReturn(0);
        
        // Setup StorageManager mocks
        when(storageManager.getQuest(anyInt())).thenReturn(Optional.empty());
        doNothing().when(storageManager).saveQuest(anyInt(), any());
    }
    
    @Test
    void createQuest_ShouldReturnNewQuestManager() {
        List<QuestRequirement> requirements = Arrays.asList(requirement1, requirement2);
        List<QuestReward<Long, QuestOwner<Long>>> rewards = List.of(reward);
        
        QuestManager<Long> result = questService.createQuest(owner, requirements, rewards);
        
        assertNotNull(result);
        assertEquals(requirements, result.getQuest().getRequirements());
        assertEquals(rewards, result.getQuest().getRewards());
        verify(storageManager, times(1)).saveQuest(anyInt(), any());
    }
    
    @Test
    void getQuest_WithExistingId_ShouldReturnQuest() {
        // Setup a quest that will be returned
        QuestManager<?> mockQuest = mock(QuestManager.class);
        when(storageManager.getQuest(1)).thenReturn(Optional.of(mockQuest));
        
        Optional<QuestManager<?>> result = questService.getQuest(1);
        
        assertTrue(result.isPresent());
        assertEquals(mockQuest, result.get());
    }
    
    @Test
    void getQuest_WithNonExistingId_ShouldReturnEmpty() {
        Optional<QuestManager<?>> result = questService.getQuest(999);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void getQuestsByOwner_ShouldReturnOwnerQuests() {
        // Setup quests that will be returned
        List<QuestManager<?>> ownerQuests = Arrays.asList(
            mock(QuestManager.class),
            mock(QuestManager.class)
        );
        when(storageManager.getQuestsByOwner(owner)).thenReturn(ownerQuests);
        
        List<QuestManager<?>> result = questService.getQuestsByOwner(owner);
        
        assertEquals(2, result.size());
        verify(storageManager).getQuestsByOwner(owner);
    }
    
    @Test
    void completeQuest_WithCompletedRequirements_ShouldReturnTrue() {
        // Setup completed requirements
        when(requirement1.isComplete()).thenReturn(true);
        when(requirement2.isComplete()).thenReturn(true);
        
        // Create and setup the quest
        QuestManager<Long> quest = questService.createQuest(owner, 
            Arrays.asList(requirement1, requirement2),
            List.of(reward));
            
        // Clear the initial save invocation
        reset(storageManager);
        
        // Setup storage manager to return our quest
        when(storageManager.getQuest(anyInt())).thenReturn(Optional.of(quest));
        
        boolean result = questService.completeQuest(1);
        
        assertTrue(result);
        verify(reward, times(1)).giveReward(owner);
    }
    
    @Test
    void completeQuest_WithIncompleteRequirements_ShouldReturnFalse() {
        // Setup incomplete requirements
        when(requirement1.isComplete()).thenReturn(true);
        when(requirement2.isComplete()).thenReturn(false);
        
        // Create and setup the quest
        QuestManager<Long> quest = questService.createQuest(owner, 
            Arrays.asList(requirement1, requirement2),
            List.of(reward));
            
        // Clear the initial save invocation
        reset(storageManager);
        
        // Setup storage manager to return our quest
        when(storageManager.getQuest(anyInt())).thenReturn(Optional.of(quest));
        
        boolean result = questService.completeQuest(1);
        
        assertFalse(result);
        verify(reward, never()).giveReward(any());
    }
    
    @Test
    void updateQuestProgress_ShouldUpdateRequirementProgress() {
        // Setup a quest that will be returned
        QuestManager<Long> quest = questService.createQuest(owner,
            Arrays.asList(requirement1, requirement2),
            List.of(reward));
            
        // Clear the initial save invocation
        reset(storageManager);
        
        // Setup storage manager to return our quest
        when(storageManager.getQuest(1)).thenReturn(Optional.of(quest));
        
        questService.updateQuestProgress(1, 0, 5);
        
        verify(requirement1, times(1)).addProgress(5);
        verify(storageManager, times(1)).saveQuest(eq(1), any());
    }
}