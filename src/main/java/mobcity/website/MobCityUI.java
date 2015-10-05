package mobcity.website;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import mobcity.website.domain.User;
import mobcity.website.event.MobCityEvent;
import mobcity.website.event.MobCityEventBus;
import mobcity.website.view.LoginView;
import mobcity.website.view.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Theme("mobcity")
@SpringUI
public class MobCityUI extends UI {
    //private final DataProvider dataProvider = new DummyDataProvider();
    private final MobCityEventBus dashboardEventbus = new MobCityEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.GERMANY);

        MobCityEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        // Fire event if browser resize detected
        Page.getCurrent().addBrowserWindowResizeListener(
                new Page.BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final Page.BrowserWindowResizeEvent event) {
                        MobCityEventBus.post(new MobCityEvent.BrowserResizeEvent());
                    }
                }
        );
    }

    /**
     * Shows view based on user object in session
     * If user is authenticated MainView is dispayed
     * Otherwise LoginView
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final MobCityEvent.UserLoginRequestEvent event) {
        // TODO: Add UserService for authentication
        // TODO: Use Spring for Dependency Injection here
        User user = new User();
        user.setFirstName(event.getUserName());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final MobCityEvent.UserLoggedOutEvent event) {
        // Remove all atributes from session and reload page on the login screen
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    public void closeOpenWindows(final MobCityEvent.CloseOpenWindowsEvent event) {
        for (Window window: getWindows()) {
            window.close();
        }
    }

    public static MobCityEventBus getDashboardEventbus() {
        return ((MobCityUI) getCurrent()).dashboardEventbus;
    }
}
