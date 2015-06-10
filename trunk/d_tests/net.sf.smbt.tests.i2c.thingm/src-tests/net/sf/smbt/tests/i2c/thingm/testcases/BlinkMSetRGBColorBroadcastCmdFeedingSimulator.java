/***********************************************************************************
 * Smbt - A smart ambient utilities framework 
 * 
 * Copyright (c) 2008 - 2010, Lucas Bigeardel
 * 
 * The library is released under:
 * 
 * A) LGPL
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 * 
 * B) EPL
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lucas Bigeardel <lucas.bigeardel@gmail.com> - Initial API and implementation
 ***********************************************************************************/

package net.sf.smbt.tests.i2c.thingm.testcases;

import java.util.Random;

import net.sf.smbt.blinkm.cmd.injection.BlinkMUtil;
import net.sf.xqz.engine.utils.AbstractTTLCmdFeedingSimulatorJob;
import net.sf.xqz.model.cmd.Cmd;
import net.sf.xqz.model.engine.CmdPipe;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class BlinkMSetRGBColorBroadcastCmdFeedingSimulator extends
		AbstractTTLCmdFeedingSimulatorJob {
	Random randomizer;

	public BlinkMSetRGBColorBroadcastCmdFeedingSimulator(CmdPipe engine, long TTL) {
		super(engine, TTL);
		randomizer = new Random();
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Cmd cmd = BlinkMUtil.INSTANCE.createStopScriptCmd("0x00");
		cmdEngine.getTx().postCommand(cmd);

		cmd = BlinkMUtil.INSTANCE.createFadeToRGBColorCmd("0x00", randomizer.nextInt(255), randomizer.nextInt(255), randomizer.nextInt(255));
		cmdEngine.getTx().postCommand(cmd);
		
		schedule(5000);
		return Status.OK_STATUS;
	}

}
