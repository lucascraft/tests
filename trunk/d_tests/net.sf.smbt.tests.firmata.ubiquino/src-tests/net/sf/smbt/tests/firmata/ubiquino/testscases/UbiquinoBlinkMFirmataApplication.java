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

package net.sf.smbt.tests.firmata.ubiquino.testscases;

import net.sf.smbt.mdl.arduino.Arduino;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/*

Use case :

- 2 port monitored A1 (analogic 1), D1 (digital 1)

initial states : 

- Firmata on host : no registered reporting

Events :
- host -> device : MSG query firmware msg
- device -> host : MSG return query firmware ver (LSB/MSB)
- on host : set report on A1 to true
- host -> device : MSG set report on A1
- on host : set report on D1 to true
- host -> device : MSG set report on D1

....
...
..
.

- on device : sensor value arrives on A1
- device to host : MSG analog Input A1 from 0 to 0.323
- on host : internal disable for model listeners for A1 (temporary)
- on host : event handling thanks to Firmata switch synchronizing the change on the arduino board model valuation
- on host : enable back the event listening mechanism
- on host : set D1 value from 0 to 1 on arduino board model
- on host : temporarly disable changes msg waiting for a didgital Output setting MSG code 
- host -> device : MSG digital output on pin D1 from 0 to 1
- on host : wait while no return code received (TIMEOUT ongoing ... 250 ms ?)
- on device : main loop
- on host : main loop


 */
public class UbiquinoBlinkMFirmataApplication implements IApplication {
	private Arduino i2cDiecimilaBoard;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
/*
 		XQZTestsUtils.initFirmataTestWorkspace();
 
		
		
		NetConfigUtil netCfgUtil = (NetConfigUtil) Guice.createInjector(new NetCfgUtilsModule()).getInstance(INetCfgUtil.class);
		
		Map<NetCfg, List<Orchestror>> cfg = netCfgUtil.loadNetCfg(URI.createURI("platform:/resource/DefaultXQZProject/data/n.netconf"));
		
		CmdPipe engine = cfg.values().iterator().next().get(0).getApplication().getEngine().get(0);
		
		i2cDiecimilaBoard = UbiquinoUtils.INSTANCE.createArduinoBoard(
								ARDUINO_BOARD_UID.DIECMILA_ATMEGA_168, 
								ARDUINO_FIRMWARE_MODE.ARDUINO_FIRMATA_V21_I2C, 
								engine
							);

		engine.send(
			ThingM4FirmataCmdUtils.INSTANCE.createThingM4FimataStopScriptCmd("0x00")
		);
		
		Thread.sleep(150);
		
		Cmd fade2RGB = ThingM4FirmataCmdUtils.INSTANCE.createThingM4FimataFadeToRGBCmd("0x00", 255, 0,0);

		AbstractTTLJob job = new TimerJob(60000);
		job.schedule();
		
		while (job.isRunning()) {
			engine.getTx().postCommand(fade2RGB);
			
			Thread.sleep(150);
		}
		*/
		return new Object();
	}

	@Override
	public void stop() {
		
	}
}
