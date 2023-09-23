package com.munsun.notifications.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.munsun.notifications.model.OthersParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OthersParametersIntegrationTests {
    @Autowired
    private OthersParametersRepository repository;

    @Test
    public void findByName_NotFound() {
        var actual = repository.findByName("testName");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void findByName() {
        var othersParameters = new OthersParameters(0, "К-100");
        var actual = repository.findByName(othersParameters.getName());
        assertTrue(actual.isPresent());
        assertNotEquals(othersParameters.getId(), actual.get().getId());
        assertEquals(othersParameters.getName(), actual.get().getName());
    }
}
