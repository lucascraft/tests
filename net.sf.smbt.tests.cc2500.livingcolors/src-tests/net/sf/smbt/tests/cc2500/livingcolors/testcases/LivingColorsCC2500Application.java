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

package net.sf.smbt.tests.cc2500.livingcolors.testcases;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.smbt.cc2500.livingcolors.utils.LivingColorsCmdUtils;
import net.sf.smbt.comm.script.config.utils.INetCfgUtil;
import net.sf.smbt.comm.script.config.utils.NetCfgUtilsModule;
import net.sf.smbt.comm.script.config.utils.NetConfigUtil;
import net.sf.smbt.comm.script.netConf.NetCfg;
import net.sf.xqz.engine.utils.AbstractTTLJob;
import net.sf.xqz.model.cmd.Cmd;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.temporal.Orchestror;
import net.sf.xqz.tests.utils.infra.XQZTestsUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.google.inject.Guice;


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

public class LivingColorsCC2500Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		XQZTestsUtils.initLivingColorsCc2500TestWorkspace();
		
		NetConfigUtil netCfgUtil = (NetConfigUtil) Guice.createInjector(new NetCfgUtilsModule()).getInstance(INetCfgUtil.class);
		
		Map<NetCfg, List<Orchestror>> cfg = netCfgUtil.loadNetCfg(URI.createURI("platform:/resource/DefaultXQZProject/data/n.netconf"));
		
		CmdPipe engine = cfg.values().iterator().next().get(0).getApplication().getEngine().get(0);

		Cmd initCmd = LivingColorsCmdUtils.INSTANCE.createLIIVNG_COLORS_INIT_CMD();
		Cmd clearLampsCmd = LivingColorsCmdUtils.INSTANCE.createLIVIN_COLORS_CLEAR_LAMPS_CMD();
		Cmd addLampCmd = LivingColorsCmdUtils.INSTANCE.createLIVING_COLORS_ADD_LAMP_CMD(1);
		Cmd turnLampOnCmd = LivingColorsCmdUtils.INSTANCE.createLIVING_COLORS_TURN_LAMP_ON_RGB_CMD(1, 255, 0, 0);
		
		//
		// A living colors addr : AF CD C1 85 C5 57 7F CD 11 
		//
		engine.send(initCmd);
		
		Thread.sleep(100);
		
		engine.send(clearLampsCmd);

		Thread.sleep(100);

		engine.send(addLampCmd);
		
		Thread.sleep(100);

		engine.send(turnLampOnCmd);
		
		AbstractTTLJob job = new TimerJob(2500);
		job.schedule();
		
		while (job.isRunning())
			;
		return null;
	}

	@Override
	public void stop() {

	}
}
