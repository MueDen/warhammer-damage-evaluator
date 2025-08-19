# Warhammer 40k damage calculator
## Introduction
This project aims to provide a fast and intuitive way to evaluate the damage profiles of 
Warhammer 40,000 units. I created this tool to address the challenge of choosing the optimal damage 
profile when attacking tougher units, especially when navigating complex modifiers like Lethal Hits, 
Sustained Hits, Devastating Wounds, rerolls, and bonuses to hit or wound rolls. As a newcomer 
to Warhammer 40K, I found these interactions overwhelming, and this project was born out of a 
desire to better understand the game's mechanics. While seasoned players may already be familiar 
with these nuances, this tool serves as both a practical utility and a learning experience for 
those still mastering the rules.
Please note that this project is a work in progress. The current version represents an initial implementation of the core rules and some common modifiers, with room for further development and refinement.
## How to use
### Example Unit - Roboute Guilliman
All of our specific Units extend the ```Model``` class. We use two different Constructors, the default Constructor 
which is used if we only want to create the class with its stats or if we don't need rerolls and plus hits or plus wounds. It initializes the class with the values:
```java
    public RobouteGuilliman()
    {
        super(9,2,10, "Roboute Guilliman", false,false,false,false, 4, 7, getAttackModifications());
    }
```
which then calls the super constructor:
```java
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
```
To create the attack profiles with the different modifiers we can use the ```java getAttackModifications()``` Method.
For Roboute this method looks like this:
```java
    private static List<Profile> getAttackModifications()
    {
        final Profile handOfDominion = new Profile("Hand of Dominion ranged", List.of(AttackProfileModificationEnum.RAPID_FIRE_2), 2,2,6,2,2);
        final Profile emperorsSword = new Profile("Emperor’s Sword", List.of(AttackProfileModificationEnum.DEVASTATING_WOUNDS), 14,2,8,3,2);
        final Profile handOfDominionMelee = new Profile("Hand of Dominion Melee", List.of(AttackProfileModificationEnum.LETHAL_HITS), 7,2,14,4,4);
        return List.of(handOfDominion, handOfDominionMelee, emperorsSword);
    }
```



