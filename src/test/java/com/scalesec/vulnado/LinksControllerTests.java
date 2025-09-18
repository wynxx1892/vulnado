package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinksControllerTests {

    // Helper method to create a mock LinkLister
    private LinkLister createMockLinkLister() {
        return mock(LinkLister.class);
    }

    // Test for the links() method
    @Test
    public void links_ValidUrl_ShouldReturnLinks() throws IOException {
        // Arrange
        String testUrl = "http://example.com";
        List<String> expectedLinks = Arrays.asList("http://link1.com", "http://link2.com");
        LinkLister mockLinkLister = createMockLinkLister();
        when(mockLinkLister.getLinks(testUrl)).thenReturn(expectedLinks);

        LinksController controller = new LinksController(mockLinkLister);

        // Act
        List<String> actualLinks = controller.links(testUrl);

        // Assert
        assertEquals("The returned links should match the expected links.", expectedLinks, actualLinks);
        verify(mockLinkLister, times(1)).getLinks(testUrl);
    }

    @Test
    public void links_InvalidUrl_ShouldThrowIOException() throws IOException {
        // Arrange
        String invalidUrl = "invalid-url";
        LinkLister mockLinkLister = createMockLinkLister();
        when(mockLinkLister.getLinks(invalidUrl)).thenThrow(new IOException("Invalid URL"));

        LinksController controller = new LinksController(mockLinkLister);

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> controller.links(invalidUrl));
        assertEquals("Invalid URL", exception.getMessage());
        verify(mockLinkLister, times(1)).getLinks(invalidUrl);
    }

    // Test for the linksV2() method
    @Test
    public void linksV2_ValidUrl_ShouldReturnLinks() {
        // Arrange
        String testUrl = "http://example.com";
        List<String> expectedLinks = Arrays.asList("http://link1.com", "http://link2.com");
        LinkLister mockLinkLister = createMockLinkLister();
        when(mockLinkLister.getLinksV2(testUrl)).thenReturn(expectedLinks);

        LinksController controller = new LinksController(mockLinkLister);

        // Act
        List<String> actualLinks = controller.linksV2(testUrl);

        // Assert
        assertEquals("The returned links should match the expected links.", expectedLinks, actualLinks);
        verify(mockLinkLister, times(1)).getLinksV2(testUrl);
    }

    @Test
    public void linksV2_InvalidUrl_ShouldThrowBadRequest() {
        // Arrange
        String invalidUrl = "invalid-url";
        LinkLister mockLinkLister = createMockLinkLister();
        when(mockLinkLister.getLinksV2(invalidUrl)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL"));

        LinksController controller = new LinksController(mockLinkLister);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> controller.linksV2(invalidUrl));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid URL", exception.getReason());
        verify(mockLinkLister, times(1)).getLinksV2(invalidUrl);
    }
}
