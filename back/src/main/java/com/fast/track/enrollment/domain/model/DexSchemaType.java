package com.irichment.enrollment.domain.model;


public enum DexSchemaType {
    MODULE_1,  // 0  (0-3 Month)
    MODULE_2,  // 1  (4-6 Month)
    MODULE_3,  // 2  (7-9 Month)
    MODULE_4,  // 3  (10-12 Month)
    MODULE_5,  // 4  (13-15 Month)
    MODULE_6,  // 5  (16-18 Month)
    MODULE_7,  // 6  (19-24 Month)
    MODULE_8,  // 7  (25-30 Month)
    MODULE_9,  // 8  (31-36 Month)
    MODULE_10, // 10 (37-42 Month)
    MODULE_11; // 11 (43-48 Month)


    public static DexSchemaType get(int ordinal) { return values()[ordinal]; }

    }
