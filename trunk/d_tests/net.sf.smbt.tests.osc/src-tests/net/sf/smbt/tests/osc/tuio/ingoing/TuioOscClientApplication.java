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

package net.sf.smbt.tests.osc.tuio.ingoing;

import net.sf.smbt.osc.eztuio.Tuio2DCur;
import net.sf.smbt.osc.eztuio.Tuio2DObj;
import net.sf.smbt.quantic.warp.QuanticMojo;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.model.cmd.Cmd;
import net.sf.xqz.model.cmd.CompoundCmd;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.engine.EVENT_KIND;
import net.sf.xqz.model.engine.Event;
import net.sf.xqz.model.engine.impl.AbstractQxEventHandlerImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class TuioOscClientApplication implements IApplication {
	@Override
	public Object start(IApplicationContext context) throws Exception {
		CmdPipe tuioEngine = QuanticMojo.INSTANCE.openUdpPipe("tuio1", 3333);
		
		tuioEngine.addQxEventHandler(
			new AbstractQxEventHandlerImpl() {
				@Override
				public void handleQxEvent(Event event) {
					if (event.getKind().equals(EVENT_KIND.RX_READY)) {
						handleCommand(event.getCmd());
					}
				}
			}
		);
		
		AbstractTTLJob job = new AbstractTTLJob(360000) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
		while (job.isRunning())
			;
		return null;
	}
	
	private void handleCommand(Cmd cmd) {
		if (cmd instanceof CompoundCmd) {
			for (Cmd c : ((CompoundCmd)cmd).getChildren()) {
				handleCommand(c);
			}
		} else {
			if (cmd instanceof Tuio2DObj) {
				System.out.println( 
					"s_id    : " + ((Tuio2DObj)cmd).getS_id() + ", \n" +
					"fid_id  : " + ((Tuio2DObj)cmd).getF_id() + ", \n" +
					"angle   : " + ((Tuio2DObj)cmd).getAngle() + ", \n" +
					"m_accel : " + ((Tuio2DObj)cmd).getMaccel() + ", \n" +
					"r_accel : " + ((Tuio2DObj)cmd).getRaccel() + ", \n" +
					"r_speed : " + ((Tuio2DObj)cmd).getRspeed() + ", \n" +
					"x_pos   : " + ((Tuio2DObj)cmd).getXpos() + ", \n" +
					"y_pos   : " + ((Tuio2DObj)cmd).getYpos() + ", \n" +
					"x_speed : " + ((Tuio2DObj)cmd).getXspeed() + ", \n" +
					"y_speed : " + ((Tuio2DObj)cmd).getXspeed()
				);
			} else if (cmd instanceof Tuio2DCur) {
				System.out.println( 
					"s_id    : " + ((Tuio2DCur)cmd).getS_id() + ", \n" +
					"m_accel : " + ((Tuio2DCur)cmd).getMaccel() + ", \n" +
					"x_pos   : " + ((Tuio2DCur)cmd).getXpos() + ", \n" +
					"y_pos   : " + ((Tuio2DCur)cmd).getYpos() + ", \n" +
					"x_speed : " + ((Tuio2DCur)cmd).getXspeed() + ", \n" +
					"y_speed : " + ((Tuio2DCur)cmd).getXspeed()
				);
			}
		}
	}

	@Override
	public void stop() {
	}
}
