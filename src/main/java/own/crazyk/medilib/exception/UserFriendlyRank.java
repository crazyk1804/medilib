package own.crazyk.medilib.exception;

public enum UserFriendlyRank {
    friendly    { @Override public String toString() { return "friendly"; } },
    notReally   { @Override public String toString() { return "not really"; } },
    notAtAll    { @Override public String toString() { return "not at all"; } }
}
