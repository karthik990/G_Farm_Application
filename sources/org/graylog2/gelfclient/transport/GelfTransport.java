package org.graylog2.gelfclient.transport;

import org.graylog2.gelfclient.GelfMessage;

public interface GelfTransport {
    void send(GelfMessage gelfMessage) throws InterruptedException;

    void stop();

    boolean trySend(GelfMessage gelfMessage);
}
