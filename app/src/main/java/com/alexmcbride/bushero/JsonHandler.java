package com.alexmcbride.bushero;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class JsonHandler {
    public String get(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        try (InputStream stream = connection.getInputStream(); Scanner scanner = new Scanner(stream)) {
            scanner.useDelimiter("\\Z");
            return scanner.next();
        }
    }
}
