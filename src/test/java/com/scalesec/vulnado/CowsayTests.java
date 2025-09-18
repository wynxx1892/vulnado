import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CowsayTests {

    // Test for the run method with valid input
    @Test
    public void run_ValidInput_ShouldReturnExpectedOutput() throws Exception {
        // Arrange
        String input = "Hello, World!";
        String expectedOutput = "Mocked Cowsay Output\n";
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);
        BufferedReader mockReader = new BufferedReader(new StringReader(expectedOutput));

        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        when(mockProcess.getInputStream()).thenReturn(mockReader);

        // Act
        String actualOutput = Cowsay.run(input);

        // Assert
        assertEquals("The output should match the mocked Cowsay output.", expectedOutput, actualOutput);
    }

    // Test for the run method with null input
    @Test
    public void run_NullInput_ShouldHandleGracefully() throws Exception {
        // Arrange
        String input = null;
        String expectedOutput = "Mocked Cowsay Output\n";
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);
        BufferedReader mockReader = new BufferedReader(new StringReader(expectedOutput));

        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        when(mockProcess.getInputStream()).thenReturn(mockReader);

        // Act
        String actualOutput = Cowsay.run(input);

        // Assert
        assertEquals("The output should match the mocked Cowsay output.", expectedOutput, actualOutput);
    }

    // Test for the run method with empty input
    @Test
    public void run_EmptyInput_ShouldHandleGracefully() throws Exception {
        // Arrange
        String input = "";
        String expectedOutput = "Mocked Cowsay Output\n";
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);
        BufferedReader mockReader = new BufferedReader(new StringReader(expectedOutput));

        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        when(mockProcess.getInputStream()).thenReturn(mockReader);

        // Act
        String actualOutput = Cowsay.run(input);

        // Assert
        assertEquals("The output should match the mocked Cowsay output.", expectedOutput, actualOutput);
    }

    // Test for the run method when an exception occurs
    @Test
    public void run_ExceptionOccurs_ShouldReturnEmptyString() throws Exception {
        // Arrange
        String input = "Hello, World!";
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);

        when(mockProcessBuilder.start()).thenThrow(new RuntimeException("Mocked Exception"));

        // Act
        String actualOutput = Cowsay.run(input);

        // Assert
        assertEquals("The output should be an empty string when an exception occurs.", "", actualOutput);
    }
}
