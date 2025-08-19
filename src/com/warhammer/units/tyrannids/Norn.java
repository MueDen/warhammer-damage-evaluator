package com.warhammer.units.tyrannids;

import com.warhammer.attacks.Profile;
import com.warhammer.model.Model;
import com.warhammer.modifiers.AttackProfileModificationEnum;

import java.util.List;

public class Norn extends Model {
    public Norn(final boolean attackReroll,final boolean  woundReroll,final boolean  plusHit,final boolean  plusWound)
    {
        super(11,2,16, "Norn", attackReroll, woundReroll, plusHit, plusWound, 4, 5, getAttackModifications());
    }

    public Norn()
    {
        super(11,2,16, "Norn", false,false,false,false, 4, 5, getAttackModifications());
    }

    private static List<Profile> getAttackModifications()
    {
        return List.of();
    }
}
