package com.deichor.questapi.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.deichor.questapi.api.quest.QuestService;
import com.deichor.questapi.core.storage.StorageManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestAPITest {
    @Mock
    private QuestService questService;
    
    @Mock
    private StorageManager storageManager;
    
    private TestQuestAPI testApi;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testApi = new TestQuestAPI();
        QuestAPI.REGISTERED_APIS.clear(); // Clear existing registrations before each test
    }
    
    @Test
    void registerAPI_ShouldAddAPIToRegisteredList() {
        QuestAPI.registerAPI(testApi);
        assertTrue(QuestAPI.REGISTERED_APIS.contains(testApi));
    }
    
    @Test
    void unregisterAPI_ShouldRemoveAPIFromRegisteredList() {
        QuestAPI.registerAPI(testApi);
        QuestAPI.unregisterAPI(testApi);
        assertFalse(QuestAPI.REGISTERED_APIS.contains(testApi));
    }
    
    @Test
    void getAPI_WithValidIdentifier_ShouldReturnCorrectAPI() {
        QuestAPI.registerAPI(testApi);
        var result = QuestAPI.getAPI("test");
        assertTrue(result.isPresent());
        assertEquals(testApi, result.get());
    }
    
    @Test
    void getAPI_WithInvalidIdentifier_ShouldReturnEmpty() {
        QuestAPI.registerAPI(testApi);
        var result = QuestAPI.getAPI("invalid");
        assertTrue(result.isEmpty());
    }
    
    @Test
    void getByIdentifier_WithValidIdentifier_ShouldReturnAPI() {
        QuestAPI.registerAPI(testApi);
        var result = QuestAPI.getByIdentifier("test", TestQuestAPI.class);
        assertEquals(testApi, result);
    }
    
    @Test
    void getByIdentifier_WithInvalidIdentifier_ShouldThrowException() {
        QuestAPI.registerAPI(testApi);
        assertThrows(IllegalStateException.class, () -> 
            QuestAPI.getByIdentifier("invalid", TestQuestAPI.class)
        );
    }
    
    // Test implementation of QuestAPI for testing purposes
    private class TestQuestAPI implements QuestAPI {
        @Override
        public StorageManager getStorageManager() {
            return storageManager;
        }
        
        @Override
        public QuestService getQuestService() {
            return questService;
        }
        
        @Override
        public String getIdentifier() {
            return "test";
        }
    }
}