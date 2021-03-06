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

package net.sf.smbt.tests.osc.ingoing;

import java.util.List;
import java.util.Map;

import net.sf.smbt.comm.script.config.utils.INetCfgUtil;
import net.sf.smbt.comm.script.config.utils.NetCfgUtilsModule;
import net.sf.smbt.comm.script.config.utils.NetConfigUtil;
import net.sf.smbt.comm.script.netConf.NetCfg;
import net.sf.xqz.model.temporal.Orchestror;
import net.sf.xqz.tests.utils.infra.XQZTestsUtils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.google.inject.Guice;

public class OscClientApplication implements IApplication {
	private OscServerTtlJob job;
	@Override
	public Object start(IApplicationContext context) throws Exception {
		XQZTestsUtils.init1OscPortInTestWorkspace();
		
		NetConfigUtil netCfgUtil = (NetConfigUtil) Guice.createInjector(new NetCfgUtilsModule()).getInstance(INetCfgUtil.class);
		
		Map<NetCfg, List<Orchestror>> cfg = netCfgUtil.loadNetCfg(URI.createURI("platform:/resource/DefaultXQZProject/data/n.netconf"));
		
		cfg.values().iterator().next().get(0).getApplication().getEngine().get(0);

		job = new OscServerTtlJob();
		job.schedule();
		
		while (job.isRunning())
			;
		return null;
	}

	@Override
	public void stop() {
	}
}
