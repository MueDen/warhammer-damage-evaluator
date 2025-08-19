package com.warhammer.units.ultraMarines;

import com.warhammer.attacks.Profile;
import com.warhammer.model.Model;
import com.warhammer.modifiers.AttackProfileModificationEnum;

import java.util.List;

public class RobouteGuilliman extends Model
{

    public RobouteGuilliman(final boolean attackReroll,final boolean  woundReroll,final boolean  plusHit,final boolean  plusWound)
    {
        super(9,2,10, "Roboute Guilliman", attackReroll, woundReroll, plusHit, plusWound, 7, 7, getAttackModifications());
    }

    public RobouteGuilliman()
    {
        super(9,2,10, "Roboute Guilliman", false,false,false,false, 4, 7, getAttackModifications());
    }

    private static List<Profile> getAttackModifications()
    {
        final Profile handOfDominion = new Profile("Hand of Dominion ranged", List.of(AttackProfileModificationEnum.RAPID_FIRE_2), 2,2,6,2,2);
        final Profile emperorsSword = new Profile("Emperor’s Sword", List.of(AttackProfileModificationEnum.DEVASTATING_WOUNDS), 14,2,8,3,2);
        final Profile handOfDominionMelee = new Profile("Hand of Dominion Melee", List.of(AttackProfileModificationEnum.LETHAL_HITS), 7,2,14,4,4);
        return List.of(handOfDominion, handOfDominionMelee, emperorsSword);
    }
}
