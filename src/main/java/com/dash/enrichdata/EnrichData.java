package com.dash.enrichdata;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnrichData {

    public EnrichData() {
    }

    public static void main(String[] ergs) {
    }

    public static String decodeURL(String url) {
        EnrichData data = new EnrichData();
        String decodedURL = url;
        try {
            URLDecoder urlDecoder = new URLDecoder();
            decodedURL = urlDecoder.decode(url,"UTF-8");
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        return decodedURL;
    }

    public static String extractProductName(String url){
        EnrichData data = new EnrichData();
        String patternToMatch = "(/product/(.*))";
        Pattern pattern = Pattern.compile(patternToMatch);
        String decodedURL = data.decodeURL(url);
        Matcher matcher = pattern.matcher(decodedURL);
        if (matcher.find( )) {
            return matcher.group(2).replace("/add_to_cart","");
        }
        return "N/A";
    }

    public static String getCountryName(String ip) {
        EnrichData data = new EnrichData();
        String countryName = data.getCityOrCountry(ip, "Country");
        return countryName;
    }

    public static String getCityName(String ip) {
        EnrichData data = new EnrichData();
        String cityName = data.getCityOrCountry(ip, "City");
        return cityName;
    }

    private String getCityOrCountry(String ip, String cityOrCountry) {

//        Resource: https://github.com/maxmind/GeoIP2-java

        String cityOrCountryName = "N/A";

        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            String resourceName = "GeoLite2-City.mmdb";
            System.out.println("Loading resource by absolute path");
            InputStream geoLiteDatabase = EnrichData.class.getResourceAsStream("src/main/resources/" + resourceName);
            if (geoLiteDatabase == null) {
                System.out.println("Loading resource by name");
                geoLiteDatabase = EnrichData.class.getClassLoader().getResourceAsStream(resourceName);
            }
            DatabaseReader reader = new DatabaseReader.Builder(geoLiteDatabase).build();

            if (cityOrCountry.equals("Country")) {
                CountryResponse country = reader.country(ipAddress);
                cityOrCountryName = country.getCountry().getName();
            } else {
                CityResponse city = reader.city(ipAddress);
                cityOrCountryName = city.getCity().getName();
            }
        }  catch(Exception e) {
            System.out.println(e.toString());
            cityOrCountryName = e.toString();
        }

        return cityOrCountryName;
    }

}
