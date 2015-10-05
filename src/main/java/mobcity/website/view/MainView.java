package mobcity.website.view;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;



public class MainView extends HorizontalLayout {

    public MainView(ComponentContainer mainContentWrapper) {
        super();
        setSizeFull();
        addStyleName("mainview");

        addComponent(new MobCityMenu());
        addComponent(mainContentWrapper);

        setExpandRatio(mainContentWrapper, 1.0f);
    }
}
