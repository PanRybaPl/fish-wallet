/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.panryba.mc.wallet;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import pl.panryba.mc.db.FishDbPlugin;
import pl.panryba.mc.wallet.commands.WalletCommand;
import pl.panryba.mc.wallet.entities.Wallet;

/**
 *
 * @author PanRyba.pl
 */
public class Plugin extends FishDbPlugin {

    @Override
    public void onEnable() {
        EbeanServer database = getCustomDatabase();

        PluginApi api = new PluginApi(database);
        WalletManager.setup(api);

        WalletCommand walletCommand = new WalletCommand(api);
        getCommand("portfel").setExecutor(walletCommand);
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = super.getDatabaseClasses();

        list.add(Wallet.class);

        return list;
    }
}
