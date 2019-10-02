package com.rclgroup.dolphin.ijs.web.factory;

import com.rclgroup.dolphin.ijs.web.action.IjsBaseLookup;

public class IjsLookupFactory {
    private static IjsLookupFactory INSTANCE = null;

    private IjsLookupFactory() {
    }

    public static IjsLookupFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IjsLookupFactory();
        }
        return INSTANCE;
    }

    public static IjsBaseLookup getLookup(String lookup) {
        return null;
    }
}
