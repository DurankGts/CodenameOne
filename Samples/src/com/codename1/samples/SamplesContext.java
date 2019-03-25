/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.samples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author shannah
 */
public class SamplesContext {

    /**
     * @return the configDir
     */
    public File getConfigDir() {
        return configDir;
    }

    /**
     * @param configDir the configDir to set
     */
    public void setConfigDir(File configDir) {
        this.configDir = configDir;
    }
    
    public File getGlobalBuildPropertiesFile() {
        return new File(configDir, "codenameone_settings.properties");
    }
    
    public Properties getGlobalBuildProperties() throws IOException {
        Properties out = new Properties();
        File buildProps = getGlobalBuildPropertiesFile();
        if (buildProps.exists()) {
            try (FileInputStream fis = new FileInputStream(buildProps)) {
                out.load(fis);
            }
        }
        return out;
    }

    public static SamplesContext createSystemContext() {
        System.out.println(System.getProperties());
        SamplesContext ctx = new SamplesContext();
        return ctx;
    }
    
    /**
     * @return the cldcProjectDir
     */
    public File getCldcProjectDir() {
        return cldcProjectDir;
    }

    /**
     * @param cldcProjectDir the cldcProjectDir to set
     */
    public void setCldcProjectDir(File cldcProjectDir) {
        this.cldcProjectDir = cldcProjectDir;
    }

    /**
     * @return the codenameOneProjectDir
     */
    public File getCodenameOneProjectDir() {
        return codenameOneProjectDir;
    }

    /**
     * @param codenameOneProjectDir the codenameOneProjectDir to set
     */
    public void setCodenameOneProjectDir(File codenameOneProjectDir) {
        this.codenameOneProjectDir = codenameOneProjectDir;
    }

    /**
     * @return the javaSEProjectDir
     */
    public File getJavaSEProjectDir() {
        return javaSEProjectDir;
    }

    /**
     * @param javaSEProjectDir the javaSEProjectDir to set
     */
    public void setJavaSEProjectDir(File javaSEProjectDir) {
        this.javaSEProjectDir = javaSEProjectDir;
    }

    /**
     * @return the ant
     */
    public String getAnt() {
        return ant;
    }

    /**
     * @param ant the ant to set
     */
    public void setAnt(String ant) {
        this.ant = ant;
    }

    /**
     * @return the sampleProjectTemplateDir
     */
    public File getSampleProjectTemplateDir() {
        return sampleProjectTemplateDir;
    }

    /**
     * @param sampleProjectTemplateDir the sampleProjectTemplateDir to set
     */
    public void setSampleProjectTemplateDir(File sampleProjectTemplateDir) {
        this.sampleProjectTemplateDir = sampleProjectTemplateDir;
    }

   
    private static final boolean IS_MAC;
    static {
        String n = System.getProperty("os.name");
        if (n != null && n.startsWith("Mac")) {
            IS_MAC = true;
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Codename One GUIBuilder");
            System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
        } else {
            IS_MAC = false;
        }
        //isWindows = File.separatorChar == '\\';        
    }
    
    Properties props = new Properties(System.getProperties());
    
    /**
     * @return the srcDir
     */
    public File getSrcDir() {
        return srcDir;
    }

    /**
     * @param srcDir the srcDir to set
     */
    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    /**
     * @return the buildDir
     */
    public File getBuildDir() {
        return buildDir;
    }
    
    

    /**
     * @param buildDir the buildDir to set
     */
    public void setBuildDir(File buildDir) {
        this.buildDir = buildDir;
    }

    /**
     * @return the cn1LibsDir
     */
    public File getCn1LibsDir() {
        return cn1LibsDir;
    }

    /**
     * @param cn1LibsDir the cn1LibsDir to set
     */
    public void setCn1LibsDir(File cn1LibsDir) {
        this.cn1LibsDir = cn1LibsDir;
    }
    
    public File getJavaHome() {
        return new File(props.getProperty("java.home"));
    }
    
    public String getExeExtension() {
        return File.separator.equals("\\") ? ".exe" : "";
    }
    
    public File getJavac() {
        return new File(getJavaHome(), "bin" + File.separator + "javac" + getExeExtension());
    }
    
    public File getCn1Lib(String libName) {
        if (!libName.endsWith(".cn1lib")) {
            libName = libName + ".cn1lib";
        }
        return new File(getCn1LibsDir(), libName);
    }
    
    
    
    public File getCodenameOneBuildClientJar() {
        return new File(new File(props.getProperty("user.home"), ".codenameone"), "CodeNameOneBuildClient.jar");
    }
    
    public SampleList loadSamples() throws IOException {
        if (samples == null) {
            samples = new SampleList();
            
            for (File sampleDir : getSrcDir().listFiles()) {
                samples.add(new Sample(sampleDir.getName()));
            }
        }
        return samples;
    }
    
    
    
    private File srcDir=new File("samples");
    private File buildDir=new File("build");
    private File cn1LibsDir=new File("../../CodenameOneLibs");

    private File sampleProjectTemplateDir=new File("SampleProjectTemplate");
    private File configDir = new File("config");
    
    private String ant="ant";
    private File cldcProjectDir=new File("../Ports/CLDC11");
    private File codenameOneProjectDir=new File("../CodenameOne");
    private File javaSEProjectDir=new File("../Ports/JavaSE");
    private SampleList samples;
    
    
    
    
    
}
