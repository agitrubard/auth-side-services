package com.agitrubard.authside.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthSideValidTestData {

    public static final String EMAIL_ADDRESS = "test@authside.com";

    public static class AdminUser {
        public static final String ID = "e86f01ee-bde2-4a4d-b98b-b7a76041c0a7";
        public static final String USERNAME = "agitrubardemir";
        public static final String EMAIL_ADDRESS = "authside.admin@gmail.com";
        public static final String PASSWORD_ENCRYPTED = "$2a$10$kTZ1jlgzj1xxNKEye5gIM.LG9EAk9GAmJ/jxK9UHRoQpH.SdN0AkK";
        public static final String PASSWORD = "authsidepass";
    }

    public static class ReadUser {
        public static final String ID = "93cdd260-57cd-4536-bd17-e35b5b7c68fd";
        public static final String USERNAME = "agitrubard";
        public static final String EMAIL_ADDRESS = "demiragitrubar@gmail.com";
        public static final String PASSWORD_ENCRYPTED = "$2a$10$kTZ1jlgzj1xxNKEye5gIM.LG9EAk9GAmJ/jxK9UHRoQpH.SdN0AkK";
        public static final String PASSWORD = "authsidepass";
    }

}
