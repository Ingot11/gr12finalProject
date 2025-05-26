public enum Nature{
    HARDY, DOCILE, BASHFUL, QUIRKY, SERIOUS, // No boosting stat
    // Attack Boosting
    LONELY{ @Override public double boost(String stat){return calc(stat, "atk", "def");} },
    ADAMANT{ @Override public double boost(String stat){return calc(stat, "atk", "spAtk");} },
    NAUGHTY{ @Override public double boost(String stat){return calc(stat, "atk", "spDef");} },
    BRAVE{ @Override public double boost(String stat){return calc(stat, "atk", "spd");} },
    // Defence Boosting
    BOLD{ @Override public double boost(String stat){return calc(stat, "def", "atk");} },
    IMPISH{ @Override public double boost(String stat){return calc(stat, "def", "spAtk");} },
    LAX{ @Override public double boost(String stat){return calc(stat, "def", "spDef");} },
    RELAXED{ @Override public double boost(String stat){return calc(stat, "def", "spd");} },
    // Special Attack Boosting
    MODEST{ @Override public double boost(String stat){return calc(stat, "spAtk", "atk");} },
    MILD{ @Override public double boost(String stat){return calc(stat, "spAtk", "def");} },
    RASH{ @Override public double boost(String stat){return calc(stat, "spAtk", "spDef");} },
    QUIET{ @Override public double boost(String stat){return calc(stat, "spAtk", "spd");} },
    // Special Defence Boosting
    CALM{ @Override public double boost(String stat){return calc(stat, "spDef", "atk");} },
    GENTLE{ @Override public double boost(String stat){return calc(stat, "spDef", "def");} },
    CAREFUL{ @Override public double boost(String stat){return calc(stat, "spDef", "spAtk");} },
    SASSY{ @Override public double boost(String stat){return calc(stat, "spDef", "spd");} },
    // Speed Boosting
    TIMID{ @Override public double boost(String stat){return calc(stat, "spd", "atk");} },
    HASTY{ @Override public double boost(String stat){return calc(stat, "spd", "def");} },
    JOLLY{ @Override public double boost(String stat){return calc(stat, "spd", "spAtk");} },
    NAIVE{ @Override public double boost(String stat){return calc(stat, "spd", "spDef");} };
    // Calculations
    public double boost(String stat){return 1;}
    public double calc(String stat, String buff, String nerf){ return buff.equals(nerf)? 1 : stat.equals(buff) ? 1.1 : stat.equals(nerf) ? 0.9 : 1;};
}