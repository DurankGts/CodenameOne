package com.codename1.samples;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.util.EasyThread;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class SynchronizedParparVMTest {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Synchronized Test", BoxLayout.y());
        Button runTest = new Button("Run Test");
        runTest.addActionListener(evt->{
            new SynchronizedTest().run();
        });
        hi.add(runTest);
        hi.show();
        emptySynchronizedMethod();
        
        
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }
    
    private synchronized void emptySynchronizedMethod() {
        
    }
    
    

}

class SynchronizedTest 
{
    public void run()
    {
        EasyThread thread = EasyThread.start("Thread1");
        
        thread.run(() -> runTest1());
        runTest1();
        
        Log.p("TEST 1 COMPLETE");
        
        thread.run(() -> runTest2());
        runTest2();
        Log.p("TEST 2 COMPLETE");
        thread.run(() -> runTest3());
        runTest3();
        Log.p("TEST 3 COMPLETE");
        thread.run(() -> runTest4());
        runTest4();
        Log.p("TEST 4 COMPLETE");
        thread.run(() -> runTest5());
        runTest5();
        Log.p("TEST 5 COMPLETE");
        
        
        
        
        
    }
    
    private void runTest1()
    {
        try
        {
            test1();
        }
        catch (Exception e)
        {
            Log.p("EXCEPTION CAUGHT");
        }
    }
    
    private void runTest2()
    {
        try
        {
            test2();
        }
        catch (Exception e)
        {
            Log.p("EXCEPTION CAUGHT");
        }
    }
    
    private void runTest3()
    {
        try
        {
            test3();
        }
        catch (Exception e)
        {
            Log.p("EXCEPTION CAUGHT");
        }
    }
    
    private void runTest4() {
        test4();
    }
    
    private void runTest5() {
        test5();
    }
    
    private void test1() throws Exception
    {
        synchronized(this)
        {
            throw new Exception();
        }
    }
    
    private synchronized void test2() throws Exception
    {
        throw new Exception();
    }
    
    private void test3() throws Exception {
        throw new Exception();
    }
    
    private void test4() {
        try {
            throw new Exception();
        } catch (Exception ex) {
            Log.p("EXCEPTION CAUGHT");
        }
    }
    
    private void test5() {
        int finallyCount = 0;
        try {
            try {
                try {
                    throwRuntimeException();
                } catch (Exception ex) {
                    Log.p("RUNTIME EXCEPTION CAUGHT IN EXCEPTION BLOCK");
                } finally {
                    finallyCount++;
                }
            }  catch (RuntimeException ex) {
                Log.p("RUNTIME EXCEPTION CAUGHT TEST 5");
                throw new Exception();
            } finally {
                finallyCount++;
            }
        } catch (Exception ex) {
            Log.p("EXCEPTION CAUGHT TEST 5");
        } finally {
            finallyCount++;
        }
        if (finallyCount != 3) {
            throw new RuntimeException("Wrong finally count in test 5.  Should be 3 but was "+finallyCount);
        }
    }
    
    private void throwRuntimeException() {
        throw new RuntimeException();
    }
    
    private void throwException() throws Exception {
        throw new Exception();
    }
    
    
    
}



