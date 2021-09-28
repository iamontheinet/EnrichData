package com.dash.enrichdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrichDataTest {

    @org.junit.jupiter.api.Test
    void getCountryName() {
        String southKorea = EnrichData.getCountryName("222.113.87.131");
        assertEquals("Republic of Korea", southKorea);

        String usa = EnrichData.getCountryName("98.42.156.174");
        assertEquals("United States", usa);
    }

    @org.junit.jupiter.api.Test
    void getCityName() {
        String data = EnrichData.getCityName("183.225.48.201");
        assertEquals("Dali", data);
    }

    @org.junit.jupiter.api.Test
    void decodeURL() {
        String data = EnrichData.decodeURL("/department/fan%20shop/category/camping%20&%20hiking/product/Diamondback%20Women's%20Serene%20Classic%20Comfort%20Bi");
        assertEquals("/department/fan shop/category/camping & hiking/product/Diamondback Women's Serene Classic Comfort Bi", data);
    }

    @org.junit.jupiter.api.Test
    void extractProductName() {
        String data = EnrichData.extractProductName("/department/fan%20shop/category/camping%20&%20hiking/product/Diamondback%20Women's%20Serene%20Classic%20Comfort%20Bi");
        assertEquals("Diamondback Women's Serene Classic Comfort Bi", data);

        String data1 = EnrichData.extractProductName("/department/fan shop/category/water sports/product/Pelican Sunstream 100 Kayak/add_to_cart");
        assertEquals("Pelican Sunstream 100 Kayak", data1);

        String data2 = EnrichData.extractProductName("/department/fitness");
        assertEquals("N/A", data2);
    }
}