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

package net.sf.smbt.tests.osc.modul8;

import java.util.Random;
import java.util.UUID;

import net.sf.smbt.osc.wiimote.model.wiimote.WiiAccelPryCmd;
import net.sf.smbt.quantic.warp.QuanticMojo;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.model.cmd.Cmd;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.engine.EVENT_KIND;
import net.sf.xqz.model.engine.impl.AbstractQxEventHandlerImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;


/*class AutoPlaySceneJob extends AbstractTTLJob {
	CmdEngine engine;
	Random randomizer;
	int nbScene;
	public AutoPlaySceneJob(CmdEngine _engine, int _nbScene) {
		super(UUID.randomUUID().toString());
		nbScene = _nbScene;
		engine = _engine;
		randomizer = new Random();
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		engine.send(AbletonLiveUtils.INSTANCE.createLivePlayScene(randomizer.nextInt(nbScene)));
		schedule(10000);
		return Status.OK_STATUS;
	}
}*/


class AutoPlaySceneJob extends AbstractTTLJob {
	CmdPipe engine;
	Random randomizer;
	int nbScene;
	public AutoPlaySceneJob(CmdPipe _engine, int _nbScene) {
		super(UUID.randomUUID().toString());
		nbScene = _nbScene;
		engine = _engine;
		randomizer = new Random();
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		//engine.send(AbletonLiveUtils.INSTANCE.createLivePlayScene(randomizer.nextInt(nbScene)));
		schedule(10000);
		return Status.OK_STATUS;
	}
}
public class Modul8ClientGUIControlApplication implements IApplication {
	
//	private Modul8App liveApp;
	
//	public Modul8ClientGUIControlApplication() {
//		liveApp = Md8SessionUtils.INSTANCE.createDefaultModul8App();
//	}

//	private void handleAbletonLiveRcvCmd(Cmd cmd, CmdEngine oscMd8Engine) {
//		Md8SessionUtils.INSTANCE.handleModul8Synchro(liveApp, cmd);
//	}
	
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
//		final CmdEngine oscModul8Engine = WARP.INSTANCE.openUdpPipe("oscmd8", "modul8_V261_1105", "localhost:8002", 7777);
//		oscModul8Engine.addQxEventHandler(
//			new AbstractQxEventHandlerImpl() {
//				public void handleQxEvent(net.sf.xqz.model.engine.Event event) {
//					if (event.getKind().equals(EVENT_KIND.RX_CMD_REMOVED)) {
//						handleAbletonLiveRcvCmd(event.getCmd(), oscModul8Engine);
//					}
//				}
//			}
//		);
//		liveApp.setEngine(oscModul8Engine);
		
//		final CmdEngine tuioEngine = WARP.INSTANCE.openUdpPipe("tuio1", "tuioSimulator", "localhost:3334", new int[] { 3333 });
//		tuioEngine.addQxEventHandler(
//			new AbstractQxEventHandlerImpl() {
//				public void handleQxEvent(net.sf.xqz.model.engine.Event event) {
//					if (event.getKind().equals(EVENT_KIND.RX_CMD_REMOVED)) {
//						handleTuioRcvCmd(oscModul8Engine, event.getCmd());
//					}
//				}
//			}
//		);
//
		final CmdPipe oscWiimoteEngine = QuanticMojo.INSTANCE.openUdpPipe("oscwiimote", "oscWiimote0", "", new int[] { 1234 });
		oscWiimoteEngine.addQxEventHandler(
			new AbstractQxEventHandlerImpl() {
				public void handleQxEvent(net.sf.xqz.model.engine.Event event) {
					if (event.getKind().equals(EVENT_KIND.RX_DONE)) {
						handleWiimoteRcvCmd(null, event.getCmd());
					}
				}
			}
		);
		
		AbstractTTLJob job = new AutoPlaySceneJob(null/*oscModul8Engine*/, 5);
		job.schedule();
		
		while (job.isRunning())
			;

		return new Object();
	}
	
/*
	private void handleTuioRcvCmd(CmdPipe abletonEngine, Cmd cmd) {
		if (cmd instanceof CompoundCmd) {
			for (Cmd c : ((CompoundCmd)cmd).getChildren()) {
				handleTuioRcvCmd(abletonEngine, c);
			}
		}
		System.out.println("TUIO !!!!!");
		if (cmd instanceof Tuio2DCur) {
			
		} else if (cmd instanceof Tuio2DObj) {
			Tuio2DObj tuio2dObj = (Tuio2DObj) cmd;
			int fid = tuio2dObj.getF_id();
			switch(fid) {
				case 0:
					abletonEngine.send(AbletonLiveUtils.INSTANCE.createLiveMasterVolume(0, tuio2dObj.getAngle()));
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				default:
					break;
			}
		}
	}
*/
	private void handleWiimoteRcvCmd(CmdPipe abletonEngine, Cmd cmd) {
		if (cmd instanceof WiiAccelPryCmd) {
			WiiAccelPryCmd pryCmd = (WiiAccelPryCmd) cmd;
			System.out.println(cmd.eClass().getName() + " " + pryCmd.getAccel() );
	//		abletonEngine.send(AbletonLiveUtils.INSTANCE.createLiveVolumeSet(0, pryCmd.getYaw()));
		}
	}

	@Override
	public void stop() {
	}
}
