package mobcity.website.view.discussion;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mobcity.website.component.DiscussionBoardEntry;
import mobcity.website.component.DiscussionBoardEntryWindow;
import mobcity.website.event.MobCityEventBus;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringView(name = DiscussionView.VIEW_NAME)
public class DiscussionView extends Panel implements View {
    public static final String VIEW_NAME = "discussion";
    public static final String TITLE_ID = "discussion-title";


    private VerticalLayout rootLayout;
    private CssLayout discussionPanels;

    @PostConstruct
    void init() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        MobCityEventBus.register(this);

        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.setMargin(true);
        rootLayout.addStyleName("discussion-view");
        setContent(rootLayout);
        Responsive.makeResponsive(rootLayout);

        //build components for rootLayout
        rootLayout.addComponent(buildHeader());

        Component mainContent = buildMainContent();
        rootLayout.addComponent(mainContent);
        rootLayout.setExpandRatio(mainContent, 1);
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        //title on the left
        Label title = new Label("Discussion Board");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        //toolbar on the right
        HorizontalLayout toolbar = new HorizontalLayout();
        Component newEntryButton = buildNewEntryButton();
        toolbar.addComponent(newEntryButton);
        toolbar.setSpacing(true);
        toolbar.addStyleName("toolbar");
        header.addComponent(toolbar);

        return header;
    }

    private Component buildNewEntryButton() {
        Button newEntry = new Button();
        newEntry.setIcon(FontAwesome.PLUS);
        newEntry.addStyleName("icon-add");
        newEntry.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        newEntry.setDescription("Add new Discussion Entry");
        newEntry.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().addWindow(new DiscussionBoardEntryWindow(DiscussionView.this));
            }
        });
        return null;
    }

    private Component buildMainContent() {
        discussionPanels = new CssLayout();
        discussionPanels.addStyleName("discussion-panels");
        Responsive.makeResponsive(discussionPanels);

        buildDiscussionPanels();
        return discussionPanels;
    }

    private void buildDiscussionPanels() {
        List<DiscussionBoardEntry> entryList = new ArrayList<DiscussionBoardEntry>();
        for(DiscussionBoardEntry entry : entryList) {
            entry.setSizeFull();
            discussionPanels.addComponent(createContentWrapper(entry));
        }
    }

    private Component createContentWrapper(final Component entry) {
        return null;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
