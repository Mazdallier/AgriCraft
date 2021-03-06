package com.InfinityRaider.AgriCraft.compatibility;

import com.InfinityRaider.AgriCraft.reference.Names;
import com.InfinityRaider.AgriCraft.utility.LogHelper;
import cpw.mods.fml.common.Loader;

public class LoadedMods {
    public static boolean nei;
    public static boolean harvestcraft;
    public static boolean natura;
    public static boolean thaumicTinkerer;

    public static void init() {
        nei = Loader.isModLoaded(Names.nei);
        harvestcraft = Loader.isModLoaded(Names.harvestcraft);
        natura = Loader.isModLoaded(Names.natura);
        thaumicTinkerer = Loader.isModLoaded(Names.thaumicTinkerer);
        LogHelper.info("NEI loaded: "+nei);
        LogHelper.info("HarvestCraft loaded: "+harvestcraft);
        LogHelper.info("Natura loaded: "+natura);
        LogHelper.info("Thaumic tinkerer loaded: "+thaumicTinkerer);
    }
}
