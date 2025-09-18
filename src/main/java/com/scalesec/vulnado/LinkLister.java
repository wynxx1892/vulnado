package com.scalesec.vulnado;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.net.URL;
import java.io.IOException;
import java.util.logging.Logger;
import java.net.*;
public class LinkLister {

    private static final Logger LOGGER = Logger.getLogger(LinkLister.class.getName());

    public static List<String> getLinks(String url) throws IOException {
public class LinkLister {
        List<String> result = new ArrayList<>();
  public static List<String> getLinks(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
    List<String> result = new ArrayList<String>();
        Elements links = doc.select("a");
    Document doc = Jsoup.connect(url).get();
        for (Element link : links) {
    Elements links = doc.select("a");
            result.add(link.absUrl("href"));
    for (Element link : links) {
        }
      result.add(link.absUrl("href"));
        return result;
    }
    }
    return result;
    public static List<String> getLinksV2(String url) throws BadRequest {
  }
        try {

            URL aUrl = new URL(url);
  public static List<String> getLinksV2(String url) throws BadRequest {
            String host = aUrl.getHost();
    try {
            LOGGER.info("Host: " + host);
      URL aUrl= new URL(url);
            if (isPrivateIP(host)) {
      String host = aUrl.getHost();
                throw new BadRequest("Use of Private IP");
      System.out.println(host);
            } else {
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")){
                return getLinks(url);
        throw new BadRequest("Use of Private IP");
            }
      } else {
        } catch (Exception e) {
        return getLinks(url);
            throw new BadRequest(e.getMessage());
      }
        }
    } catch(Exception e) {
    }
      throw new BadRequest(e.getMessage());
    private static boolean isPrivateIP(String host) {
    }
        return host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.");
  }
    }
}
}
