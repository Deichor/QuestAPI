package com.deichor.questapi.api.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.core.storage.DatabaseConfig;
import com.deichor.questapi.core.storage.StorageManager;
import com.deichor.questapi.core.storage.StorageTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class StorageManagerIntegrationTest {
    @Mock
    private QuestOwner<Long> owner1;
    
    @Mock
    private QuestRequirement requirement1;
    
    @Mock
    private QuestRequirement requirement2;
    
    @Mock
    private QuestReward<Long, QuestOwner<Long>> reward;
    
    private static final String TEST_DB_PATH = "test-quest.db";
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(owner1.getId()).thenReturn(1L);
        when(owner1.getOwnerType()).thenReturn("PLAYER");
    }
    
    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(new File(TEST_DB_PATH).toPath());
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }
    
    @Nested
    class SQLiteStorageTests {
        private StorageManager storageManager;
        
        @BeforeEach
        void setUp() {
            DatabaseConfig config = new DatabaseConfig(TEST_DB_PATH);
            storageManager = new StorageManager(StorageTypes.SQLITE, config);
        }
        
        @AfterEach
        void tearDown() {
            storageManager.close();
        }
        
        @Test
        void saveAndRetrieveQuest() {
            // Create a test quest
            BaseQuest<Long, Long> quest = createTestQuest(owner1);
            QuestManager<Long> questManager = new QuestManager<>(quest) {};
            
            // Save the quest
            storageManager.saveQuest(1, questManager);
            
            // Retrieve and verify
            Optional<QuestManager<?>> retrieved = storageManager.getQuest(1);
            assertThat(retrieved).isPresent();
            assertThat(retrieved.get().getQuest().getOwner().getId())
                .isEqualTo(owner1.getId());
        }
    }
    
    private BaseQuest<Long, Long> createTestQuest(QuestOwner<Long> owner) {
        BaseQuest<Long, Long> quest = new BaseQuest<>() {};
        quest.setOwner(owner);
        quest.getRequirements().addAll(Arrays.asList(requirement1, requirement2));
        quest.getRewards().add(reward);
        return quest;
    }
}