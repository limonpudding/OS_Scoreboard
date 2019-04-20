package psu.lp.scoreboard.util;

public enum ScoreboardActionType {
    RESET_HALF("Сбросить тайм и время."),
    TIMER_START("Запустить таймер."),
    TIMER_PAUSE("Поставить таймер на паузу."),
    NEW_HALF("Новый тайм."),
    SET_TEAM_NAMES("Установить имена командам."),
    INCREASE_SCORE("Увеличить счёт.");

    private String info;

    ScoreboardActionType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
