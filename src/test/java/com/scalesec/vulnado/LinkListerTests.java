package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkListerTests {

    // Test for getLinks method
    @Test
    public void getLinks_ValidUrl_ShouldReturnLinks() throws IOException {
        // Arrange
        String testUrl = "http://example.com";
        String mockHtml = "<html><body><a href='http://example.com/link1'>Link1</a><a href='http://example.com/link2'>Link2</a></body></html>";
        JsoupMock jsoupMock = new JsoupMock(mockHtml);

        // Act
        List<String> links = LinkLister.getLinks(testUrl);

        // Assert
        assertNotNull("Links should not be null", links);
        assertEquals("Should return 2 links", 2, links.size());
        assertTrue("Should contain link1", links.contains("http://example.com/link1"));
        assertTrue("Should contain link2", links.contains("http://example.com/link2"));
    }

    // Test for getLinksV2 method with valid URL
    @Test
    public void getLinksV2_ValidUrl_ShouldReturnLinks() throws Exception {
        // Arrange
        String testUrl = "http://example.com";
        String mockHtml = "<html><body><a href='http://example.com/link1'>Link1</a><a href='http://example.com/link2'>Link2</a></body></html>";
        JsoupMock jsoupMock = new JsoupMock(mockHtml);

        // Act
        List<String> links = LinkLister.getLinksV2(testUrl);

        // Assert
        assertNotNull("Links should not be null", links);
        assertEquals("Should return 2 links", 2, links.size());
        assertTrue("Should contain link1", links.contains("http://example.com/link1"));
        assertTrue("Should contain link2", links.contains("http://example.com/link2"));
    }

    // Test for getLinksV2 method with private IP
    @Test(expected = BadRequest.class)
    public void getLinksV2_PrivateIp_ShouldThrowBadRequest() throws Exception {
        // Arrange
        String testUrl = "http://192.168.1.1";

        // Act
        LinkLister.getLinksV2(testUrl);
    }

    // Test for getLinksV2 method with malformed URL
    @Test(expected = BadRequest.class)
    public void getLinksV2_MalformedUrl_ShouldThrowBadRequest() throws Exception {
        // Arrange
        String testUrl = "malformed-url";

        // Act
        LinkLister.getLinksV2(testUrl);
    }

    // Mock class for Jsoup
    private static class JsoupMock {
        private final String html;

        public JsoupMock(String html) {
            this.html = html;
        }

        public Document connect(String url) {
            return Jsoup.parse(html);
        }
    }
}
