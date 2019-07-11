package ru.mls.parts.helper;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class TemplateProcessor {
    private static final String HTML_DIR = "/view";

    private final Configuration configuration;

    private static TemplateProcessor instance = new TemplateProcessor();

    public static TemplateProcessor instance() {
        return instance;
    }

    private TemplateProcessor()  {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setEncoding(Locale.ROOT,"UTF-8");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(),HTML_DIR);
    }
    public String getPage(String filename, Map<String,Object> varMap) throws IOException {
        try(Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(varMap,stream);
            return stream.toString();
        }catch (Exception e){
            throw new IOException();
        }
    }

}
