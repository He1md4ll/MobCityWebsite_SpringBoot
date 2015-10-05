package mobcity.website.view;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mobcity.website.domain.User;
import mobcity.website.event.MobCityEvent;
import mobcity.website.event.MobCityEventBus;

public class MobCityMenu extends CustomComponent {
    private static final String STYLE_VISIBLE = "valo-menu-visible";

    public MobCityMenu() {
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        setId("mobcity-menu");
        setSizeUndefined();

        MobCityEventBus.register(this);

        setCompositionRoot(buildContnet());
    }

    private Component buildContnet() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label label = new Label("MobCity");
        label.addStyleName(ValoTheme.LABEL_BOLD);
        label.setSizeUndefined();

        HorizontalLayout labelWrapper = new HorizontalLayout(label);
        labelWrapper.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        labelWrapper.addStyleName(ValoTheme.MENU_TITLE);

        return labelWrapper;
    }

    private Component buildUserMenu() {
        final MenuBar menuBar = new MenuBar();
        menuBar.addStyleName("user-menu");

        MenuBar.MenuItem menuItem = menuBar.addItem("", FontAwesome.ANDROID, null);
        updateUserName(menuItem);
        menuItem.addItem("Sign Out", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                MobCityEventBus.post(new MobCityEvent.UserLoggedOutEvent());
            }
        });
        return menuBar;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final MobCityViewType view: MobCityViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;
    }

    private void updateUserName(MenuBar.MenuItem menuItem) {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        menuItem.setText(user.getFirstName() + " " + user.getLastName());
    }

    @Subscribe
    public void postViewChange(final MobCityEvent.PostViewChangeEvent event) {
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }

    public final class ValoMenuItemButton extends Button {
        private static final String SYTLE_SELECTED = "selected";
        private final MobCityViewType view;

        public ValoMenuItemButton(final MobCityViewType view) {
            this.view = view;
            setPrimaryStyleName(ValoTheme.MENU_ITEM);
            setIcon(view.getIcon());
            setCaption(view.getViewName());
            MobCityEventBus.register(this);
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    UI.getCurrent().getNavigator().navigateTo(view.getViewName());
                }
            });
        }

        @Subscribe
        public void postViewChange(final MobCityEvent.PostViewChangeEvent event) {
            removeStyleName(SYTLE_SELECTED);
            if (event.getView() == view){
                addStyleName(SYTLE_SELECTED);
            }
        }
    }
}
