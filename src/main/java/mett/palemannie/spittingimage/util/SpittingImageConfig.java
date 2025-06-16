package mett.palemannie.spittingimage.util;

import eu.midnightdust.lib.config.MidnightConfig;

public class SpittingImageConfig extends MidnightConfig {

    @Entry(min = 0.0, max = Float.MAX_VALUE)
    public static float spitdamage = 1.0f;

    @Entry
    public static boolean enable3dmodel = true;
    @Comment(category = "text") public static Comment damagedesc;
    @Comment(category  = "text") public static Comment modeldesc;
}