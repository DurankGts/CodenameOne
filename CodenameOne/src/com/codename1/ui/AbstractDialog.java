/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 */
package com.codename1.ui;

import com.codename1.ui.animations.Transition;

/// Shared dialog contract for `Dialog` and `InteractionDialog`.
public interface AbstractDialog {
    /// Adds a component to the dialog using layout constraints.
    void addComponent(Object constraints, Component cmp);
    /// Sets whether dialog content should be scrollable.
    void setScrollable(boolean scrollable);
    /// Sets a dialog sound type (e.g. `Dialog#TYPE_INFO`).
    void setDialogType(int dialogType);
    /// Sets in/out transition for dialogs that support transitions.
    void setTransitions(Transition transition);
    /// Configures commands for the dialog UI.
    void configureCommands(Command[] cmds, boolean commandsAsButtons);
    /// Sets the default command when supported.
    void setDefaultCommand(Command defaultCommand);
    /// Sets timeout in milliseconds after which dialog should close.
    void setTimeout(long timeout);
    /// Disposes the dialog.
    void dispose();
    /// Shows the dialog and returns the selected command if applicable.
    Command showDialog();
}
