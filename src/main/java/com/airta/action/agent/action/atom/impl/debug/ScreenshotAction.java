package com.airta.action.agent.action.atom.impl.debug;

import com.airta.action.agent.action.atom.AbstractDebugAction;
import com.airta.action.agent.action.raw.RawAction;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotAction extends AbstractDebugAction {

    public ScreenshotAction(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void exec(String key, RawAction rawAction) {
        logger.info("## Take screenshot for action: {}", rawAction.getId());
        TakesScreenshot scrShot =((TakesScreenshot)webDriver);
        File scrShotScreenshotAs=scrShot.getScreenshotAs(OutputType.FILE);

        String destinationFilePath = buildDestinationPath(rawAction);
        if(StringUtils.isEmpty(destinationFilePath)) {
            logger.warn("## skip screenshot action for resource issue.");
            return;
        }
        File destFile=new File(destinationFilePath);

        //Copy file at destination
        try {
            logger.info("copy cached screenshot {} to share folder.", destinationFilePath);
            FileUtils.copyFile(scrShotScreenshotAs, destFile);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private String buildDestinationPath(RawAction rawAction) {

        if(!new File(debugRecordFolder).exists()) {
            logger.warn("The record share folder not exist, maybe you need to manually create the directory.");
            return "";
        }
        String finalPath = debugRecordFolder + "/" + rawAction.getId();
        File finalPathFolder = new File(finalPath);
        if(!finalPathFolder.exists()) {
            finalPathFolder.mkdirs();
        }
        finalPath += rawAction.getId()+"_"+System.currentTimeMillis()+".png";

        return finalPath;
    }
}