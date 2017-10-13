package com.oracle.cloud.accs.javaee8;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.SseEventSink;

@Path("")
public class EventsResource {

    @Inject
    Broadcaster broadcaster;

    @Path("subscribe")
    @GET
    @Produces("text/event-stream")
    public void subscribe(@Context SseEventSink eSink) {
        broadcaster.register(eSink);
    }

}
