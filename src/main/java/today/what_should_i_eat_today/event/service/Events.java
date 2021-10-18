package today.what_should_i_eat_today.event.service;

import org.springframework.context.ApplicationEventPublisher;
import today.what_should_i_eat_today.event.event.DomainEvent;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.Queue;

public class Events {
    private static ThreadLocal<ApplicationEventPublisher> publisherLocal = new ThreadLocal<>();
    private static ThreadLocal<EntityManager> managerThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<LinkedList<DomainEvent>> eventHolder = new ThreadLocal<>();

    public static void raise(DomainEvent event) {
        if (event == null) return;

        EntityManager entityManager = managerThreadLocal.get();

        if (publisherLocal.get() != null) {
            if (entityManager != null && entityManager.contains(event.getDomain()))
                publisherLocal.get().publishEvent(event);
            else
                eventHolder.get().add(event);
        }

    }

    static void setPublisher(ApplicationEventPublisher publisher) {
        publisherLocal.set(publisher);
    }

    static void setEntityManager(EntityManager manager) {
        managerThreadLocal.set(manager);
    }

    static void setQueue(LinkedList<DomainEvent> queue) {
        eventHolder.set(queue);
    }

    static void reset() {
        publisherLocal.remove();
        managerThreadLocal.remove();
        eventHolder.remove();
    }

    static Queue<DomainEvent> getDelayEvent() {
        return eventHolder.get();
    }
}
