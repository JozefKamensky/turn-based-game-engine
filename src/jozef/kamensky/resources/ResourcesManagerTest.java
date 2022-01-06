package jozef.kamensky.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesManagerTest {

    private final static String ID1 = "mat1";
    private final static String ID2 = "mat2";
    private final static String ID3 = "mat3";

    private ResourcesManager resourcesManager;

    private void prepareResourceManager() {
        resourcesManager = new ResourcesManager();
        resourcesManager.insertResource(new NormalResource(ID1, ID1, ID1, 20, 100));
        resourcesManager.insertResource(new RenewableResource(ID2, ID2, ID2, 30, 100));
        resourcesManager.insertResource(new CalculatedResource(ID3, ID3, ID3, 0, 100, ID1, 2));
    }

    @BeforeEach
    public void beforeEach() {
        prepareResourceManager();
    }

    @Test
    public void shouldPrepareResourceManagerCorrectly() {
        var resourceMap = resourcesManager.getResourceMap();
        assertNotNull(resourceMap.get(ID1));
        assertEquals(20, resourceMap.get(ID1).getAmount());

        assertNotNull(resourceMap.get(ID2));
        assertEquals(30, resourceMap.get(ID2).getAmount());

        assertNotNull(resourceMap.get(ID3));
        assertEquals(0, resourceMap.get(ID3).getAmount());
    }

    @Test
    public void shouldCorrectlyUpdateResourcesOnTurnStart() {
        var resourceMap = resourcesManager.getResourceMap();
        assertEquals(20, resourceMap.get(ID1).getAmount());
        assertEquals(30, resourceMap.get(ID2).getAmount());
        assertEquals(0, resourceMap.get(ID3).getAmount());
        resourcesManager.onTurnStart();
        var updatedResourceMap = resourcesManager.getResourceMap();
        assertEquals(20, updatedResourceMap.get(ID1).getAmount());
        assertEquals(100, updatedResourceMap.get(ID2).getAmount());
        assertEquals(40, updatedResourceMap.get(ID3).getAmount());
    }

}