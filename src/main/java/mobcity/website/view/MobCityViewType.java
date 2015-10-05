package mobcity.website.view;


import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import mobcity.website.view.discussion.DiscussionView;

public enum MobCityViewType {
    DISCUSSION(DiscussionView.VIEW_NAME, DiscussionView.class, FontAwesome.HOME, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private MobCityViewType(final String viewName,
                            final Class<? extends View> viewClass, final Resource icon,
                            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static MobCityViewType getByViewName(final String viewName) {
        MobCityViewType result = null;
        for (MobCityViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
}
