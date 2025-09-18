import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CowControllerTests {

    // Test for the cowsay method with default input
    @Test
    public void cowsay_WithDefaultInput_ShouldReturnExpectedOutput() {
        // Arrange
        CowController cowController = new CowController();
        String expectedOutput = "Expected output for default input"; // Replace with the actual expected output

        // Act
        String result = cowController.cowsay(null);

        // Assert
        assertEquals(expectedOutput, result, "The cowsay method should return the expected output for default input.");
    }

    // Test for the cowsay method with custom input
    @Test
    public void cowsay_WithCustomInput_ShouldReturnExpectedOutput() {
        // Arrange
        CowController cowController = new CowController();
        String customInput = "Hello, World!";
        String expectedOutput = "Expected output for custom input"; // Replace with the actual expected output

        // Act
        String result = cowController.cowsay(customInput);

        // Assert
        assertEquals(expectedOutput, result, "The cowsay method should return the expected output for custom input.");
    }

    // Mocking the Cowsay dependency
    @Test
    public void cowsay_WithMockedCowsay_ShouldReturnMockedOutput() {
        // Arrange
        CowController cowController = new CowController();
        Cowsay mockedCowsay = mock(Cowsay.class);
        String customInput = "Mocked Input";
        String mockedOutput = "Mocked Output";

        // Mock the behavior of the Cowsay.run method
        when(mockedCowsay.run(customInput)).thenReturn(mockedOutput);

        // Act
        String result = cowController.cowsay(customInput);

        // Assert
        assertEquals(mockedOutput, result, "The cowsay method should return the mocked output.");
    }
}
