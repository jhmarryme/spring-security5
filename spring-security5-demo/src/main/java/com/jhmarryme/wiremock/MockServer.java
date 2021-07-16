package com.jhmarryme.wiremock;

//import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.lang3.StringUtils;

/*
public class MockServer {

    */
/**
     * @param args
     * @throws IOException
     *//*

    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8062);
        WireMock.removeAllMappings();

        mock("/order/1", "01");
        mock("/order/2", "02");
    }

    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + file + ".txt");
        String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url)).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }

}*/
