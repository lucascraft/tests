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

package net.sf.smbt.tests.pachube.testcases;

import net.sf.smbt.ezpachube.AbstractPachubeCmd;
import net.sf.smbt.pachube.utils.PachubeUtils;
import net.sf.smbt.quantic.warp.QuanticMojo;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.engine.utils.ITTLJob;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.engine.EVENT_KIND;
import net.sf.xqz.model.engine.Event;
import net.sf.xqz.model.engine.impl.AbstractQxEventHandlerImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eeml.xsd._0._5.EemlType;

public class PachubeSimpleIncomingTestApplication implements IApplication {

	public final static String API_KEY = "439862e5bb5aba2fdbfbf98eee4bcbea31e14623cb5563c12a905456df954343";
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		final CmdPipe pachubeEngine = QuanticMojo.INSTANCE.openHttpPipe(
			"pachube", 
			"http://api.pachube.com"
		);
		pachubeEngine.addQxEventHandler(new AbstractQxEventHandlerImpl() {
			@Override
			public void handleQxEvent(Event event) {
				if (event.getCmd() instanceof AbstractPachubeCmd) {
					if (event.getKind().equals(EVENT_KIND.RX_DONE)) {
						EemlType eeml = ((AbstractPachubeCmd)event.getCmd()).getEeml();
						PachubeUtils.INSTANCE.dumpEemlEnvironement(eeml);
					}
				}
			}
		});
		
		pachubeEngine.send(PachubeUtils.INSTANCE.createGET("", "12777.xml", API_KEY));
		
		ITTLJob ttl = new AbstractTTLJob(36000) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				pachubeEngine.send(
					PachubeUtils.INSTANCE.createGET("", "12777.xml", API_KEY)
				);
				schedule(3000);
				return Status.OK_STATUS;
			}
		};
		ttl.schedule();
		
		while(ttl.isRunning());
		
		return new Object();
	}

	@Override
	public void stop() {
	}
}
