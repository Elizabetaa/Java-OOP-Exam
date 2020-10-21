package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GangNeighbourhood implements Neighbourhood {

    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        List<Gun> guns = (List<Gun>) mainPlayer.getGunRepository().getModels();
        int index = 0;
        for (Player civilPlayer : civilPlayers) {
            if (index >= guns.size()){
                break;
            }
            while (civilPlayer.isAlive() && guns.get(index).canFire()) {
                civilPlayer.takeLifePoints(guns.get(index).fire());
                if (!guns.get(index).canFire()) {
                    index++;
                    if (index >= guns.size()){
                        break;
                    }
                }
                if (!civilPlayer.isAlive()) {
                    break;
                }
            }
        }
        List<Player> players = new ArrayList<>();
        for (Player civilPlayer : civilPlayers) {
            if (civilPlayer.isAlive()) {
                players.add(civilPlayer);
            }
        }
        index = 0;
        if (players.size() > 0) {
            for (Player player : players) {
                guns = (List<Gun>) player.getGunRepository().getModels();
                if (index >= guns.size()){
                    break;
                }
                while (mainPlayer.isAlive()){
                    if (guns.get(index).canFire()){
                        mainPlayer.takeLifePoints(guns.get(index).fire());
                    }else {
                        index ++;
                        if (index >= guns.size()){
                            break;
                        }
                    }
                }
            }
        }

    }
}
