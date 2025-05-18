module cryptopad.main {
    requires  com.formdev.flatlaf;
    requires  com.formdev.flatlaf.intellijthemes;
    requires org.bouncycastle.provider;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires jdk.unsupported;
    requires java.datatransfer;


    exports actions;
    exports controllers;
    exports drivers;
    exports handlers.matcher;
    exports handlers.menu;
    exports handlers.tabs;
    exports models;
    exports util;
    exports util.enums;
    exports util.constants;
    exports handlers.keystore;
    exports handlers.preferences;
    exports components.dialogs;
    exports components.panels;
}