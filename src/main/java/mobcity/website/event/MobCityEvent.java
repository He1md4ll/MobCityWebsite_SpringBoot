package mobcity.website.event;

import mobcity.website.view.MobCityViewType;

public class MobCityEvent {
    public static final class UserLoginRequestEvent {
        private final String userName, password;

        public UserLoginRequestEvent(final String userName, final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {
    }

    public static class UserLoggedOutEvent {
    }

    public static final class PostViewChangeEvent {
        private final MobCityViewType view;

        public PostViewChangeEvent(final MobCityViewType view) {
            this.view = view;
        }

        public MobCityViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }
}
