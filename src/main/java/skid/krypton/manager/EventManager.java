// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.manager;

import skid.krypton.Krypton;
import skid.krypton.event.CancellableEvent;
import skid.krypton.event.Event;
import skid.krypton.event.EventListener;
import skid.krypton.event.Listener;
import skid.krypton.module.Module;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventManager {
    private final Map<Class<? extends Listener>, List<Listener>> EVENTS;

    public EventManager() {
        this.EVENTS = new HashMap<Class<? extends Listener>, List<Listener>>();
    }

    public void register(final Object o) {
        final Method[] declaredMethods = o.getClass().getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; ++i) {
            final Method method = declaredMethods[i];
            if (method.isAnnotationPresent(EventListener.class) && method.getParameterCount() == 1 && Listener.class.isAssignableFrom(method.getParameterTypes()[0])) {
                this.addListener(o, method, method.getAnnotation(EventListener.class));
            }
        }
    }

    private void addListener(final Object o, final Method method, final EventListener eventListener) {
        final Class<?> key = method.getParameterTypes()[0];
        method.setAccessible(true);
        this.EVENTS.computeIfAbsent((Class<? extends Listener>) key, p0 -> new CopyOnWriteArrayList()).add(new Listener(o, method, eventListener.priority()));
        this.EVENTS.get(key).sort(Comparator.comparingInt(listener -> listener.getPriority().getValue()));
    }

    public void unregister(Object v12) {
        for (List<Listener> listeners : this.EVENTS.values()) {
            listeners.removeIf(v1 -> v1.getInstance() == v12);
        }
    }

    public void clear() {
        this.EVENTS.clear();
    }

    public void a(final Event event) {
        List<Listener> listeners = this.EVENTS.get(event.getClass());
        if (listeners == null) return;

        for (Listener listener : listeners) {
            try {
                Object holder = listener.getInstance();

                if (holder instanceof Module && !((Module) holder).isEnabled()) {
                    continue;
                }

                if (!event.isCancelled() || event instanceof CancellableEvent) {
                    listener.invoke(event);
                }
            } catch (Throwable _t) {
                System.err.println("Error dispatching event " + event.getClass().getSimpleName() + " to " + (listener.getInstance() != null ? listener.getInstance().getClass().getSimpleName() : "unknown"));
                _t.printStackTrace(System.err);
            }
        }
    }

    public static void b(final Event listener) {
        if (Krypton.INSTANCE == null || Krypton.INSTANCE.c() == null) return;
        Krypton.INSTANCE.c().a(listener);
    }
}