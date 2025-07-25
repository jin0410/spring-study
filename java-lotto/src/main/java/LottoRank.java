public enum LottoRank {
    FIRST(6),
    SECOND(7),
    THIRD(5),
    FOURTH(4),
    FIFTH(3),
    NORANK(0);

    private final int matches;

    LottoRank(int number) {
        this.matches = number;
    }

    public int matches() {
        return matches;
    }
}
