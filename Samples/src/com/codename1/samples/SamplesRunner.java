/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.samples;

import com.codename1.samples.SamplesPanel.Delegate;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author shannah
 */
public class SamplesRunner implements SamplesPanel.Delegate {

    private SamplesContext ctx;
    private SamplesPanel view;
    private String currentSearch;
    
    public static void main(String[] args)throws Exception {
        new SamplesRunner().launch(args);
    }
    /**
     * @param args the command line arguments
     */
    public void launch(String[] args) throws Exception  {
        // TODO code application logic here
        ctx = SamplesContext.createSystemContext();
        SampleList samples = ctx.loadSamples();
        if (currentSearch != null && !currentSearch.isEmpty()) {
            samples = samples.filter(ctx, currentSearch);
        }
        final SampleList fSamples = samples;
        EventQueue.invokeLater(()->{
            view= new SamplesPanel(fSamples);
            view.setDelegate(this);
            JFrame dlg = view.showDialog(null);
            dlg.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
            
        });
        
    }

    @Override
    public void launchSample(Sample sample) {
        new Thread(()->{
            try {
                sample.run(ctx);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Process p = sample.getThreadLocalProcess();
            if (p != null) {
                view.removeProcess(p);
            }
        }).start();
    }
    
    @Override
    public void sendWindowsDesktopBuild(Sample sample) {
        new Thread(()->{
            try {
                sample.sendWindowsDesktopBuild(ctx);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Process p = sample.getThreadLocalProcess();
            if (p != null) {
                view.removeProcess(p);
            }
        }).start();
    }
    
    @Override
    public void launchJSSample(Sample sample) {
        new Thread(()->{
            try {
                sample.runJavascript(ctx, p->{
                    view.addProcess(p, sample.getName());
                });
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Process p = sample.getThreadLocalProcess();
            if (p != null) {
                view.removeProcess(p);
            }
        }).start();
    }

    @Override
    public void createNewSample() {
        String name = JOptionPane.showInputDialog(view, "Enter a sample name", "MySample");
        if (name == null || name.isEmpty()) {
            return;
        }
        try {
            Sample sample = new Sample(name);
            sample.save(ctx, getSampleTemplate(name));
            sample.openJavaSourceFile(ctx);
            ctx = SamplesContext.createSystemContext();
            SampleList samples = ctx.loadSamples();
            view.setSamples(samples);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to save sample: "+ex.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
        
    }
    
    private String getSampleTemplate(String name) throws IOException {
        String tpl = IOUtil.readToString(SamplesRunner.class.getResourceAsStream("MyApplication.txt"));
        tpl = tpl.replace("{{MyApplication}}", name);
        return tpl;
    }

    @Override
    public void viewSource(Sample sample) {
        try {
            sample.openJavaSourceFile(ctx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateSearch() {
        try {
            SwingWorker worker = new SwingWorker() {
                SampleList samples;
                @Override
                protected Object doInBackground() throws Exception {
                    samples = ctx.loadSamples();
                    if (currentSearch != null && !currentSearch.isEmpty()) {
                        //System.out.println("Filtering samples on "+currentSearch);
                        samples = samples.filter(ctx, currentSearch);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    //System.out.println("Updating samples");
                    view.setSamples(samples);
                }
                
                
                
            };
            worker.execute();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void searchChanged(String newSearch) {
        currentSearch = newSearch;
        updateSearch();
    }

    @Override
    public void editGlobalBuildHints() {
        ctx.getConfigDir().mkdirs();
        File configFile = ctx.getGlobalBuildPropertiesFile();
        if (!configFile.exists()) {
            try (FileOutputStream fos = new FileOutputStream(configFile)) {
                Properties props = ctx.getGlobalBuildProperties();
                props.store(fos, "Add your custom codenameone_settings.properties key/value pairs to be used for building samples");
            } catch (Exception ex) {
                ex.printStackTrace();
                
            }
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(configFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void editBuildHints(Sample sample) {
        sample.getConfigDir(ctx).mkdirs();
        
        File configFile = sample.getRunPropertiesFile(ctx);
        if (!configFile.exists()) {
            try (FileOutputStream fos = new FileOutputStream(configFile)) {
                Properties props = sample.getRunProperties(ctx);
                props.store(fos, "Add your custom codenameone_settings.properties key/value pairs to be used for building this sample");
            } catch (Exception ex) {
                ex.printStackTrace();
                
            }
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().edit(configFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void launchIOSDebug(Sample sample) {
        new Thread(()->{
            try {
                sample.buildIOSDebug(ctx);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stopProcess(Process p, String name) {
        int res = JOptionPane.showConfirmDialog(view, "Stop process "+name+"?");
        if (res == JOptionPane.OK_OPTION) {
            try {
                p.destroy();
                JOptionPane.showMessageDialog(view, "Process has been stopped");
                view.removeProcess(p);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    

    
    
}
