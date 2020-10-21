package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;
import viceCity.repositories.GunRepository;
import viceCity.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.List;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    private List<Player> civilPlayers;
    private Repository<Gun> gunRepository;
    private Player mainPlayer;
    private Neighbourhood neighbourhood;
    public ControllerImpl() {
        this.civilPlayers = new ArrayList<>();
        this.gunRepository = new GunRepository();
        this.mainPlayer = new MainPlayer();
        this.neighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayers.add(player);
        return String.format(ConstantMessages.PLAYER_ADDED,name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun = null;
        switch (type){
            case "Pistol":
                gun = new Pistol(name);
                break;
            case "Rifle":
                gun = new Rifle(name);
                break;
            default:
                return "Invalid gun type!";
        }
        gunRepository.add(gun);
        return String.format(ConstantMessages.GUN_ADDED,name,type);
    }

    @Override
    public String addGunToPlayer(String name) {
        if (this.gunRepository.getModels().size() <= 0){
            return "There are no guns in the queue!";
        }
        List<Gun> guns = (List<Gun>) gunRepository.getModels();
        Gun gun = guns.get(0);
        if (name.equals("Vercetti")){
            this.mainPlayer.getGunRepository().add(gun);
            this.gunRepository.remove(gun);
            return String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER,gun.getName(),"Tommy Vercetti");
        }
        Player player = findCivilPlayer(name,this.civilPlayers);
        if (player == null){
            return "Civil player with that name doesn't exists!";
        }
        player.getGunRepository().add(gun);
        this.gunRepository.remove(gun);
        return String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER,gun.getName(),name);
    }

    private Player findCivilPlayer(String name, List<Player> players) {
        Player player = null;
        for (Player player1 : players) {
            if (player1.getName().equals(name)){
                player = player1;
            }
        }
        return player;
    }

    @Override
    public String fight() {  this.neighbourhood.action(this.mainPlayer, this.civilPlayers);
        long deadCivilPlayers = this.civilPlayers
                .stream()
                .filter(p -> !p.isAlive())
                .count();

        StringBuilder sb = new StringBuilder();

        boolean areTakenLifePoints = this.civilPlayers.stream().allMatch(p -> p.getLifePoints() == 50);

        if (this.mainPlayer.getLifePoints() == 100 && areTakenLifePoints) {
            sb.append(FIGHT_HOT_HAPPENED);
        } else {
            sb.append(FIGHT_HAPPENED)
                    .append(System.lineSeparator())
                    .append(String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, this.mainPlayer.getLifePoints()))
                    .append(System.lineSeparator())
                    .append(String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, deadCivilPlayers))
                    .append(System.lineSeparator())
                    .append(String.format(CIVIL_PLAYERS_LEFT_MESSAGE, this.civilPlayers.size() - deadCivilPlayers));
        }

        String res = sb.toString().trim();
        return res;
    }
}
