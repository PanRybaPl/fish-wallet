/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.wallet;

import com.avaje.ebean.EbeanServer;
import org.bukkit.entity.Player;
import pl.panryba.mc.wallet.entities.Wallet;

/**
 *
 * @author PanRyba.pl
 */
public class PluginApi {
    private EbeanServer database;

    public PluginApi(EbeanServer database) {
        this.database = database;
    }

    public Wallet getWallet(Player player) {
        return this.getWallet(player.getName());
    }

    public Wallet getWallet(String playerName) {
        Wallet wallet = Wallet.getForPlayer(this.database, playerName);
        if(wallet == null) {
            wallet = new Wallet();
            wallet.setPlayer(playerName);
            wallet.setCoins(0);

            this.database.save(wallet);
        }

        return wallet;
    }

    public int getCoins(String playerName) {
        Wallet wallet = getWallet(playerName);
        return wallet.getCoins();
    }

    void changeCoinsBy(Player player, int delta, String details) {
        this.changeCoinsBy(player.getName(), delta, details);
    }

    void changeCoinsBy(String playerName, int delta, String details) {
        // Get wallet to ensure it exists
        getWallet(playerName);

        Wallet.changeBy(this.database, playerName, delta, details);
    }

    boolean charge(Player player, int coins, String details) {
        return this.charge(player.getName(), coins, details);
    }

    boolean charge(String playerName, int coins, String details) {
        Wallet wallet = getWallet(playerName);
        if(wallet.getCoins() < coins)
            return false;

        if(coins < 0)
            coins = 0;

        if(coins == 0)
            return true;

        try {
            Wallet.changeBy(this.database, playerName, -coins, details);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
