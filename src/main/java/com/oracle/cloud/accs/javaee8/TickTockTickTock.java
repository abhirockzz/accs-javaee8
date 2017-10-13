package com.oracle.cloud.accs.javaee8;

import java.util.Random;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class TickTockTickTock {

    @Inject
    @TickTockQualfier
    private Event<TickTock> eventBus;

    private static final Random gen = new Random();

    @Resource
    private ManagedExecutorService mes;

    public void onTrigger(@Observes @Initialized(ApplicationScoped.class) Object test) {

        mes.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        eventBus.fireAsync(new TickTock("tick-" + gen.nextInt(10), "tock-" + gen.nextInt(10)));
                        System.out.println("Fired CDI event from thread " + Thread.currentThread().getName());
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        System.out.println("Scheduler initialized");
    }
}
