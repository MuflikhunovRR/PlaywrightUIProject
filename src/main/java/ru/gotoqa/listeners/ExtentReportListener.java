package ru.gotoqa.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static ru.gotoqa.factory.PlaywrightFactory.takeScreenshot;

/**
 * ExtentReportListener class is used to generate the Extent Report
 */
public class ExtentReportListener implements ITestListener {
    protected static final Logger LOGGER = LogManager.getLogger(ExtentReportListener.class);

    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";

    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<ExtentTest>();
    private static ExtentReports extentReports;


    private static ExtentReports init() {

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Automation GotoQA Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "macOS 12.5 (21G72)");
        extentReports.setSystemInfo("Author", "Roman Muflikhunov");
        extentReports.setSystemInfo("Build#", "1.0");
        extentReports.setSystemInfo("Team", "Driving Quality, Delivering Excellence!");
        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        LOGGER.info("Test Suite started!");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        LOGGER.info("Test Suite is ending!");
        extent.flush();
        testThreadLocal.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        LOGGER.info(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        testThreadLocal.set(extentTest);
        testThreadLocal.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public synchronized void onTestSuccess(ITestResult result) {
        LOGGER.info(result.getMethod().getMethodName() + " passed!");
        testThreadLocal.get().pass("Test passed");
        testThreadLocal.get().pass(result.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),
                        result.getMethod().getMethodName()).build());
        testThreadLocal.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        LOGGER.info(result.getMethod().getMethodName() + " failed!");
        testThreadLocal.get().fail(result.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),
                        result.getMethod().getMethodName()).build());
        testThreadLocal.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {
        LOGGER.info(result.getMethod().getMethodName() + " skipped!");
        testThreadLocal.get().skip(result.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(),
                        result.getMethod().getMethodName()).build());
        testThreadLocal.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LOGGER.info("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }

    private Date getTime(long millis) {
        return new Date(millis);
    }

}