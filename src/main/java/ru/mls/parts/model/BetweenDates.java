package ru.mls.parts.model;

import java.sql.Date;
import java.time.LocalDate;

public class BetweenDates {
    LocalDate from;
    LocalDate to;

    public BetweenDates(LocalDate from, LocalDate to) {

        if (from == null) {
            this.from = LocalDate.parse("1019-01-01");
        } else this.from = from;
        if (to == null) {
            this.to = LocalDate.parse("3019-01-01");
        } else this.to = to;
    }

    public Date getFrom() {
        return Date.valueOf(from);
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public Date getTo() {
        return Date.valueOf(to);
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
