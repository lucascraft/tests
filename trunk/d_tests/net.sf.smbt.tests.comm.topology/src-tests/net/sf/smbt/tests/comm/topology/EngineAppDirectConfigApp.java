/***********************************************************************************
 * Smbt - A smart ambient utilities framework 
 * 
 * Copyright (c) 2010, Lucas Bigeardel
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

package net.sf.smbt.tests.comm.topology;

import java.util.UUID;

import net.sf.smbt.ezmotion.MotionValue;
import net.sf.smbt.motion.engine.IMotionReceiver;
import net.sf.smbt.motion.engine.Motion3DUtils;
import net.sf.smbt.motion.engine.MotionEngine;
import net.sf.smbt.osc.spatserver.utils.SpatializationServerUtils;
import net.sf.smbt.osc.utils.OscCmdUtils;
import net.sf.smbt.osccmd.OscCmd;
import net.sf.smbt.quantic.warp.QuanticMojo;
import net.sf.smbt.spatserver.SpatServer;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.engine.Event;
import net.sf.xqz.model.engine.impl.AbstractQxEventHandlerImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.illposed.osc.OSCMessage;

public class EngineAppDirectConfigApp implements IApplication {
	private SpatServer server;
	
	class TimerJob extends AbstractTTLJob {
		public TimerJob() {
			this(AbstractTTLJob.FOREVER);
		}
		public TimerJob(long TTL) {
			super(UUID.randomUUID().toString(), TTL);
		}
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return Status.OK_STATUS;
		}
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		server = SpatializationServerUtils.INSTANCE.initSpatServerModel();
		final CmdPipe engine = QuanticMojo.INSTANCE.openUdpPipe(
			"osc", 
			//"192.168.1.100:5200",
			"localhost:5200",
			5201
		);
		engine.addQxEventHandler(new AbstractQxEventHandlerImpl() {
			@Override
			public void handleQxEvent(Event event) {
				if (event.getCmd() instanceof OscCmd) {
					OscCmd cmd = (OscCmd) event.getCmd();
					SpatializationServerUtils.INSTANCE.handleSpatServerCommand(server, cmd);
					if (Platform.inDebugMode()) {
						System.out.println(((OSCMessage)cmd.getMsg()).getAddress());
					}
				}
			}
		});
		
		server.getEngines().add(engine);
		
		engine.send(
			OscCmdUtils.INSTANCE.createOscSndCmd("/ubqt/bonjour")
		);

		Thread.sleep(250);

		MotionEngine rotateEngine = new MotionEngine(100, Motion3DUtils.INSTANCE.createCyclic2DRotateMotion(10, 2f));
		
		rotateEngine.getMotionReceiver().add(
			new IMotionReceiver() {
				@Override
				public IStatus getKicked(int step, int min, int max, int incr, MotionValue val, long time) {
					System.out.println(val.getX() + ", " + val.getY() + ", " + val.getZ());
					server.setGain(1, new Float(val.getX()));
					return org.eclipse.core.runtime.Status.OK_STATUS;
				}
			}
		);
		rotateEngine.schedule();

		TimerJob tJob = new TimerJob();
		tJob.schedule();
		
		while(tJob.isRunning());
		
		return new Object();
	}

	@Override
	public void stop() {

	}
	
	public void handleCommand(int channel) {
		String txt = "/spatServer/mixerUnit/"+channel+"/gain/input";
	}
}
