/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.wallet.entities;

import com.avaje.ebean.EbeanServer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author PanRyba.pl
 */

@Entity
@Table(name = "wallets")
public class Wallet {

    public static Wallet getForPlayer(EbeanServer database, String playerName) {
        return database.find(Wallet.class, playerName);
    }

    public static void changeBy(EbeanServer database, String playerName, int delta, String details) {

        database.createUpdate(Wallet.class,
                "UPDATE wallet w SET w.coins = w.coins + :delta, w.details = :details WHERE player = :player").
                setParameter("delta", delta).
                setParameter("player", playerName).
                setParameter("details", details).
                execute();
    }

    @Id
    private String player;

    @Column(name = "coins", nullable = false)
    private int coins;

    @Column(name = "details", nullable = true)
    private String details;

    @Version
    private Long version;

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return this.player;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }
}
