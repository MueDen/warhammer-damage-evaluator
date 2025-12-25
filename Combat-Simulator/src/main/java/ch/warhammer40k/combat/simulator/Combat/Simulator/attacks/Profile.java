package ch.warhammer40k.combat.simulator.Combat.Simulator.attacks;

import ch.warhammer40k.combat.simulator.Combat.Simulator.modifiers.AttackProfileModificationEnum;

import java.util.List;

public class Profile {
    private final String name;
    private final List<AttackProfileModificationEnum> attackProfileModificationEnumList;
    private final int attacks;
    private final int skill;
    private final int strength;
    private final int ap;
    private final int damage;

    public Profile(String name, List<AttackProfileModificationEnum> attackProfileModificationEnumList, int attacks, int skill, int strength, int ap, int damage) {
        this.name = name;
        this.attackProfileModificationEnumList = attackProfileModificationEnumList;
        this.attacks = attacks;
        this.skill = skill;
        this.strength = strength;
        this.ap = ap;
        this.damage = damage;
    }

    public int getAttacks() {
        return attacks;
    }

    public int getSkill() {
        return skill;
    }

    public int getStrength() {
        return strength;
    }

    public int getAp() {
        return ap;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public List<AttackProfileModificationEnum> getAttackProfileModificationEnumList() {
        return attackProfileModificationEnumList;
    }
}
