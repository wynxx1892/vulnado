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
import java.net.MalformedURLException;
import java.net.*;
public class LinkLister {

    public static List<String> getLinks(String url) throws IOException {

        List<String> result = new ArrayList<>();
public class LinkLister {
        Document doc = Jsoup.connect(url).get();
  public static List<String> getLinks(String url) throws IOException {
        Elements links = doc.select("a");
    List<String> result = new ArrayList<String>();
        for (Element link : links) {
    Document doc = Jsoup.connect(url).get();
            result.add(link.absUrl("href"));
    Elements links = doc.select("a");
        }
    for (Element link : links) {
        return result;
      result.add(link.absUrl("href"));
    }
    }
    public static List<String> getLinksV2(String url) throws BadRequest {
    return result;
        try {
  }
            URL aUrl = new URL(url);

            String host = aUrl.getHost();
  public static List<String> getLinksV2(String url) throws BadRequest {
            System.out.println(host);
    try {
            if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")) {
      URL aUrl= new URL(url);
                throw new BadRequest("Use of Private IP");
      String host = aUrl.getHost();
            } else {
      System.out.println(host);
                return getLinks(url);
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")){
            }
        throw new BadRequest("Use of Private IP");
        } catch (MalformedURLException e) {
      } else {
            throw new BadRequest("Invalid URL: " + e.getMessage());
        return getLinks(url);
        } catch (IOException e) {
      }
            throw new BadRequest("Error fetching links: " + e.getMessage());
    } catch(Exception e) {
        }
      throw new BadRequest(e.getMessage());
    }
    }
}
  }
}
