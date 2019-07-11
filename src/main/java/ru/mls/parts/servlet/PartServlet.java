package ru.mls.parts.servlet;

import ru.mls.parts.dbservice.DBService;
import ru.mls.parts.dbservice.DBServiceJDBC;
import ru.mls.parts.executor.DataSourceH2;
import ru.mls.parts.helper.TemplateProcessor;
import ru.mls.parts.model.BetweenDates;
import ru.mls.parts.model.Params;
import ru.mls.parts.model.Part;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class PartServlet extends HttpServlet {
    DBService<Part> dbService;
    DataSource dataSource;

    public PartServlet() {
        dataSource = new DataSourceH2();
        this.dbService = new DBServiceJDBC<>(dataSource);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Params params = checkFilterParams(request);
        String page = params.isEmpty() ? getPage(loadParts()) : getPage(loadParts(params));
        response.getWriter().println(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    private Map<String, Object> loadParts() {
        Map<String, Object> varMap = new HashMap<>();
        List<Part> parts = dbService.loadAll("parts", Part.class);
        varMap.put("parts", parts);
        return varMap;
    }

    private Map<String, Object> loadParts(Params params) {
        Map<String, Object> varMap = new HashMap<>();
        List<Part> parts = dbService.loadAll("parts", params, Part.class);
        varMap.put("parts", parts);
        return varMap;
    }

    private String getPage(Map<String, Object> varMap) throws IOException {
        return TemplateProcessor.instance().getPage("parts.ftl", varMap);
    }

    private Params checkFilterParams(HttpServletRequest request) {

        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String vendor = request.getParameter("vendor");
        String qty = request.getParameter("qty");
        String snippedStart = request.getParameter("snippedStart");
        String snippedEnd = request.getParameter("snippedEnd");
        String receiveStart = request.getParameter("receiveStart");
        String receiveEnd = request.getParameter("receiveEnd");

        Params params = new Params();

        Integer qtyValue = getParamForInt(qty);
        LocalDate snippedStartValue = getParamForDate(snippedStart);
        LocalDate receiveStartValue = getParamForDate(receiveStart);
        LocalDate snippedEndValue = getParamForDate(snippedEnd);
        LocalDate receiveEndValue = getParamForDate(receiveEnd);

        BetweenDates snippedDate = createBetweenDates(snippedStartValue, snippedEndValue);
        BetweenDates receiveDate = createBetweenDates(receiveStartValue, receiveEndValue);

        params.addParam("name", name);
        params.addParam("number", number);
        params.addParam("vendor", vendor);
        params.addParam("qty", qtyValue);
        params.addParam("snipped", snippedDate);
        params.addParam("receive", receiveDate);

        return params.createParamsWithoutEmptyValues();
    }

    private Integer getParamForInt(String str) {
        Integer value;
        try {
            if (!(str == null || str.equals(""))){
                value = Integer.parseInt(str);
            }
            else value = null;
        } catch (Exception e){
            value=null;
        }
        return value;
    }

    private LocalDate getParamForDate(String stringDate) {
        LocalDate date = null;
        try {
            if (!(stringDate == null || stringDate.equals(""))) {
                date = LocalDate.parse(stringDate);
            }
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    private BetweenDates createBetweenDates(LocalDate from, LocalDate to) {
        if (!(from == null && to == null)) {
            return new BetweenDates(from, to);
        } else return null;
    }
}

