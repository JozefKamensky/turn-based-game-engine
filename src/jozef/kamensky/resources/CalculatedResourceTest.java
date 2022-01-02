package jozef.kamensky.resources;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculatedResourceTest {

    @Test
    public void shouldCalculateCorrectly_happyScenario(){
        var r = ResourceTestsUtils.createCalculatedResource(0, 100, ResourceTestsUtils.ID, 5);
        var r2 = ResourceTestsUtils.createNormalResource(10, 100);
        Map<String, BaseResource> resourceMap = Collections.singletonMap(r2.getId(), r2);
        assertEquals(0, r.getAmount());
        r.onTurnStart(resourceMap);
        assertEquals(50, r.getAmount());
    }

    @Test
    public void shouldCalculateCorrectly_calculateFromZero(){
        var r = ResourceTestsUtils.createCalculatedResource(0, 100, ResourceTestsUtils.ID, 5);
        var r2 = ResourceTestsUtils.createNormalResource(0, 100);
        Map<String, BaseResource> resourceMap = Collections.singletonMap(r2.getId(), r2);
        assertEquals(0, r.getAmount());
        r.onTurnStart(resourceMap);
        assertEquals(0, r.getAmount());
    }

    @Test
    public void shouldCalculateCorrectly_missingResource(){
        var r = ResourceTestsUtils.createCalculatedResource(0, 100, ResourceTestsUtils.ID, 5);
        Map<String, BaseResource> resourceMap = Collections.emptyMap();
        assertEquals(0, r.getAmount());
        r.onTurnStart(resourceMap);
        assertEquals(0, r.getAmount());
    }

    @Test
    public void shouldCalculateCorrectly_clipToMax(){
        var r = ResourceTestsUtils.createCalculatedResource(0, 100, ResourceTestsUtils.ID, 50);
        var r2 = ResourceTestsUtils.createNormalResource(10, 100);
        Map<String, BaseResource> resourceMap = Collections.singletonMap(r2.getId(), r2);
        assertEquals(0, r.getAmount());
        r.onTurnStart(resourceMap);
        assertEquals(100, r.getAmount());
    }

}