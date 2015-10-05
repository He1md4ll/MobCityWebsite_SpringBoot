package mobcity.website;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import mobcity.website.event.MobCityEvent;
import mobcity.website.event.MobCityEventBus;
import mobcity.website.view.MobCityViewType;

public class MobCityNavigator extends Navigator {

    public MobCityNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container);
        initViewChangeListener();
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
}
