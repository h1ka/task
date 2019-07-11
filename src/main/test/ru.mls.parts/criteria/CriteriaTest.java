package ru.mls.parts.criteria;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mls.parts.model.BetweenDates;
import java.time.LocalDate;

@DisplayName("Test criteria")
public class CriteriaTest {


    @Test
    @DisplayName("should create SQL for WHERE")
    public void testCriteria() {
        final Criteria criteria = new Criteria().eq("object_id", "1234");
        assertEquals(" WHERE object_id ILIKE ?", criteria.getWhere().getSql());
        assertEquals("1234", criteria.getWhere().getValues().get(0));
    }

    @Test
    @DisplayName("should create SQL for WHERE")
    public void testDateCriteria(){
        LocalDate to = LocalDate.parse("2019-01-01");
        LocalDate from = LocalDate.parse("2018-01-01");

        BetweenDates betweenDates = new BetweenDates(from,to);
        final Criteria criteria = new Criteria().eq("date",betweenDates);
        assertEquals(" WHERE date BETWEEN ? AND ?",criteria.getWhere().getSql());
    }

    @Test
    public void testOrderByASC(){
        final Criteria criteria = new Criteria().orderBy("object_id", Order.ASC);
        criteria.eq("object_id", "1234");
        assertEquals(" ORDER BY object_id", criteria.getOrderBySql());
        System.out.println(criteria.getWhere().getSql()+criteria.getOrderBySql());
    }


}