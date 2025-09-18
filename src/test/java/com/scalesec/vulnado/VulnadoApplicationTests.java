package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    // Test to ensure the application context loads successfully
    @Test
    public void contextLoads() {
        // This test ensures that the Spring application context is loaded without issues.
    }

    // Test to verify that the main method calls the necessary setup and run methods
    @Test
    public void main_ShouldInvokeSetupAndRun() {
        // Arrange
        String[] args = {};
        Class<VulnadoApplication> applicationClass = VulnadoApplication.class;

        // Mock the Postgres class to verify the setup method is called
        Postgres postgresMock = Mockito.mock(Postgres.class);
        Mockito.doNothing().when(postgresMock).setup();

        // Act
        VulnadoApplication.main(args);

        // Assert
        Mockito.verify(postgresMock, Mockito.times(1)).setup();
        // Verify that SpringApplication.run is called with the correct arguments
        Mockito.verifyStatic(SpringApplication.class, Mockito.times(1));
        SpringApplication.run(applicationClass, args);
    }
}
