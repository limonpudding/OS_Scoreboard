package psu.lp.scoreboard.util;

public enum ScoreboardActionType {
    RESET_SCORETABLE("Сбросить табло."),
    RESET_TIME("Сбросить время."),
    TIMER_START("Запустить таймер."),
    TIMER_PAUSE("Поставить таймер на паузу."),
    TIMER_STOP("Остановить таймер."),
    SET_TEAM_NAME("Установить имя команды."),
    INCREASE_SCORE("Увеличить счёт.");

    private String info;

    ScoreboardActionType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
