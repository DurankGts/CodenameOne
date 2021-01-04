package com.codename1.samples;


import com.codename1.components.ImageViewer;
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
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class ImageViewerTest2679 {

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
        Form f = new Form(new LayeredLayout());
        
        f.setScrollableY(false);
        f.setScrollableX(false);
        f.getContentPane().setScrollableY(false);        
        f.getContentPane().setScrollableX(false);

        
        ImageViewer viewer = new ImageViewer();
        viewer.setAllowScaleDown(true);
        viewer.setImageInitialPosition(ImageViewer.IMAGE_FILL);

        ListModel images = new DefaultListModel();
        final int w = 724;
        final int h = 1024;
        for(int i=0; i < 10; i++) {
            Image placeholder = Image.createImage(w, h);
            Image img = URLImage.createCachedImage("experimentImage", "https://media.istockphoto.com/vectors/decorative-black-rectangular-frame-for-label-certificate-card-a3-a4-vector-id1039615844", placeholder, URLImage.FLAG_RESIZE_SCALE);
            images.addItem(img);
        }
        
        f.addComponent(viewer);
        viewer.setImageList(images);

        f.show();

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

}