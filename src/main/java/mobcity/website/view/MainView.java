package mobcity.website.view;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import mobcity.website.MobCityNavigator;


public class MainView extends HorizontalLayout {
    public MainView() {
        setSizeFull();
        addStyleName("mainview");

        addComponent(new MobCityMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new MobCityNavigator(content);
    }
}
