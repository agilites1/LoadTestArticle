package com.erpnext.Base

import groovy.json.JsonSlurper
import groovy.text.GStringTemplateEngine
import org.apache.commons.io.IOUtils
import org.codehaus.groovy.runtime.StackTraceUtils
import org.openqa.selenium.JavascriptExecutor
import ru.yandex.qatools.allure.annotations.Attachment

import static com.codeborne.selenide.WebDriverRunner.getWebDriver
import static com.erpnext.Base.AllureAttach.saveHtmlAttach
import static com.erpnext.Base.AllureAttach.saveHtmlAttachResult

/**
 * Created by artyom on 3/28/2017.
 */
class TimingAPI implements BaseClass {
    static def stepsTime = []
    static def preStepTime = []

    static def getPrePageLoadTime() {
        String pageLoadTimingJson = ((JavascriptExecutor) getWebDriver())
                .executeScript("return JSON.stringify(performance.getEntries());")
                .toString().replace("\"", "'")
        preStepTime << new JsonSlurper().parseText(pageLoadTimingJson.replace("'", '"'))
    }

    static def getPageLoadTime() {
        def odds = { listLeft, listRight ->
            def result = listLeft
            listRight.each {
                if (result.startTime.contains(it.startTime) && result.name.contains(it.name))
                    result.remove(it)
                else
                    result.add(it)

            }
            result
        }
        String pageLoadTimingJson = ((JavascriptExecutor) getWebDriver())
                .executeScript("return JSON.stringify(performance.getEntries());")
                .toString().replace("\"", "'")
        def parsed = new JsonSlurper().parseText(pageLoadTimingJson.replace("'", '"'))
        def tmp = preStepTime.empty ? parsed : odds(parsed, preStepTime.last())
        def loadTime = tmp.responseEnd.max() - tmp.startTime.min()
        String callMethodName = StackTraceUtils.sanitize(new Throwable()).stackTrace[2].methodName as String
        def timeObj = [:]
        timeObj << [stepName: normalizeText(callMethodName), timing: Integer.valueOf(loadTime.intValue()), model: tmp]
        stepsTime.push(timeObj)
        def binding = [
                topics: parsed
        ]
        def templateFile = IOUtils.toString(TimingAPI.getClassLoader().getResourceAsStream('Table.template'))
        def template = new GStringTemplateEngine().createTemplate(templateFile).make(binding)
        saveHtmlAttach(Integer.valueOf(loadTime.intValue()), template.toString())
    }

    static def getAllTiming() {
        def binding = [
                topics: stepsTime
        ]
        def templateFile = IOUtils.toString(TimingAPI.getClassLoader()
                .getResourceAsStream('AllPagesGraph.template'))
        def template = new GStringTemplateEngine().createTemplate(templateFile).make(binding)

        saveHtmlAttachResult(0, template.toString())
    }
}

class AllureAttach {
    @Attachment(value = "Page load time {0} ms", type = "text/html")
    public static byte[] saveHtmlAttach(Long pageLoadTime, String attachName) {
        return attachName.getBytes()
    }

    @Attachment(value = "Result graph", type = "text/html")
    public static byte[] saveHtmlAttachResult(Long pageLoadTime, String attachName) {
        return attachName.getBytes()
    }
}