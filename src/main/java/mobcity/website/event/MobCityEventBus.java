package mobcity.website.event;


import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;
import mobcity.website.MobCityUI;

/**
 * Created by Alpha on 03.10.2015.
 */
public class MobCityEventBus implements SubscriberExceptionHandler {
    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MobCityUI.getDashboardEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
        MobCityUI.getDashboardEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        MobCityUI.getDashboardEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
