package jozef.kamensky.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesManagerTest {

    private final static String ID1 = "mat1";
    private final static String ID2 = "mat2";
    private final static String ID3 = "mat3";

    private ResourcesManager resourcesManager;

    private void prepareRegularResource() {
        resourcesManager.insertResource(ResourceTestsUtils.createRegularResource(ID1, 20, 100));
    }

    private void prepareRenewableResource() {
        resourcesManager.insertResource(ResourceTestsUtils.createRenewableResource(ID2,30,100));
    }

    private void prepareCalculatedResource() {
        resourcesManager.insertResource(ResourceTestsUtils.createCalculatedResource(ID3,0, 100, ID1, 2));
    }

    @BeforeEach
    public void beforeEach() {
        resourcesManager = new ResourcesManager();
    }

    @Test
    public void shouldCorrectlyUpdateRegularResource() {
        prepareRegularResource();
        var resourceMap = resourcesManager.getResourceMap();
        assertEquals(20, resourceMap.get(ID1).getAmount());
        resourcesManager.onTurnStart();
        var updatedResourceMap = resourcesManager.getResourceMap();
        assertEquals(20, updatedResourceMap.get(ID1).getAmount());
    }

    @Test
    public void shouldCorrectlyUpdateRenewableResource() {
        prepareRenewableResource();
        var resourceMap = resourcesManager.getResourceMap();
        assertEquals(30, resourceMap.get(ID2).getAmount());
        resourcesManager.onTurnStart();
        var updatedResourceMap = resourcesManager.getResourceMap();
        assertEquals(100, updatedResourceMap.get(ID2).getAmount());
    }

    @Test
    public void shouldCorrectlyUpdateCalculatedResource() {
        prepareRegularResource();
        prepareCalculatedResource();
        var resourceMap = resourcesManager.getResourceMap();
        assertEquals(20, resourceMap.get(ID1).getAmount());
        assertEquals(0, resourceMap.get(ID3).getAmount());
        resourcesManager.onTurnStart();
        var updatedResourceMap = resourcesManager.getResourceMap();
        assertEquals(40, updatedResourceMap.get(ID3).getAmount());
    }

}