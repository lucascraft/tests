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

package net.sf.smbt.tests.itunes.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.smbt.comm.http.io.HttpCommunicator;
import net.sf.smbt.dxxp.cmd.DxxpEventHandler;
import net.sf.smbt.dxxp.daap.DaapCmdUtils;
import net.sf.smbt.ezdaap.EZDaapITunesInstance;
import net.sf.smbt.ezdaap.EzdaapFactory;
import net.sf.smbt.ezdxxp.DaapCmd;
import net.sf.smbt.ezdxxp.DaapLoginResponse;
import net.sf.smbt.ezdxxp.DaapServerInfoResponse;
import net.sf.smbt.ezdxxp.DaapUpdateResponse;
import net.sf.smbt.ezdxxp.HttpProperty;
import net.sf.smbt.quantic.warp.QuanticMojo;
import net.sf.xqz.engine.utils.TTLApplication;
import net.sf.xqz.model.engine.CmdPipe;
import net.sf.xqz.model.engine.Event;

import org.eclipse.equinox.app.IApplicationContext;

public class DaapTestApplication extends TTLApplication {

	private EZDaapITunesInstance iTunes;
	private CmdPipe daapPipe;
	private HttpCommunicator communicator;
	private String addr = "127.0.0.1";
	private String port = "3689";
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		iTunes = EzdaapFactory.eINSTANCE.createEZDaapITunesInstance();
		
		daapPipe = QuanticMojo.INSTANCE.openHttpPipe(
			"daap", 
			"http://" + addr + ":" + port
		);
		
		daapPipe.addQxEventHandler(new DxxpEventHandler(){
			@Override
			public void handleQxEvent(Event evt) {
				if (evt.getCmd() instanceof DaapUpdateResponse) {
					iTunes.setRevID(((DaapUpdateResponse)evt.getCmd()).getRevisionID());
				} else if (evt.getCmd() instanceof DaapLoginResponse) {
					iTunes.setSessionID(((DaapLoginResponse)evt.getCmd()).getSessionID());
				} else if (evt.getCmd() instanceof DaapServerInfoResponse) {
					iTunes.setServerName(((DaapServerInfoResponse)evt.getCmd()).getServerName());
				}
			}
		});
		communicator = (HttpCommunicator) daapPipe.getPort().getChannel();
		
		try {
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_SERVER_INFO_REQUEST());
			Thread.sleep(500);
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_CONTENT_CODES_REQUEST(addr));
			Thread.sleep(500);
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_LOGIN());
			Thread.sleep(500);
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_UPDATE_REQUEST(iTunes.getSessionID(), iTunes.getRevID(), "127.0.0.1"));
			Thread.sleep(500);
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_DATABASE_REQUEST(iTunes.getSessionID(), iTunes.getRevID()));
			Thread.sleep(500);
			processDaapCmd(DaapCmdUtils.INSTANCE.createDAAP_LOGOUT(iTunes.getSessionID()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return super.start(context);
	}

	@Override
	public void stop() {
		printReport();
	}

	//
	// methods
	//
	
	void printReport() {
		System.out.println("bye");
	}
	
	private void processDaapCmd(DaapCmd cmd) {
		communicator.setProperties(propertiesLst2Map(cmd.getHttpProperties()));
		daapPipe.send(cmd);
	}
	
	private Map<String, String> propertiesLst2Map(List<HttpProperty> lst) {
		Map<String, String> map = new HashMap<String, String>();
		for (HttpProperty p : lst) {
			map.put(p.getKey(), p.getValue());
		}
		return map;
	}
}
