package com.warhammer.model;

import com.warhammer.attacks.Profile;
import com.warhammer.modifiers.AttackProfileModificationEnum;
import com.warhammer.modifiers.Modification;

import java.util.List;

public class Model
{
    private final int toughness;
    private final int save;
    private final int wounds;
    private final String name;
    private final boolean attackReroll;
    private final boolean woundReroll;
    private final boolean plusHit;
    private final boolean plusWound;
    private final int invulSave;
    private final int feelNoPain;

    private List<Modification> modificationlist;
    private final List<Profile> profiles;

    public Model(int toughness, int save, int wounds, String name, boolean attackReroll, boolean woundReroll, boolean plusHit, boolean plusWound, int invulSave, int feelNoPain, List<Profile> profiles)
    {
        this.toughness = toughness;
        this.save = save;
        this.wounds = wounds;
        this.name = name;
        this.attackReroll = attackReroll;
        this.woundReroll = woundReroll;
        this.plusHit = plusHit;
        this.plusWound = plusWound;
        this.invulSave = invulSave;
        this.feelNoPain = feelNoPain;
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public List<Modification> getModificationlist() {
        return modificationlist;
    }

    public void setModificationlist(List<Modification> modificationlist) {
        this.modificationlist = modificationlist;
    }

    public int getToughness() {
        return toughness;
    }

    public int getSave() {
        return save;
    }

    public int getWounds() {
        return wounds;
    }

    public void attackTarget(final Model target)
    {
        for(final Profile profile: profiles)
        {
            // Hit roll
            float attacks = profile.getAttacks();
            boolean containsRapidFire = false;
            if(containsAttackProfile(AttackProfileModificationEnum.RAPID_FIRE_1, profile)) {
                containsRapidFire = true;
                float incomingDamage = getIncomingDamage(target, profile, attacks+1);
                System.out.println("["+ profile.getName() +"] of " + this.name + " deals on average " + incomingDamage + " damage to " + target.name + " which has "+ target.getWounds() + " total wounds. With rapid fire 1");

            }
            if(containsAttackProfile(AttackProfileModificationEnum.RAPID_FIRE_2, profile)) {
                containsRapidFire = true;
                float incomingDamage = getIncomingDamage(target, profile, attacks+2);
                System.out.println("["+ profile.getName() +"] of " + this.name + " deals on average " + incomingDamage + " damage to " + target.name + " which has "+ target.getWounds() + " total wounds. With rapid fire 2");
            }

            float incomingDamage = getIncomingDamage(target, profile, attacks);

            System.out.println("["+ profile.getName() +"] of " + this.name + " deals on average " + incomingDamage + " damage to " + target.name + " which has "+ target.getWounds() + " total wounds." + (containsRapidFire ? " without rapid fire" : ""));

        }
    }

    private float getIncomingDamage(final Model target, final Profile profile, final float attacks) {
        float hits = getHits(profile, attacks);

        // Wound roll
        float lethalHits = containsAttackProfile(AttackProfileModificationEnum.LETHAL_HITS, profile) ? attacks / 6 : 0;

        float wounds = getWounds(target, profile, hits, lethalHits);

        float devastatingWounds = containsAttackProfile(AttackProfileModificationEnum.DEVASTATING_WOUNDS, profile) ? wounds / 6 : 0;


        float successfulWounds = resistance(wounds - devastatingWounds, target, profile.getAp());
        successfulWounds += devastatingWounds;

        float incomingDamage = successfulWounds * profile.getDamage();

        if(target.getFeelNoPain() <= 6)
        {
            incomingDamage = incomingDamage * (target.getFeelNoPain()-1)/6;
        }

        return incomingDamage;
    }

    private float getWounds(final Model target, final Profile profile,final float hits,final float lethalHits) {

        float wounds = calculateWounds(profile, hits - lethalHits, target.getToughness());
        wounds += lethalHits;

        float missedWounds = hits - wounds;
        wounds += isWoundReroll() ? calculateWounds(profile, missedWounds, target.getToughness()) : 0;
        return wounds;
    }

    private float getHits(final Profile profile, final float attacks) {
        float hits = calculateHits(profile, attacks);
        float missedAttacks = attacks - hits;

        hits += isAttackReroll() ? calculateHits(profile, missedAttacks) : 0;
        return hits;
    }

    private float resistance(float wounds, Model target, int ap) {
        float finalSave = Math.min(target.getSave() + ap, target.getInvulSave());
        if(finalSave > 6) return wounds;
        return wounds * (finalSave - 1)/6;
    }

    private float calculateHits(final Profile profile, float attacks)
    {
        float skill = profile.getSkill();
        if(isPlusHit() && skill > 2) skill -= 1;
        final float hitChance = (7-skill)/6;
        float hits = attacks * hitChance;

        final float additionalHits = containsAttackProfile(AttackProfileModificationEnum.SUSTAINED_HITS, profile) ? attacks / 6 : 0;
        return hits + additionalHits;
    }

    private float calculateWounds(final Profile profile, float hits, int toughness)
    {
        float woundThreshold = getWoundThreshold(profile.getStrength(), toughness);
        final float woundChance = (7-woundThreshold)/6;

        return hits * woundChance;
    }

    private float getWoundThreshold(int strength, int toughness) {
        if(strength >= toughness * 2) return 2;
        int regularThreshold = 3;
        if(strength == toughness) regularThreshold = 4;
        if(strength < toughness) regularThreshold = 5;
        if(strength*2 < toughness) regularThreshold = 6;
        if(plusWound) regularThreshold -= 1;
        return regularThreshold;
    }


    private boolean containsAttackProfile(final AttackProfileModificationEnum attackModificationEnum, final Profile profile)
    {
        return profile.getAttackProfileModificationEnumList().contains(attackModificationEnum);
    }

    public boolean isAttackReroll() {
        return attackReroll;
    }

    public boolean isWoundReroll() {
        return woundReroll;
    }

    public boolean isPlusHit() {
        return plusHit;
    }

    public boolean isPlusWound() {
        return plusWound;
    }

    public int getInvulSave() {
        return invulSave;
    }

    public int getFeelNoPain() {
        return feelNoPain;
    }
}
