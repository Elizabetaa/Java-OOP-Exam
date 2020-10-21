package viceCity.repositories;

import viceCity.models.guns.Gun;
import viceCity.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class GunRepository implements Repository<Gun> {
    private final Collection<Gun> models;

    public GunRepository() {
        this.models = new LinkedList<>();
    }

    @Override
    public Collection<Gun> getModels() {
        return this.models;
    }

    @Override
    public void add(Gun model) {
        boolean exist = false;
        for (Gun gun : models) {
            if (gun.getName().equals(model.getName())){
                exist = true;
            }
        }
        if (!exist){
            this.models.add(model);
        }
    }

    @Override
    public boolean remove(Gun model) {
        return this.models.remove(model);
    }

    @Override
    public Gun find(String name) {
        Gun gun1 = null;
        for (Gun gun : models) {
            if (gun.getName().equals(name)){
                gun1 = gun;
            }
        }
        return gun1;
    }
}
