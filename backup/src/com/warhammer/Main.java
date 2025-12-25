package com.warhammer;

import com.warhammer.units.tyrannids.Norn;
import com.warhammer.units.ultraMarines.RobouteGuilliman;
import com.warhammer.units.tyrannids.Tyrannofex;

public class Main {

    public static void main(String[] args) {
        final RobouteGuilliman robouteGuilliman = new RobouteGuilliman(true, false, false, true);
        final Tyrannofex tyrannofex = new Tyrannofex();
        final Norn norn = new Norn();

        robouteGuilliman.attackTarget(norn);


    }
}
