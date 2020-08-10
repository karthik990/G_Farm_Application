package com.google.rpc;

import com.google.protobuf.MessageLiteOrBuilder;
import com.google.rpc.Help.Link;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface HelpOrBuilder extends MessageLiteOrBuilder {
    Link getLinks(int i);

    int getLinksCount();

    List<Link> getLinksList();
}
