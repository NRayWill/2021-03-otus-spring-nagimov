package ru.otus.spring.rnagimov.studenttestingshell.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class AppProperties {

    private boolean shuffleAnswerOptions;
    private String questionFileName;
    private String locale;
    private int passScore;

    public String getLocalizedFileName() {
        String localizedFileName = this.questionFileName + "_" + locale + ".csv";
        URL u = this.getClass().getResource("/" + localizedFileName);
        if (u == null) {
            return this.questionFileName + ".csv";
        }
        return localizedFileName;
    }
}
