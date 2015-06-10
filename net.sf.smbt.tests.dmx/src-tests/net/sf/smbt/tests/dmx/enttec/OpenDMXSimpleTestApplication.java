/***********************************************************************************
 * Smbt - A smart ambient utilities framework 
 * 
 * Copyright (c) 2011, Lucas Bigeardel
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

package net.sf.smbt.tests.dmx.enttec;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/*
class AutoPlaySceneJob extends AbstractTTLJob {
	CmdPipe engine;
	Random randomizer;
	public AutoPlaySceneJob(CmdPipe _engine) {
		super(UUID.randomUUID().toString(), 60000L);
		engine = _engine;
		randomizer = new Random();
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		engine.send(OpenDmxCmdUtils.INSTANCE.createOpenDMXFadeRGBCmd(0, randomizer.nextInt(255), randomizer.nextInt(255), randomizer.nextInt(255)));
		if (Platform.inDebugMode()) {
			System.out.println("Sending serial req command to Enttec serial : " + engine.getAddr());
		}
		schedule(randomizer.nextInt(1000));
		return Status.OK_STATUS;
	}
}
*/

public class OpenDMXSimpleTestApplication implements IApplication {
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
/*
		final CmdPipe dmxUsbSerialPipe = QuanticMojo.INSTANCE.openUsbPipe("dmx", "/dev/tty.usbserial-EN084592", 115200);
		if (dmxUsbSerialPipe != null) {
			dmxUsbSerialPipe.addQxEventHandler(
				new AbstractQxEventHandlerImpl() {
					public void handleQxEvent(net.sf.xqz.model.engine.Event event) {
						if (event.getKind().equals(EVENT_KIND.RX_DONE)) {
							handleDMXRcvCmd(null, event.getCmd());
						}
					}
				}
			);
		}
		
		AbstractTTLJob job = new AutoPlaySceneJob(dmxUsbSerialPipe);
		job.schedule();
		
		while (job.isRunning())
			;
*/
		return new Object();
	}

/*	
	private void handleDMXRcvCmd(CmdPipe abletonEngine, Cmd cmd) {
		if (cmd instanceof OpenDMXCmd) {
			OpenDMXCmd dmxCmd = (OpenDMXCmd) cmd;
			System.out.println(cmd.eClass().getName() + " " + dmxCmd.getLabel() );
		}
	}
*/
	
	@Override
	public void stop() {
	}
}
