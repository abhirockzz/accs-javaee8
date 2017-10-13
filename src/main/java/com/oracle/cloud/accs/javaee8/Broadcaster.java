package com.oracle.cloud.accs.javaee8;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Path("")
@ApplicationScoped
public class Broadcaster {
    @Context
    Sse sse;
    
    private SseBroadcaster broadcaster;
    
    @PostConstruct
    public void init(){
        broadcaster = sse.newBroadcaster();
        System.out.println("broadcaster created");
    }
        
    public void register(SseEventSink eventSink){
        broadcaster.register(eventSink);
        System.out.println("Registered Event sink");
    }
    
    public void listener(@ObservesAsync @TickTockQualfier TickTock ticktock){
        System.out.println("SSE event in thread "+ Thread.currentThread().getName());
        
        OutboundSseEvent sseEvent = sse.newEventBuilder()
                .id(String.valueOf(System.currentTimeMillis()))
                .data(new TickTock(ticktock.getTick(), ticktock.getTock()))
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build();
        
        broadcast(sseEvent);
    }
    
    public void broadcast(OutboundSseEvent event){
        try {
            broadcaster.broadcast(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
    
    @PreDestroy
    public void free(){
        broadcaster.close();
        System.out.println("broadcaster closed");
    }
}
