package mobcity.website;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import mobcity.website.event.MobCityEvent;
import mobcity.website.event.MobCityEventBus;
import mobcity.website.view.MobCityViewType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MobCityNavigator extends Navigator {
    private static final MobCityViewType ERROR_VIEW = MobCityViewType.DISCUSSION;

    @Autowired
    private SpringViewProvider viewProvider;

    public MobCityNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container);
    }

    @PostConstruct
    void init() {
        initViewChangeListener();
        addProvider(viewProvider);
        initErrorProviders();
    }

    private void initViewChangeListener() {
        addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                //TODO: Do some permission checks if necessary
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                MobCityViewType view = MobCityViewType.getByViewName(event
                        .getViewName());
                // Appropriate events get fired after the view is changed.
                MobCityEventBus.post(new MobCityEvent.PostViewChangeEvent(view));
                MobCityEventBus.post(new MobCityEvent.BrowserResizeEvent());
                MobCityEventBus.post(new MobCityEvent.CloseOpenWindowsEvent());
            }
        });
    }

    private void initErrorProviders() {
        setErrorProvider(new ViewProvider() {
            @Override
            public String getViewName(final String viewAndParameters) {
                return ERROR_VIEW.getViewName();
            }

            @Override
            public View getView(final String viewName) {
                return viewProvider.getView(ERROR_VIEW.getViewName());
            }
        });
    }
}
