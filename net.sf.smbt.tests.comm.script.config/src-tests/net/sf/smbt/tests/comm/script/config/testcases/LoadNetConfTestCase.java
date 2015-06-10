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

package net.sf.smbt.tests.comm.script.config.testcases;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.smbt.comm.script.config.utils.INetCfgUtil;
import net.sf.smbt.comm.script.config.utils.NetCfgUtilsModule;
import net.sf.smbt.comm.script.config.utils.NetConfigUtil;
import net.sf.smbt.comm.script.netConf.NetCfg;
import net.sf.smbt.comm.script.netConf.NetOrchestrorScope;
import net.sf.xqz.engine.script.OrchestrorStandaloneSetup;
import net.sf.xqz.model.temporal.Orchestror;
import net.sf.xqz.script.utils.IOrchestrorUtils;
import net.sf.xqz.script.utils.OrchestrorUtil;

import org.eclipse.xtext.junit.AbstractXtextTests;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class LoadNetConfTestCase extends AbstractXtextTests {
	private Injector injector;
	private OrchestrorUtil orchestrorUtil;
	private NetConfigUtil netCfgUtil;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		with(new OrchestrorStandaloneSetup());
		injector = Guice.createInjector(new NetCfgUtilsModule());
		orchestrorUtil = (OrchestrorUtil) injector.getInstance(IOrchestrorUtils.class);
		netCfgUtil = (NetConfigUtil) injector.getInstance(INetCfgUtil.class);
	}

	public void testLoadNetConf() {
		try {
			Map<NetCfg, List<Orchestror>> cfgMap = netCfgUtil.loadNetCfg(new File("data/n.netconf").getCanonicalPath());

			for (NetCfg cfg : cfgMap.keySet()) {
				assertDSLOrchestrorScopes(cfg);
				assertCfg(cfg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void testNetConfBindingSetup(){
		try {
			Map<NetCfg, List<Orchestror>> cfgMap = netCfgUtil.loadNetCfg(new File("data/n.netconf").getCanonicalPath());
			
			for (NetCfg cfg : cfgMap.keySet()) {
				NetPort port = cfg.getPorts().get(0);
				switch (port.getKind()) {
					case USB:
						Serial serial = new Serial(port.getPortID(), port.getSpeed());
						assertUSBSerial(serial);
						break;
					case TCP:
					case UDP:
						// TODO : LB to add support for TCP/UDP comm
						break;
						//;
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void assertUSBSerial(Serial serial)  {
		assertNotNull(serial.getSerialPort());
	}
	*/
	
	public void assertCfg(NetCfg cfg) {
		assertNotNull(cfg);
		assertNotNull(cfg.getImports());
		assertNotNull(cfg.getListeners());
		assertNotNull(cfg.getOrchestrorScopes());
		assertNotNull(cfg.getPorts());

		assertEquals(1, cfg.getImports().size());
		assertEquals(1, cfg.getListeners().size());
		assertEquals(1, cfg.getOrchestrorScopes().size());
		assertEquals(1, cfg.getPorts().size());

		assertEquals("o.orchestror", cfg.getImports().get(0).getImportURI());

		assertNotNull(cfg.getListeners().get(0).getClazz());

		assertNotNull(cfg.getOrchestrorScopes().get(0).getBinds());
		assertEquals(1, cfg.getOrchestrorScopes().get(0).getBinds().size());
		assertNotNull(cfg.getOrchestrorScopes().get(0).getBinds().get(0));
		assertNotNull(cfg.getOrchestrorScopes().get(0).getBinds().get(0).getEngine());
		assertNotNull(cfg.getOrchestrorScopes().get(0).getBinds().get(0).getPort());
		assertNotNull(cfg.getOrchestrorScopes().get(0).getBinds().get(0).getListeners());
		assertEquals(1, cfg.getOrchestrorScopes().get(0).getBinds().get(0).getListeners().size());

		assertNotNull(cfg.getListeners().get(0).getClazz());
	}

	public void assertDSLOrchestrorScopes(NetCfg cfg) {
		for (NetOrchestrorScope o : cfg.getOrchestrorScopes()) {
			assertDSLOrchestror(orchestrorUtil.buildOrchestror(o.getDslOrchestror()));
		}
	}

	public void assertDSLOrchestror(Orchestror orchestror) {

		assertNotNull(orchestror);
		assertNotNull(orchestror.getClock());
		assertNotNull(orchestror.getId());
		assertNotNull(orchestror.getName());
		assertNotNull(orchestror.getApplication());
		assertNotNull(orchestror.getTime());
		assertNotNull(orchestror.getTimelines());

		assertEquals(1, orchestror.getTimelines().size());
/*
		assertNotNull(orchestror.getTimelines().get(0).getId());
		assertNotNull(orchestror.getTimelines().get(0).getName());
		assertNotNull(orchestror.getTimelines().get(0).getCmdEngine());
		assertNotNull(orchestror.getTimelines().get(0).getFinish());
		assertNotNull(orchestror.getTimelines().get(0).getOrchestror());
		assertNotNull(orchestror.getTimelines().get(0).getStart());
		assertNotNull(orchestror.getTimelines().get(0).getStatus());
		assertNotNull(orchestror.getTimelines().get(0).getTime());
*/
		
		assertNotNull(orchestror.getApplication().getId());
		assertNotNull(orchestror.getApplication().getName());
		assertNotNull(orchestror.getApplication().getClients());
		assertNotNull(orchestror.getApplication().getEngine());

		assertEquals(1, orchestror.getApplication().getClients().size());

		assertNotNull(orchestror.getApplication().getClients().get(0).getEngines());

		assertEquals(1, orchestror.getApplication().getEngine().size());

		assertNotNull(orchestror.getApplication().getEngine().get(0));
		assertNotNull(orchestror.getApplication().getEngine().get(0).getRx());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getRx().getEngine());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getRx().getCommands());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getRx().getEngine());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getTx());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getTx().getEngine());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getTx().getCommands());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getTx().getEngine());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getQueues());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getId());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getName());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getApplication());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getClient());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getCommandPool());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getEvents());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getOutputInterpreter());
		assertNotNull(orchestror.getApplication().getEngine().get(0).getInputInterpreter());
	}
}
