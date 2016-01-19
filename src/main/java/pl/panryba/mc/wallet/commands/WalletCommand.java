/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.wallet.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.panryba.mc.pl.Declination;
import pl.panryba.mc.pl.DeclinationManager;
import pl.panryba.mc.wallet.PluginApi;
import pl.panryba.mc.wallet.WalletManager;

/**
 *
 * @author PanRyba.pl
 */
public class WalletCommand implements CommandExecutor {

    private PluginApi api;
    private DeclinationManager pl;
    private Declination coinsSingular;
    private Declination coinsPlural;

    public WalletCommand(PluginApi api) {
        this.api = api;
        this.pl = DeclinationManager.getInstance();

        this.coinsSingular = new Declination("moneta", "monety", "monecie", "monete", "moneta", "monecie", "moneto");
        this.coinsPlural = new Declination("monety", "monet", "monetom", "monety", "monetami", "monetach", "monety");
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (!cmnd.getName().equalsIgnoreCase("portfel")) {
            return false;
        }

        if (!(cs instanceof Player)) {
            return false;
        }

        Player player = (Player) cs;
        Player targetPlayer;

        if (strings.length > 0) {
            targetPlayer = player.getServer().getPlayer(strings[0]);
        } else {
            targetPlayer = player;
        }

        if (targetPlayer == null) {
            player.sendMessage("Nie znaleziono gracza o podanym nicku");
            return true;
        }

        boolean samePlayer = targetPlayer.getName().equals(player.getName());

        if (strings.length > 1) {
            String subOp = strings[1];
            if(!subOp.equals("daj")) {
                return false;
            }

            if(strings.length < 3) {
                return false;
            }

            if(!player.isOp()) {
                player.sendMessage("Nie masz uprawnien do tego polecenia");
                return true;
            }

            int coins = Integer.parseInt(strings[2]);
            WalletManager.getInstance().addCoins(targetPlayer.getName(), coins);

            String coinsString = DeclinationManager.getInstance().getPossesiveCountDeclination(coins, coinsSingular, coinsPlural);
            if(samePlayer) {
                player.sendMessage("Przekazales sobie " + coins + " " + coinsString);
            } else {
                player.sendMessage("Przekazales " + coins + " " + coinsString + " graczowi " + targetPlayer.getName());
            }
        } else {
            if (!samePlayer) {
                if (!player.isOp()) {
                    player.sendMessage("Nie masz uprawnien aby sprawdzic portfel innego gracza");
                    return true;
                }
            }
            int coinsCount = api.getCoins(targetPlayer.getName());

            if (coinsCount == 0) {
                if (samePlayer) {
                    player.sendMessage("Nie posiadasz monet");
                } else {
                    player.sendMessage("Gracz " + targetPlayer.getName() + " nie posiada monet");
                }
            } else {
                String coinsString = this.pl.getPossesiveCountDeclination(coinsCount, this.coinsSingular, this.coinsPlural);
                if (samePlayer) {
                    player.sendMessage("Posiadasz " + coinsCount + " " + coinsString);
                } else {
                    player.sendMessage("Gracz " + targetPlayer.getName() + " posiada " + coinsCount + " " + coinsString);
                }
            }
        }

        return true;
    }
}
