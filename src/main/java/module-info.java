module cryptopad {
    requires  com.formdev.flatlaf;
    requires  com.formdev.flatlaf.intellijthemes;
    requires org.bouncycastle.provider;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;


    exports actions;
    exports components;
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