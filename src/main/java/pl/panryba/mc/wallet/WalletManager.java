/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.wallet;

import org.bukkit.entity.Player;

/**
 *
 * @author PanRyba.pl
 */
public class WalletManager {
    private static WalletManager instance;
    private PluginApi api;

    public WalletManager(PluginApi api) {
        this.api = api;
    }

    public static void setup(PluginApi api) {
        WalletManager.instance = new WalletManager(api);
    }

    public static WalletManager getInstance() {
        return WalletManager.instance;
    }

    public int getPlayerCoins(String playerName) {
        return api.getCoins(playerName);
    }

    public int getPlayerCoins(Player player) {
        return getPlayerCoins(player.getName());
    }

    public void changePlayerCoins(Player player, int delta) {
        api.changeCoinsBy(player, delta, null);
    }

    public boolean charge(Player player, int coins) {
        return api.charge(player, coins, null);
    }

    public boolean charge(Player player, int coins, String details) {
        return api.charge(player, coins, details);
    }

    public boolean charge(String playerName, int coins) {
        return api.charge(playerName, coins, null);
    }

    public boolean charge(String playerName, int coins, String details) {
        return api.charge(playerName, coins, details);
    }

    public void addCoins(String playerName, int coins) {
        api.changeCoinsBy(playerName, coins, null);
    }

    public void addCoins(String playerName, int coins, String details) {
        api.changeCoinsBy(playerName, coins, details);
    }
}
