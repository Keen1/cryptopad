module cryptopad {
    requires java.desktop;
    requires static com.formdev.flatlaf;
    requires static com.formdev.flatlaf.intellijthemes;



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
}