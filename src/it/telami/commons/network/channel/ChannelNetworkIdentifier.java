package it.telami.commons.network.channel;

import it.telami.annotations.PlannedForFuture;

import java.nio.channels.NetworkChannel;

@PlannedForFuture
public interface ChannelNetworkIdentifier {

    boolean isLocal ();

    CrossJVMChannel<NetworkChannel> internalCommunicationHandler ();

    byte[] externalRead ();
    boolean externalWrite ();
}
