package ru.otus.spring.rnagimov.studenttestingshell.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;
import ru.otus.spring.rnagimov.studenttestingshell.service.ExamineService;
import ru.otus.spring.rnagimov.studenttestingshell.service.LocalizedIoService;

@ShellComponent
@SuppressWarnings("unused")
public class TestCommands {

    private final ExamineService examineService;
    private final LocalizedIoService localizedIoService;
    private final AppProperties appProperties;

    @Autowired
    public TestCommands(ExamineService examineService, LocalizedIoService localizedIoService, AppProperties appProperties) {
        this.examineService = examineService;
        this.localizedIoService = localizedIoService;
        this.appProperties = appProperties;
    }

    @ShellMethod(value = "Start examine", key = {"startExamine", "start", "s"})
    public void startExamine() {
        examineService.examine();
    }

    @ShellMethod(value = "About", key = "about")
    public void about(){
        localizedIoService.printLocalizedMessage("messages.about");
    }

    @ShellMethod(value = "Change locale", key = {"changeLocale", "chl"})
    public void changeLocale(@ShellOption(defaultValue = "") String newLocale){
        String prevLocale = appProperties.getLocale();
        appProperties.setLocale(newLocale);
        localizedIoService.printLocalizedMessage("messages.changed.locale", prevLocale, newLocale);
    }

    @ShellMethod(value = "Get locale", key = {"getLocale", "locale", "loc", "l"})
    public void getLocale(){
        String locale = appProperties.getLocale();
        System.out.println(locale);
    }
}
