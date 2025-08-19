package com.warhammer.units.tyrannids;

import com.warhammer.attacks.Profile;
import com.warhammer.model.Model;

import java.util.List;

public class Tyrannofex extends Model {
    private static final int toughness = 12;
    private static final int save = 2;
    private static final int wounds = 16;

    public Tyrannofex(final boolean attackReroll,final boolean  woundReroll,final boolean  plusHit,final boolean  plusWound)
    {
        super(toughness, save, wounds, "Tyrannofex", attackReroll, woundReroll, plusHit, plusWound, 7, 7, getAttackModifications());
    }

    public Tyrannofex()
    {
        super(toughness, save, wounds, "Tyrannofex", false,false,false,false, 4, 7, getAttackModifications());
    }

    private static List<Profile> getAttackModifications()
    {
        return List.of();
    }

}
