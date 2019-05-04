package com.yrw.im.gateway.connector.start;

/**
 * Date: 2019-05-02
 * Time: 17:59
 *
 * @author yrw
 */
public class ConnectorStarter {

    public static void main(String[] args) {
        int port = 9081;
        ConnectorServer.startGateServer(port);

        String transferHost = "127.0.0.1";
        int transferPort = 9082;
        ConnectorClient.startClient(transferHost, transferPort);
    }
}
